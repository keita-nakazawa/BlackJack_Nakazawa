package model;

import java.sql.Timestamp;

public class History {

	private String userId;
	private Timestamp time;
	private int result;

	public String getUserId() {
		return userId;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public int getResult() {
		return result;
	}
	
	public String getStrResult() {
		switch(result) {
		case -1:
			return "LOSE";
		case 0:
			return "DRAW";
		case 1:
			return "WIN";
		default:
			//念のためのdefault
			return "ERROR";
		}
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
}
