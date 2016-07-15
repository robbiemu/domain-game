package xyz.personalenrichment.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.personalenrichment.domain.model.User;

public class Util {
	public static ObjectMapper ObjectMapper = new ObjectMapper();
	/**
	 * 
	 * @param criteria - url string after ? with name=value pairs for criteria to interface a db model object
	 * @return a map of paramerters to use to query the database from the criteria given.
	 */
	public static Map<String, String> paramsFromCriteria (String criteria){
		String[] kvs = criteria.split("=");
		
		Map<String, String> params = new HashMap<>();
		for (int i = 0; i < kvs.length; i += 2) {
			params.put(kvs[i], kvs[i+1]);
		}
		
		return params;
	}

	public static Map<String, Tuple<Class<?>, Object>> modelParamsFromCriteria(
			Class<User> clazz, String criteria) {
		Map<String, String> rawCriteriaMap = paramsFromCriteria(criteria);
		Map<String, Tuple<Class<?>, Object>> returnMap = new HashMap<>();
		for (String key: rawCriteriaMap.keySet()) {
			String val = rawCriteriaMap.get(key);

			Class<?> t;
			try {
				t = clazz.getDeclaredField(key).getType();
			
				switch (t.getName()) {
				case "String":
					returnMap.put(key,  new Tuple<Class<?>, Object>(String.class, val));
					break;
				case "Integer":
					returnMap.put(key, new Tuple<Class<?>, Object>(Integer.class, Integer.parseInt(val)));
					break;
				case "int":
					Integer i = Integer.parseInt(val);
					returnMap.put(key, new Tuple<Class<?>, Object>(int.class, i.intValue()));
					break;
				case "Byte":
					returnMap.put(key, new Tuple<Class<?>, Object>(Byte.class, Byte.parseByte(val)));				
					break;
				case "byte":
					Byte b = Byte.parseByte(val);
					returnMap.put(key, new Tuple<Class<?>, Object>(byte.class, b.byteValue()));
					break;
				case "Date":
					returnMap.put(key,  new Tuple<Class<?>, Object>(Date.class, new Date(val)));
					break;
				}
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnMap;
	}
}
