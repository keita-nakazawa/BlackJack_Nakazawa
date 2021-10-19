package model;

public class User {

	private String userId;
	private String nickname;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
		
	public String getUserId() {
		return userId;
	}
	
	public String getNickname() {
		return nickname;
	}
}
