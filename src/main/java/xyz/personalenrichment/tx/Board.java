package xyz.personalenrichment.tx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.personalenrichment.Tuple;
import xyz.personalenrichment.Util;

public class Board {
	public static final String UNSET = "";
	public static final String PLAYER_LEFT = "player_left";
	public static final String PLAYER_RIGHT = "player_right";
	public static final String TOWER_LEFT = "tower_left";
	public static final String TOWER_RIGHT = "tower_right";	
	public static final Short DEFAULT_X = 40;	
	public static final Short DEFAULT_Y = 40;
	Short width;
	Short height;
	String[][] board;
	
	public Board() {
		init_board(DEFAULT_X, DEFAULT_Y);
	}
	public Board(Short width, Short height) {
		init_board(width, height);
	}	
	
	private void init_board(Short width, Short height) {
		this.width = width;
		this.height = height;
		board = new String[width][height];

		Integer boardSize = width * height;
		Integer unsetCount = boardSize/3;
		Integer leftCount = unsetCount;
		Integer rightCount = unsetCount;
		unsetCount += boardSize - (unsetCount + leftCount + rightCount);
			
		Double unsetChance = unsetCount/(boardSize*1.0);
		Double leftChance = leftCount/(boardSize*1.0);
		Double rightChance = rightCount/(boardSize*1.0);
		
		Double chance = 0.;
		for(Short y = 0; y < height; y++) {
			for(Short x = 0; x < width; x++) {
				unsetChance = unsetCount/(boardSize*1.0);
				leftChance = leftCount/(boardSize*1.0);
				rightChance = rightCount/(boardSize*1.0);

				chance = Math.random();
				if(chance < rightChance) {
					rightCount--;
					board[x][y] = PLAYER_RIGHT;
				} else if (chance < unsetChance + leftChance) {
					leftCount--;
					board[x][y] = PLAYER_LEFT;
				} else {
					unsetCount--;
					board[x][y] = UNSET;
				}
				boardSize--;
			}
		}		
	}

