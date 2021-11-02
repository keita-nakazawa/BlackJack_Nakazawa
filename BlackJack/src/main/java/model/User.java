package model;

public class User {

	private String userId;
	private String nickname;
	private int win;
	private int lose;
	private int draw;
	private float winRate;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setWin(int win) {
		this.win = win;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public void setDraw(int draw) {
		this.draw = draw;
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

	public int getWin() {
		return win;
	}

	public int getLose() {
		return lose;
	}

	public int getDraw() {
		return draw;
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
