package xyz.personalenrichment.component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xyz.personalenrichment.entity.User;

import static xyz.personalenrichment.Util.*;

@Component
public class SocketHandler extends TextWebSocketHandler {
    @Autowired
    GameComponent gc;

    @Autowired
    UserComponent uc;

    @Autowired
    SessionComponent sc;
    
	HashSet<WebSocketSession> sessions = new HashSet<WebSocketSession>();
	Map<WebSocketSession, User> su = new ConcurrentHashMap<WebSocketSession, User>();

    Gson gson = new GsonBuilder().create(); 

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {	
		sc.add(session);
		System.out.println("New connection established. Current connections: " + sc.getSessionsCount());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(session.getId() + " " + message.getPayload());
		
		String[] message_parts = message.getPayload().split(" ", 3);
		
		System.out.println("--------" + message.getPayload());

		String handler = null;
		if(message_parts[0] == null || message_parts[0].equals("")) {
			System.out.println(UNKNOWN_HANDLER + " message did not split properly or lacked a handler");
			session.sendMessage(new TextMessage(UNKNOWN_HANDLER + " message did not split properly or lacked a handler"));
		} else {
			handler = message_parts[0];
		}
		
		String command = null;
		if(message_parts[1] == null || message_parts[1].equals("")) {
			System.out.println(UNKNOWN_COMMAND + " message did not split properly or lacked a command");
			session.sendMessage(new TextMessage(UNKNOWN_COMMAND + " message did not split properly or lacked a command"));
		} else {
			command = message_parts[1];
		}

		String tx_body = null;
		if(message_parts[2] == null) {
			tx_body = "";
		} else {
			tx_body = " " + message_parts[2];
		}

		System.out.println("========" + message.getPayload());
		
		if(command.equals(CLOSE)) {
        	System.out.println("processing " + CLOSE);
            close(session);
            return;
		}
		
		System.out.println("++++++++" + message.getPayload());

		TextMessage resultant_message = new TextMessage(command + tx_body);
		
		System.out.println(">>>>>>>>" + handler + " " + resultant_message.getPayload());
		
		switch (handler) {
			case (GAME_URI):
                gc.handleTextMessage(session, resultant_message);
				break;
			case (USER_URI):
                uc.handleTextMessage(session, resultant_message);
				break;
		}
	}
	
    private void close(WebSocketSession session) throws IOException {
		sc.remove(session);
		session.close();

        System.out.println("Session closed. Current connections: " + sc.getSessionsCount());
    }

}
