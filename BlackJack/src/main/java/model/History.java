package model;

public class History {

	private String userId;
	private String time;
	private int result;

	public String getUserId() {
		return userId;
	}
	
	public String getTime() {
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
			return "ERROR";
		}
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
}
