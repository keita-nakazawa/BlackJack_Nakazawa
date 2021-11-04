package model;

public class Dealer extends BasePlayer {

	//ディーラーは自分の点数がこの値以上になるまでカードを引き続ける。
	private static final int CRITERIA = 17;

	public void stand(Deck deck) {
		while (point < CRITERIA) {
			drawCard(deck.removeCard());
			setPoint();
			setBurst();
		}
	}
}
