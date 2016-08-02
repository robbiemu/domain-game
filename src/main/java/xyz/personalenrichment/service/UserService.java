package xyz.personalenrichment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.component.SessionComponent;
import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.repository.UserRepository;

@Service
public class UserService extends TextWebSocketHandler {
	@Autowired
	UserRepository ur;
	
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
		} else if (message.getPayload().startsWith("LOGIN")) {
			String[] jsm = message.getPayload().split(" ", 2);
			User txu = gson.fromJson(jsm[1], User.class);

			if(ur.isValidUserDetailsForLogin(txu)) {
				User u = ur.findOneByUsernameAndPassword(txu.getUsername(), txu.getPassword());
				if(u != null) {
					sc.pair(session, u);
					// TODO - success message
				} else {
					// TODO - incorrect login details
				}
			} else {
				// TODO - invalid user credentials for login
			}
		} else if (message.getPayload().startsWith("REGISTER")) {
			String[] jsm = message.getPayload().split(" ", 2);
			User txu = gson.fromJson(jsm[1], User.class);

			if(ur.isValidUserDetailsForLogin(txu)) {
				User u = ur.findOneByUsername(txu.getUsername());
				if(u == null) {
					u = ur.save(txu);
					sc.pair(session, u);
				} else {
					// TODO - cannot register current username
				}
			} else {
				// TODO - invalid user credentials (missing password or username)
			}
		}
	}	
}
