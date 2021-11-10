package model;

public class Dealer extends BasePlayer {

	//ディーラーは自分の点数がこの値以上になるまでカードを引き続ける。
	private static final int CRITERIA = 17;

	public void action(Deck deck) {
		while (getPlayerPoint() < CRITERIA) {
			Card newCard = deck.removeCard();
			drawCard(newCard);
			setPoint(newCard);
			setBurst();
			setPlayerPoint();
		}
	}
}
