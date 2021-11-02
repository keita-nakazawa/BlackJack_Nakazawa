package model;

import java.sql.Timestamp;

public class History {

	private String userId;
	private Timestamp timestamp;
	private int result;

	public String getUserId() {
		return userId;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public int getResult() {
		return result;
	}
	
	public String getStrResult() {
		switch(result) {
		case 1:
			return "win";
		case -1:
			return "lose";
		case 0:
			return "draw";
		default:
			//念のためのdefault
			return "error";
		}
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
}
