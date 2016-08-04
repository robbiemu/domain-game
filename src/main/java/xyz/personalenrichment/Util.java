package xyz.personalenrichment;

import com.google.gson.Gson;

public class Util {
	public static final String GAME_URI = "game";
	public static final String USER_URI = "user";
    public static final String UNKNOWN_HANDLER = "UNKNOWN_HANDLER";
    public static final String UNKNOWN_COMMAND = "UNKNOWN_COMMAND";
    public static final String TX_ERROR = "TX_ERROR";
	public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
	public static final String LOGIN_FAILURE = "LOGIN_FAILURE";
	public static final String REGISTRATION_SUCCESS = "REGISTRATION_SUCCESS";
	public static final String REGISTRATION_FAILURE = "REGISTRATION_FAILURE";
	public static final String CLOSE = "CLOSE";
	public static final String LOGIN = "LOGIN";
	public static final String REGISTER = "REGISTER";
    public static final String LIST_QUEUED_GAMES = "LIST_QUEUED_GAMES";
    public static final String SUBSCRIBE_LIST_GAME_QUEUE = "SUBSCRIBE_LIST_GAME_QUEUE";
    public static final String UNSUBSCRIBE_LIST_GAME_QUEUE = "UNSUBSCRIBE_LIST_GAME_QUEUE";
    public static final String SUBSCRIBE_LIST_ONGOING_GAMES = "SUBSCRIBE_LIST_ONGOING_GAMES";
    public static final String UNSUBSCRIBE_LIST_ONGOING_GAMES = "UNSUBSCRIBE_LIST_ONGOING_GAMES";
    public static final String QUEUE_FOR_GAME = "QUEUE_FOR_GAME";
    public static final String ENROLL_IN_GAME = "ENROLL_IN_GAME";
    public static final String PROCESS_MOVE = "PROCESS_MOVE";
	public static final String GAME_QUEUE_LIST = "GAME_QUEUE_LIST";
	public static final String ONGOING_GAMES_LIST = "ONGOING_GAME_LIST";
	
	public static String getFirstWord(String text) {
		if (text.indexOf(' ') > -1) { // Check if there is more than one word.
			return text.substring(0, text.indexOf(' ')); // Extract first word.
		} else {
			return text; // Text is the first word itself.
		}
  	}
	public static <T> T deepCopy(T object, Class<T> type) {
	    try {
	        Gson gson = new Gson();
	        return gson.fromJson(gson.toJson(object, type), type);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	public static String[][] copyOfS2D(String[][] originalArray) {
	   String[][] newArray = new String[originalArray.length][];
	 
	   for (int x = 0; x < originalArray.length; x++) {
	      newArray[x] = originalArray[x].clone();
	   }
	 
	   return newArray;
	}
}
