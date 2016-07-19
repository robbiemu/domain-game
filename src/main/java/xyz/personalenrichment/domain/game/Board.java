package xyz.personalenrichment.domain.game;

import java.util.Random;

import xyz.personalenrichment.domain.Tuple;

import static xyz.personalenrichment.domain.game.Defs.*;

public class Board {
	static Random rnd = new Random();
	
	public static Short[][] generate (short x, short y)  {
		int cnt_a = x*y/3;
		int cnt_b = x*y/3;
		int rem = x*y/3;
		Short[][] board = new Short[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++){
				int cand = rnd.nextInt(cnt_a + cnt_b +rem);
				if(cand < cnt_a) {
					cnt_a--;
					board[i][j] = 	PLAYER_A_BOARDMARK;			
				} else if (cand < (cnt_a + cnt_b)) {
					cnt_a--;
					board[i][j] = PLAYER_B_BOARDMARK;						
				} else {
					rem--;
					board[i][j] = EMPTY_BOARDMARK;
				}
			}
		}
		return board;
	}
	
	public static Short[][] move(Tuple<Short, Short> point_a, Tuple<Short, Short> point_b, Short player, Short[][] board) 
			throws IllegalMoveException {
		if (board[point_a.getLeft()][point_a.getRight()].equals(TOWER_BOARDMARK)) {
			throw new IllegalMoveException("Move was illegal!", point_a);
		} 
		
		if (board[point_b.getLeft()][point_b.getRight()].equals(TOWER_BOARDMARK)) {
			throw new IllegalMoveException("Move was illegal!", point_b);
		}

		int end_y = 0;
		int start_y = 0;
		if (point_a.getRight() < point_b.getRight()) {
			start_y = point_a.getRight();
			end_y = point_b.getRight() + 1;
		} else {
			start_y = point_b.getRight();
			end_y = point_a.getRight() + 1;
		}

		int end_x = 0;
		int start_x = 0;
		if (point_a.getLeft() < point_b.getLeft()) {
			start_x = point_a.getLeft();
			end_x = point_b.getLeft() + 1;
		} else {
			start_x = point_b.getLeft();
			end_x = point_a.getLeft() + 1;
		}
		
		
		for (int x = start_x; x < end_x; x++) {
			for (int y = start_y; y < end_y; y++) {
					if (board[x][y] == TOWER_BOARDMARK) {
						board[x][y] = EMPTY_BOARDMARK;
					} else if(board[x][y] == EMPTY_BOARDMARK) {
						board[x][y] = (player == PLAYER_A)? PLAYER_A_BOARDMARK: PLAYER_B_BOARDMARK;
					} else {
//					} else if( ((player == PLAYER_A) && (board[x][y] == PLAYER_B_BOARDMARK)) || 
//							((player == PLAYER_B) && (board[x][y] == PLAYER_A_BOARDMARK))) {
						board[x][y] =EMPTY_BOARDMARK;
					}
			}
		}

		board[point_a.getLeft()][point_a.getRight()] = TOWER_BOARDMARK;
		board[point_b.getLeft()][point_b.getRight()] = TOWER_BOARDMARK;
		
		return board;
	}
	
	static class IllegalMoveException extends Exception {
	    public IllegalMoveException(String message) {
	        super(message);
	    }
	    public IllegalMoveException(String message, Tuple<Short, Short> illegal_point) {
	        super(message + "\nIllegal point: " + illegal_point);
	    }
	}
}
