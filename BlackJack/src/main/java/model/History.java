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

	public String getSignedResult() {
		if (result > 0) {
			return "+" + String.valueOf(result);
		} else if (result == 0) {
			return "±" + String.valueOf(result);
		} else {
			return String.valueOf(result);
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
