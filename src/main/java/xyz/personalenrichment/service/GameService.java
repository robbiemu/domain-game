package xyz.personalenrichment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.component.SessionComponent;
import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.tx.Game;
import xyz.personalenrichment.tx.Move;

@Service
public class GameService {
	@Autowired
	SessionComponent sc;
	
	Gson gson = new GsonBuilder().create(); 
	
	Map<WebSocketSession, User> game_queue = new ConcurrentHashMap<WebSocketSession, User>();
	List<Game> ongoing_games = new ArrayList<>();

	public void processMove(WebSocketSession session, Move m) throws IOException {
		String id = session.getId();
		for(Game g: ongoing_games) {
			if ((g.getPlayerLeftSession().getId().equals(id)) || 
					(g.getPlayerRightSession().getId().equals(id))) {

				g.getBoard().processMove(m);

				if(g.getBoard().isEndState()) {
					ongoing_games.remove(g);

					TextMessage msg = new TextMessage("GAME_END " + gson.toJson(g));
					g.getPlayerLeftSession().sendMessage(msg);
					g.getPlayerRightSession().sendMessage(msg);
				} else {
					TextMessage msg = new TextMessage("GAME_MOVE " + gson.toJson(g));
					g.getPlayerLeftSession().sendMessage(msg);
					g.getPlayerRightSession().sendMessage(msg);
				}
				break;
			}
		}
	}

	/* game_index is the session.getId() of the player who queued it */
	public void startGame(String game_index, WebSocketSession session) throws IOException {
		WebSocketSession origin_session = sc.getSessionFromId(game_index);
		game_queue.remove(origin_session);
		
		Game g = new Game(origin_session, session);
		ongoing_games.add(g);
		
		TextMessage msg = new TextMessage("START_GAME " + gson.toJson(g));
		origin_session.sendMessage(msg);
		session.sendMessage(msg);
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
