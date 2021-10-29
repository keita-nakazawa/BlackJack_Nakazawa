package model;

public class User {

	private String userId;
	private String nickname;
	private float winRate;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setWinRate(float winRate) {
		this.winRate = winRate;
	}
		
	public String getUserId() {
		return userId;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public float getWinRate() {
		return winRate;
	}
	
//	public boolean isEmpty() {
//		if(userId.isEmpty() && nickname.isEmpty()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
