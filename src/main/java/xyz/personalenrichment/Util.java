package xyz.personalenrichment;

import com.google.gson.Gson;

public class Util {
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
