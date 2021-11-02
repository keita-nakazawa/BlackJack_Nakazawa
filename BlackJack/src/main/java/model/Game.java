package model;

public class Game {

	private Deck deck;
	private Player player;
	private Dealer dealer;
	private String gameMessage;
	
	public Game() {

		Player player = new Player();
		Dealer dealer = new Dealer();
		Deck deck = new Deck();

		for (int i = 0; i < 2; i++) {
			player.drawCard(deck.removeCard());
			dealer.drawCard(deck.removeCard());
		}
		player.setPoint();
		dealer.setPoint();

		this.deck = deck;
		this.player = player;
		this.dealer = dealer;
	}

	public Deck getDeck() {
		return deck;
	}

	public Player getPlayer() {
		return player;
	}

	public Dealer getDealer() {
		return dealer;
	}
	
	public String getGameMessage() {
		return gameMessage;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return Game<br>
	 *         パラメータがnullの場合は自身を、nullでない場合はパラメータを返す。
	 */
	public Game start(Game oldGame) {

		if (oldGame == null) {
			return this;
		} else {
			return oldGame;
		}
	}

	/**
	 * プレイヤーとディーラーの点数を比較する。burstフラグも考慮する。
	 */
	public History comparePoints(User loginUser) {
 
		History history = new History();
		history.setUserId(loginUser.getUserId());

		if (player.getBurst() == true) {
			history.setResult(-1);
			gameMessage = "バーストしました。</br>ディーラーの勝利です。";

		} else if (dealer.getBurst() == true) {
			history.setResult(1);
			gameMessage = "ディーラーがバーストしました。</br>あなたの勝利です。";

		} else {

			if (dealer.getPoint() > player.getPoint()) {
				history.setResult(-1);
				gameMessage = "ディーラーの勝利です。";

			} else if (dealer.getPoint() == player.getPoint()) {
				history.setResult(0);
				gameMessage = "引き分けです。";

			} else {
				history.setResult(1);
				gameMessage = "あなたの勝利です。";
			}
		}
		return history;
	}
}
