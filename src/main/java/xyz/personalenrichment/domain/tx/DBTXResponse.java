package xyz.personalenrichment.domain.tx;

import xyz.personalenrichment.domain.Util;

public class DBTXResponse {
	public static final String CREATE = "CREATE";
	public static final String READ = "READ";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	
	private String table;
	private String primary_key;
	private String type;
	private Boolean result;
	
	public DBTXResponse (Boolean result, String table, String primary_key, String type) {
		this.result = result;
		this.table = table;
		this.primary_key = primary_key;
		this.type = type;
	}
	
/*	@Override
	public String toString() {
		//return "DBTXResponse [table=" + table + ", primary_key=" + primary_key + ", type=" + type + ", result=" + result
		//		+ "]";
		return Defs.jackson.toJson(this);
	} */

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPrimary_key() {
		return primary_key;
	}

	public void setPrimary_key(String primary_key) {
		this.primary_key = primary_key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
	
}
