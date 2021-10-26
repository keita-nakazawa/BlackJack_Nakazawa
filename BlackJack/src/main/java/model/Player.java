package model;

public class Player extends PlayerBase{
	private User loginUser;
	
	public User getLoginUser() {
		return loginUser;
	}
	
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
