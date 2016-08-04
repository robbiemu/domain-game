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
import xyz.personalenrichment.repository.UserRepository;

@Component
public class UserComponent {
    @Autowired
	UserRepository ur;
	
	@Autowired
	SessionComponent sc;
		
	Gson gson = new GsonBuilder().create(); 

	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String command = Util.getFirstWord(message.getPayload());
        switch(command) {
            case LOGIN:
            	System.out.println("processing " + LOGIN);
                login(session, message);
                break;
            case REGISTER:
            	System.out.println("processing " + REGISTER);
                register(session, message);
                break;
            default:
                System.out.println("Session " + session.getId() + " sent unknown command: " + command + " to "+ session.getUri());
                session.sendMessage(new TextMessage(UNKNOWN_COMMAND + " " + command));
        }
    }

    private void login(WebSocketSession session, TextMessage message) throws IOException {
        String[] jsm = message.getPayload().split(" ", 2);
        User txu = gson.fromJson(jsm[1], User.class);

        if(ur.isValidUserDetailsForLogin(txu)) {
            User u = ur.findOneByUsernameAndPassword(txu.getUsername(), txu.getPassword());
            if(u != null) {
                sc.pair(session, u);
                session.sendMessage(new TextMessage(LOGIN_SUCCESS));
            } else {
                session.sendMessage(new TextMessage(LOGIN_FAILURE + " { \"reason\":\"invalid username or password\"}"));
            }
        } else {
            session.sendMessage(new TextMessage(LOGIN_FAILURE + " { \"reason\":\"not a complete set of credentials for authentication\"}"));
        }
    }

    private void register(WebSocketSession session, TextMessage message) throws IOException {
        String[] jsm = message.getPayload().split(" ", 2);
        User txu = gson.fromJson(jsm[1], User.class);

        if(ur.isValidUserDetailsForLogin(txu)) {
            User u = ur.findOneByUsername(txu.getUsername());
            if(u == null) {
                try {
                    u = ur.save(txu);
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(REGISTRATION_FAILURE + " { \"reason\":\""+e.getMessage()+"\"}"));						
                }
                sc.pair(session, u);
                session.sendMessage(new TextMessage(REGISTRATION_SUCCESS));
            } else {
                session.sendMessage(new TextMessage(REGISTRATION_FAILURE + " { \"reason\":\"cannot register this username\"}"));
            }
        } else {
            session.sendMessage(new TextMessage(REGISTRATION_FAILURE + " { \"reason\":\"not a complete set of credentials for authentication\"}"));
        }
	}	

}