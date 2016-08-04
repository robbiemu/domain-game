package xyz.personalenrichment.component;

import static xyz.personalenrichment.Util.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.Util;
import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.service.GameService;
import xyz.personalenrichment.tx.Move;

@Component
public class GameComponent {
	@Autowired
	GameService gs;
	
	@Autowired
	SessionComponent sc;
	
	Gson gson = new GsonBuilder().create(); 

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(session.getId() + " " + message.getPayload());
    	
    	String command = Util.getFirstWord(message.getPayload());
        switch(command) {
            case LIST_QUEUED_GAMES:
            	System.out.println("processing " + LIST_QUEUED_GAMES);
                listQueuedGames(session);
                break;
            case SUBSCRIBE_LIST_GAME_QUEUE:
            	System.out.println("processing " + SUBSCRIBE_LIST_GAME_QUEUE);
                toggleSubscription(GAME_QUEUE_LIST, session, true);
                break;
            case UNSUBSCRIBE_LIST_GAME_QUEUE:
            	System.out.println("processing " + UNSUBSCRIBE_LIST_GAME_QUEUE);
                toggleSubscription(GAME_QUEUE_LIST, session, false);
                break;
            case SUBSCRIBE_LIST_ONGOING_GAMES:
            	System.out.println("processing " + SUBSCRIBE_LIST_ONGOING_GAMES);
                toggleSubscription(ONGOING_GAMES_LIST, session, true);
                break;
            case UNSUBSCRIBE_LIST_ONGOING_GAMES:
            	System.out.println("processing " + UNSUBSCRIBE_LIST_ONGOING_GAMES);
                toggleSubscription(ONGOING_GAMES_LIST, session, true);
                break;
            case QUEUE_FOR_GAME:
            	System.out.println("processing " + QUEUE_FOR_GAME);
                queueForGame(session);
                break;
            case ENROLL_IN_GAME:
            	System.out.println("processing " + ENROLL_IN_GAME);
                enrollInGame(session, message);
                break;
            case PROCESS_MOVE:
            	System.out.println("processing " + PROCESS_MOVE);
                processMove(session, message);
                break;
            default:
                System.out.println("Session " + session.getId() + " sent unknown command: " + command + " to "+ session.getUri());
                session.sendMessage(new TextMessage(UNKNOWN_COMMAND + " " + command));
        }

	}

    private void listQueuedGames(WebSocketSession session) throws IOException {
        gs.listGames(session);
        System.out.println("received " + LIST_QUEUED_GAMES);
        System.out.println(session);
        System.out.println(sc.su);
        System.out.println("received "+LIST_QUEUED_GAMES+" (from player: "+ sc.getPlayerFromSession(session).getUsername() + ")");
    }

    private void toggleSubscription(String subscription, WebSocketSession session, Boolean state) throws IOException {
        if(state) {
            if(subscription.equals(GAME_QUEUE_LIST)) {
                gs.subscribeListGameQueue(session);
                gs.listGames(session);
            } else {
                gs.subscribeListOngoingGames(session);                
            }
        } else {
            if(subscription.equals(GAME_QUEUE_LIST)) {
                gs.unsubscribeListGameQueue(session);
            } else {
                gs.unsubscribeListOngoingGames(session);                
            }            
        }

        System.out.println("Session "+session+" subscription status to " + subscription + " set to " + (state?"SUBSCRIBED":"UNSUBSCRIBED"));
    }

    private void queueForGame(WebSocketSession session) throws IOException {
        User player = sc.getPlayerFromSession(session);
        if(player == null) {
            // TODO - txError no player associated with session yet
        }
        System.out.println("received QUEUE_FOR_GAME (from player: "+ player.getUsername() + ")");

        gs.queue(session);
    }

    private void enrollInGame(WebSocketSession session, TextMessage message) throws IOException {
        User player = sc.getPlayerFromSession(session);
        if(player == null) {
            session.sendMessage(new TextMessage(TX_ERROR + " {\"reason\":\"no player associated with session yet\"} "));
        }
        String[] jsg = message.getPayload().split(" ", 2); // jsg[1] is the game index to request

        System.out.println("received ENROLL_IN_GAME (from player: "+ player.getUsername() + ", game:" + jsg[1] + ")");

        gs.startGame(jsg[1], session);	
    }

    private void processMove(WebSocketSession session, TextMessage message) throws IOException {
        User player = sc.getPlayerFromSession(session);
        if(player == null) {
            session.sendMessage(new TextMessage(TX_ERROR + " {\"reason\":\"no player associated with session yet\"} "));
        }
        String[] jsm = message.getPayload().split(" ", 2);
        Move m = gson.fromJson(jsm[1], Move.class);

        System.out.println("received PROCESS_MOVE (from player: "+ player.getUsername() +"): " + m);

        m.setPlayer(player.getUsername());

        gs.processMove(session, m);
    }

}