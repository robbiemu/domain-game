package xyz.personalenrichment.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import xyz.personalenrichment.component.SessionComponent;
import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.tx.Move;

@Service
public class GameService {
	@Autowired
	BoardService bs;

	@Autowired
	SessionComponent sc;
	
	Map<WebSocketSession, User> game_queue = new ConcurrentHashMap<WebSocketSession, User>();

	public void processMove(Move m) {
		// TODO Auto-generated method stub
		
	}

	public void startGame(String game_index) {
		game_queue.remove(sc.getSessionFromId(game_index));
		// TODO - build the board, start the game
	}

	public void queue(WebSocketSession session) {
		if(game_queue.containsKey(session)) {
			game_queue.remove(session);
		}
		game_queue.put(session, sc.getPlayerFromSession(session));
	}                       

	public Set<String> listGames() {
		Set<String> rs = new HashSet<>();
		for(WebSocketSession s: game_queue.keySet()) {
			rs.add(s.getId());
		}
		return rs;
	}

}
