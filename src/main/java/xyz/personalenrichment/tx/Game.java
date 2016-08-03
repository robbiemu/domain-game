package xyz.personalenrichment.tx;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class Game {
	public static final Boolean TURN_LEFT = true;
	public static final Boolean TURN_RIGHT = false;
	
	Board board;
	WebSocketSession playerLeftSession;
	WebSocketSession playerRightSession;
	Boolean turnState;
	
	public Game() { 
		board = new Board(); 		
		turnState = TURN_LEFT;
	}
	public Game(WebSocketSession cand_a, WebSocketSession cand_b) {
		board = new Board();
		randomLeftRight(cand_a, cand_b);
		turnState = TURN_LEFT;
	}
	public Game(Board b, WebSocketSession cand_a, WebSocketSession cand_b) {
		board = b;
		randomLeftRight(cand_a, cand_b);
		turnState = TURN_LEFT;
	}
	
	private void randomLeftRight(WebSocketSession cand_a, WebSocketSession cand_b) {
		if(Math.random() > 0.5) {
			playerLeftSession = cand_a;
			playerRightSession = cand_b;
		} else {
			playerLeftSession = cand_b;
			playerRightSession = cand_a;			
		}
	}
}
