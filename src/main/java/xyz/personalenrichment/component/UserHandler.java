package xyz.personalenrichment.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.entity.User;
import xyz.personalenrichment.repository.UserRepository;

@Component
public class UserHandler extends TextWebSocketHandler {
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
		System.out.println(message.getPayload());
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
					session.sendMessage(new TextMessage("LOGIN_SUCCESS"));
				} else {
					session.sendMessage(new TextMessage("LOGIN_FAILURE { \"reason\":\"invalid username or password\"}"));
				}
			} else {
				session.sendMessage(new TextMessage("LOGIN_FAILURE { \"reason\":\"not a complete set of credentials for authentication\"}"));
			}
		} else if (message.getPayload().startsWith("REGISTER")) {
			String[] jsm = message.getPayload().split(" ", 2);
			User txu = gson.fromJson(jsm[1], User.class);

			if(ur.isValidUserDetailsForLogin(txu)) {
				User u = ur.findOneByUsername(txu.getUsername());
				if(u == null) {
					try {
						u = ur.save(txu);
					} catch (Exception e) {
						session.sendMessage(new TextMessage("REGISTRATION_FAILURE { \"reason\":\""+e.getMessage()+"\"}"));						
					}
					sc.pair(session, u);
					session.sendMessage(new TextMessage("REGISTRATION_SUCCESS"));
				} else {
					session.sendMessage(new TextMessage("REGISTRATION_FAILURE { \"reason\":\"cannot register this username\"}"));
				}
			} else {
				session.sendMessage(new TextMessage("REGISTRATION_FAILURE { \"reason\":\"not a complete set of credentials for authentication\"}"));
			}
		}
	}	
}
