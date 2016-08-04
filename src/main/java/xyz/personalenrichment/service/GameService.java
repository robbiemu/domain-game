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

	List<WebSocketSession> game_queue_subscribers = new ArrayList<>();
	List<WebSocketSession> ongoing_game_subscribers = new ArrayList<>();

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

					notify_ongoing_game_subscribers();
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

		notify_game_queue_subscribers();
		notify_ongoing_game_subscribers();
	}

	public void queue(WebSocketSession session) throws IOException {
		if(game_queue.containsKey(session)) {
			game_queue.remove(session);
		}
		game_queue.put(session, sc.getPlayerFromSession(session));
		
		notify_game_queue_subscribers();
	}                       

	public void listGames(WebSocketSession session) throws IOException {
		session.sendMessage( new TextMessage("GAME_QUEUE_CHANGE " + gson.toJson(game_queue)) );
	}

	public void subscribeListGameQueue(WebSocketSession session) {
		game_queue_subscribers.add(session);
	}

	public void subscribeListOngoingGames(WebSocketSession session) {
		ongoing_game_subscribers.add(session);
	}

	private void notify_game_queue_subscribers() throws IOException {
		TextMessage msg = new TextMessage("GAME_QUEUE_CHANGE " + gson.toJson(game_queue));
		for(WebSocketSession s: game_queue_subscribers) {
			s.sendMessage(msg);
		}
	}

	private void notify_ongoing_game_subscribers() throws IOException {
		TextMessage msg = new TextMessage("ONGOING_GAME_CHANGE " + gson.toJson(ongoing_games));
		for(WebSocketSession s: ongoing_game_subscribers) {
			s.sendMessage(msg);
		}
	}
}
