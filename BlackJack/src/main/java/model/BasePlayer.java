package model;

public abstract class BasePlayer {

	protected static final int BLACKJACK = 21;
	
	protected Hand hand = new Hand();
	protected int point = 0;
	protected int point2 = 0;
	protected int playerPoint = 0;
	protected boolean burst = false;
	protected boolean burst2 = false;
	protected boolean playerBurst = false;

	public void drawCard(Card card) {
		hand.addCard(card);
	}

	public Hand getHand() {
		return hand;
	}
	
	public int getPoint() {
		return point;
	}

	public int getPoint2() {
		return point2;
	}

	public int getPlayerPoint() {
		return playerPoint;
	}

	public boolean getBurst() {
		return burst;
	}

	public boolean getBurst2() {
		return burst2;
	}

	public boolean isBurst() {
		return playerBurst;
	}

	public void addPoint(Card newCard) {

		//いかなるときも point≦point2 となる
		if (newCard.getNumber().equals("A")) {
			point2 = point + newCard.getPointAce();
			point = point + newCard.getPoint();
		} else {
			point2 += newCard.getPoint();
			point += newCard.getPoint();
		}
	}
	
	public void setBurst() {

		if (point > BLACKJACK) {
			burst = true;
		}
		if (point2 > BLACKJACK) {
			burst2 = true;
		}
		if ((point > BLACKJACK) && (point2 > BLACKJACK)) {
			playerBurst = true;
		}
	}
	
	public void setPlayerPoint() {
		if (burst2) {
			playerPoint = point;
		} else {
			playerPoint = point2;
		}
	}
}
