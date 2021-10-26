package model;

public abstract class PlayerBase {
	
	protected Hand hand = new Hand();
	protected int point = 0;
	protected boolean burst = false;
	
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
	
	public void setPoint() {
		int pointCounter = 0;
		for(Card card: hand.getListOfHand()) {
			pointCounter += card.getIntPoint();
		}
		point = pointCounter;
	}
	
	public void setBurst() {
		 if(point > 21) {
			 burst = true;
		 }
	}
}
