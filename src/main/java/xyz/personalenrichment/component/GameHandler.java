package xyz.personalenrichment.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.service.GameService;
import xyz.personalenrichment.tx.Move;

@Component
public class GameHandler extends TextWebSocketHandler {
	@Autowired
	GameService gs;
	
	@Autowired
	SessionComponent sc;
	
	Gson gson = new GsonBuilder().create(); 
		
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {	
		sc.add(session);
		System.out.println("New connection established. Current connections: " + sc.getSessionsCount());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
			System.out.println("Session closed. Current connections: " + sc.getSessionsCount());

			session.close();
			sc.remove(session);
		} else if (message.getPayload().equalsIgnoreCase("LIST_QUEUED_GAMES")) {
			gs.listGames(session);
			System.out.println("received LIST_QUEUED_GAMES");
			System.out.println(session);
			System.out.println(sc.su);
			System.out.println("received LIST_QUEUED_GAMES (from player: "+ sc.getPlayerFromSession(session).getUsername() + ")");
		} else if (message.getPayload().equalsIgnoreCase("SUBSCRIBE_LIST_GAME_QUEUE")) {
			gs.subscribeListGameQueue(session);
			gs.listGames(session);
		} else if (message.getPayload().equalsIgnoreCase("SUBSCRIBE_LIST_ONGOING_GAMES")) {
			gs.subscribeListOngoingGames(session);
		} else if (message.getPayload().equalsIgnoreCase("UNSUBSCRIBE_LIST_GAME_QUEUE")) {
			gs.unsubscribeListGameQueue(session);
		} else if (message.getPayload().equalsIgnoreCase("UNSUBSCRIBE_LIST_ONGOING_GAMES")) {
			gs.unsubscribeListOngoingGames(session);
		} else if (message.getPayload().equalsIgnoreCase("GAME_LIST_CHANGE")) {

		} else if (message.getPayload().equalsIgnoreCase("ENQUEUE_FOR_GAME")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			System.out.println("received ENQUEUE_FOR_GAME (from player: "+ player.getUsername() + ")");

			gs.queue(session);
		} else if (message.getPayload().startsWith("ENROLL_IN_GAME")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			String[] jsg = message.getPayload().split(" ", 2); // jsg[1] is the game index to request

			System.out.println("received ENROLL_IN_GAME (from player: "+ player.getUsername() + ", game:" + jsg[1] + ")");

			gs.startGame(jsg[1], session);	
		} else if (message.getPayload().startsWith("PROCESS_MOVE")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			String[] jsm = message.getPayload().split(" ", 2);
			Move m = gson.fromJson(jsm[1], Move.class);

			System.out.println("received MOVE (from player: "+ player.getUsername() +"): " + m);
			
			m.setPlayer(player.getUsername());
			
			gs.processMove(session, m);
		}
	}
	
}
