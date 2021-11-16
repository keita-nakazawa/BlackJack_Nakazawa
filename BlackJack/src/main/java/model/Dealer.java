package model;

public class Dealer extends BasePlayer {

	// ディーラーは自分の点数がこの値以上になるまでカードを引き続ける。
	private static final int CRITERIA = 17;

	/**
	 * プレイヤーの手札が1つでも結果待ち状態である場合、<br>
	 * ディーラーは基準を満たすまでカードを引き続ける。<br>
	 * プレイヤーの手札がすべてバースト状態である場合、<br>
	 * ディーラーは何もしない。
	 */
	public void action(SplitPlayers splitPlayers, Deck deck) {
		
		if (isBlackJack()) {
			
			setNaturalBJFlag();
		
		} else {

			for (Player player: splitPlayers.getList()) {
				if (!(player.isBurst())) {
					while (getPlayerPoint() < CRITERIA) {
						Card newCard = deck.removeCard();
						drawCard(newCard);
						addPoint(newCard);
						setBurst();
						setPlayerPoint();
					}
					break;
				}
			}
		}
	}
	
}
