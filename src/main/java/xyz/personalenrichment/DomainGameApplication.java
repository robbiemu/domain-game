package xyz.personalenrichment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import xyz.personalenrichment.component.SocketHandler;

@SpringBootApplication
@EnableWebSocket
@EnableScheduling
public class DomainGameApplication implements WebSocketConfigurer {
	@Autowired
	SocketHandler socketHandler;
	
	public static void main(String[] args) {
		SpringApplication.run(DomainGameApplication.class, args);
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry handlerRegistry) {
		handlerRegistry.addHandler(socketHandler, "/domain").setAllowedOrigins("*");
	}
}
