package model;

public class Player extends BasePlayer{
	
	public void hit(Deck deck) {
		Card newCard = deck.removeCard();
		drawCard(newCard);
		setPoint(newCard);
		setBurst();
		setPlayerPoint();
	}
}