	/* moves from the client with have the player unset, or in some other way need to be set by the server. 
	 * The server must keep a list of ongoing games with players assigned left and right.
	 * 
	 *  By the time this method is called, the server must have set the player to PLAYER_LEFT or PLAYER_RIGHT
	 *  
	 * (The board must also be set from the games list.) Processing of moves on the board is reflected in the 
	 * state of the board after the change.
	 */
	public void processMove(Move move) {
		List<String> tower_consts = new ArrayList<>(Arrays.asList(TOWER_LEFT, TOWER_RIGHT));
		if(tower_consts.contains(board[move.primary.getLeft()][move.primary.getRight()]) || 
				tower_consts.contains(board[move.secondary.getLeft()][move.secondary.getRight()]) ){
			// TODO -- illegal position in move, already has tower
		}
		if(move.primary.getLeft() < 0 || move.secondary.getLeft() < 0 || 
				move.primary.getRight() < 0 || move.secondary.getRight() < 0 ||
				move.primary.getLeft() > width || move.secondary.getLeft() > width || 
				move.primary.getRight() > height || move.secondary.getRight() > height ) {			
			// TODO -- illegal point selection for move
		}
		if((!move.getPlayer().equals(PLAYER_LEFT)) && (!move.getPlayer().equals(PLAYER_RIGHT))) {
			// TODO - illegal player in move.
		}		
		
		String[][] tmp_board = Util.copyOfS2D(board);
				
		boolean inverted_x = (move.primary.getLeft() > move.secondary.getLeft());
		boolean inverted_y = (move.primary.getRight() > move.secondary.getRight());
		
		Short start_x = 0;
		Short start_y = 0;
		Short end_x = 0;
		Short end_y = 0;
		if(inverted_x) { 
			start_x = move.secondary.getLeft();
			end_x = move.primary.getLeft();
		} else {
			start_x = move.primary.getLeft();
			end_x = move.secondary.getLeft();			
		}
		if(inverted_y) {
			start_y = move.secondary.getRight();
			end_y = move.primary.getRight();			
		} else {
			start_y = move.primary.getRight();
			end_y = move.secondary.getRight();						
		}
		
		List<Tuple<Short, Short>> zztowers = new ArrayList<>();
		List<Tuple<Short, Short>> zptowers = new ArrayList<>();
		List<Tuple<Short, Short>> pztowers = new ArrayList<>();
		List<Tuple<Short, Short>> pptowers = new ArrayList<>();
		for(short y = start_y; y <= end_y; y++) {
			for(short x = start_x; x <= end_x; x++){
				if((y == 0 && x == 0) || (y == end_y && x == end_x)) {
					board[x][y] = move.getPlayer().equals(PLAYER_LEFT)? TOWER_LEFT: TOWER_RIGHT;
					tmp_board[x][y] = board[x][y]; // Towers are placed in any case
				} else {
					if (board[x][y].equals(
							move.getPlayer().equals(PLAYER_LEFT)? TOWER_RIGHT: TOWER_LEFT)) {

						Boolean x_quad = ((end_x - start_x)/2 > x);
						Boolean y_quad = ((end_y - start_y)/2 > y);

						if(x_quad) {
							if(y_quad) {
								pptowers.add(new Tuple<Short, Short>(x,y));								
							} else {
								pztowers.add(new Tuple<Short, Short>(x,y));
							}
						} else {
							if(y_quad) {
								zptowers.add(new Tuple<Short, Short>(x,y));								
							} else {
								zztowers.add(new Tuple<Short, Short>(x,y));
							}
						}
						
						board[x][y] = UNSET;
					} else if (board[x][y].equals(UNSET)) {
						board[x][y] = move.getPlayer();
					} else if (board[x][y].equals(
							move.getPlayer().equals(PLAYER_LEFT)? PLAYER_RIGHT: PLAYER_LEFT)) {
						board[x][y] = UNSET;
					}
				}				
			}
		}
		
		/* Now we have a set of points that should shield segments of the board from change, and
		 * a fully changed board. Time to selectively preserve in those quadrants. */
		if(zztowers.size() > 0) {
			for (Tuple<Short, Short> t: zztowers) {
				for(short y = start_y; y <= t.getRight(); y++) {
					for(short x = start_x; x <= t.getLeft(); x++){
						board[x][y] = tmp_board[x][y];
					}
				}
			}
		}
		if(pptowers.size() > 0) {
			for (Tuple<Short, Short> t: zztowers) {
				for(short y = t.getRight(); y <= end_y; y++) {
					for(short x = t.getLeft(); x <= end_x; x++){
						board[x][y] = tmp_board[x][y];
					}
				}
			}
		}
		if(pztowers.size() > 0) {
			for (Tuple<Short, Short> t: zztowers) {
				for(short y = start_y; y <= t.getRight(); y++) {
					for(short x = t.getLeft(); x <= end_x; x++){
						board[x][y] = tmp_board[x][y];
					}
				}
			}
		}		
		if(zptowers.size() > 0) {
			for (Tuple<Short, Short> t: zztowers) {
				for(short y = t.getRight(); y <= end_y; y++) {
					for(short x = start_x; x <= t.getLeft(); x++){
						board[x][y] = tmp_board[x][y];
					}
				}
			}
		}
	}
	
	public boolean isEndState() {
		Integer left_count = 0;
		Integer right_count = 0;
		for(short y = 0; y <= height; y++) {
			for(short x = 0; x <= width; x++){
				switch (board[x][y]){
				case PLAYER_LEFT:
					left_count++;
					break;
				case PLAYER_RIGHT:
					right_count++;
					break;
				}
			}
		}
		return ((left_count>(width*height)/2)|| (right_count>(width*height)/2)? true: false);
	}

	public String winningPlayer() {
		Integer left_count = 0;
		Integer right_count = 0;
		for(short y = 0; y <= height; y++) {
			for(short x = 0; x <= width; x++){
				switch (board[x][y]){
				case PLAYER_LEFT:
					left_count++;
					break;
				case PLAYER_RIGHT:
					right_count++;
					break;
				}
			}
		}
		if (left_count>(width*height)/2) {
			return PLAYER_LEFT;
		}
		if (right_count>(width*height)/2) {
			return PLAYER_RIGHT;
		}
		return null;
	}
	
}
