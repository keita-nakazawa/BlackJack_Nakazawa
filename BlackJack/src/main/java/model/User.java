package model;

public class User {

	private String userId;
	private String nickname;
	private int chip;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setChip(int chip) {
		this.chip = chip;
	}
		
	public String getUserId() {
		return userId;
	}
	
	public String getNickname() {
		return nickname;
	}

	public int getChip() {
		return chip;
	}
	
	public void addChip(int result) {
		chip += result;
	}

	
//	public boolean isEmpty() {
//		if(userId.isEmpty() && nickname.isEmpty()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
