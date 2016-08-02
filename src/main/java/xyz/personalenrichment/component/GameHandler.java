package xyz.personalenrichment.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.service.BoardService;
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
		//System.out.println(message.getPayload());
		if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
			session.close();
			sc.remove(session);
			System.out.println("Session closed. Current connections: " + sc.getSessionsCount());
		} else if (message.getPayload().equalsIgnoreCase("LIST_GAMES")) {
			gs.listGames();
		} else if (message.getPayload().equalsIgnoreCase("ENQUEUE_FOR_GAME")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			gs.queue(session);
			System.out.println("received ENQUEUE_FOR_GAME (from player: "+ player.getUsername() + ")");
		} else if (message.getPayload().startsWith("ENROLL_IN_GAME")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			String[] jsg = message.getPayload().split(" ", 2); // jsg[1] is the game index to request
			// we start game at this point:
			// generate board
			gs.startGame(jsg[1]);	

			System.out.println("received ENROLL_IN_GAME (from player: "+ player.getUsername() + ", game:" + jsg[1] + ")");

			// return message with board and begin flag
		} else if (message.getPayload().startsWith("PROCESS_MOVE")) {
			User player = sc.getPlayerFromSession(session);
			if(player == null) {
				// TODO - txError no player associated with session yet
			}
			String[] jsm = message.getPayload().split(" ", 2);
			Move m = gson.fromJson(jsm[1], Move.class);
			
			System.out.println("received MOVE (from player: "+ player.getUsername() +"): " + m);

			m.setPlayer(player.getUsername());
			
			gs.processMove(m);
		}
	}
}
