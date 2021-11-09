package model;

public class Player extends BasePlayer{

	private int bet;
	
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getBet() {
		return bet;
	}
	
	public void hit(Deck deck) {
		Card newCard = deck.removeCard();
		drawCard(newCard);
		setPoint(newCard);
		setBurst();
		setPlayerPoint();
	}
	
	public boolean isBlackJack() {
		if (getPlayerPoint() == BLACKJACK) {
			return true;
		} else {
			return false;
		}
	}
}
