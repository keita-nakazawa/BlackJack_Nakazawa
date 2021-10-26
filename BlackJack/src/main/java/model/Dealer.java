package model;

public class Dealer extends PlayerBase{

	private static final int CRITERIA = 17;
	
	public void stand(Deck deck) {
		while(point < CRITERIA) {
			drawCard(deck.removeCard());
			setPoint();
			setBurst();
		}
	}
}
