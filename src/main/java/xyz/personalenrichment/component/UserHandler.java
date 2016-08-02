package xyz.personalenrichment.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import xyz.personalenrichment.service.UserService;

@Component
public class UserHandler extends TextWebSocketHandler {
	@Autowired
	UserService us;
	
}
