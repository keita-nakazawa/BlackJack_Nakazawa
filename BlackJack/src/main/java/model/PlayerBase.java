package model;

public abstract class PlayerBase {
	
	protected Hand hand = new Hand();
	protected int point;
	protected boolean burst;
	
	public void drawCard(Card card) {
		hand.addCard(card);
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getPoint() {
		return point;
	}
	
	public boolean getBurst() {
		return burst;
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public void addPoint(int point) {
		this.point += point;
	}
	
	public void setBurst(boolean burst) {
		this.burst = burst;
	}
}
