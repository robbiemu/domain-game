package xyz.personalenrichment.component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import xyz.personalenrichment.entity.User;

@Component
public class SessionComponent {
	HashSet<WebSocketSession> sessions = new HashSet<WebSocketSession>();
	Map<WebSocketSession, User> su = new ConcurrentHashMap<WebSocketSession, User>();
	
	public void add(WebSocketSession session) {
		sessions.add(session);
	}
	
	public void remove(WebSocketSession session) {
		sessions.remove(session);
		if(su.containsKey(session)) {
			su.remove(session);				
		}
	}

	public Integer getSessionsCount() {
		return sessions.size();
	}

	public void pair(WebSocketSession session, User u) {
		su.put(session, u);
	}
	
	public User getPlayerFromSession(WebSocketSession session) {
		return su.get(session);
	}

	public WebSocketSession getSessionFromId(String game_index) {
		for(WebSocketSession s: sessions) {
			if(s.getId().equals(game_index)) {
				return s;
			}
		}
		return null;
	}
	
	public void globalMessageCallback(String msg) {
		Iterator<WebSocketSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			WebSocketSession session = iterator.next();
			if (session != null && session.isOpen()) {		
				try {
					session.sendMessage(new TextMessage(msg));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Session is not open. Removing session");
				iterator.remove();
			}
		}
	}

}
