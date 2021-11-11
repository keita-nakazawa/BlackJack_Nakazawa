package model;

public class Game {

	private Deck deck;
	private SplitPlayers splitPlayers = new SplitPlayers();
	private Dealer dealer;
	// splitPlayers中のすべてのPlayerオブジェクトのendFlagがtrueの場合、true
	private boolean gameEndFlag = false;

	public Game(int bet) {

		Player player = new Player();
		Dealer dealer = new Dealer();
		Deck deck = new Deck();

		player.setBet(bet);

		for (int i = 0; i < 2; i++) {
			Card newCard = deck.removeCard();
			player.drawCard(newCard);
			player.addPoint(newCard);

			newCard = deck.removeCard();
			dealer.drawCard(newCard);
			dealer.addPoint(newCard);
		}

		player.setPlayerPoint();
		dealer.setPlayerPoint();
		player.setSplitFlag();

		this.deck = deck;
		splitPlayers.addPlayer(player);
		this.dealer = dealer;
	}

	public Deck getDeck() {
		return deck;
	}

	public SplitPlayers getSplitPlayers() {
		return splitPlayers;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public boolean isEnd() {
		return gameEndFlag;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public void setGameEndFlag() {
		for (Player player : splitPlayers.getList()) {
			if (player.isEnd()) {
				gameEndFlag = true;
			} else {
				gameEndFlag = false;
				break;
			}
		}
	}
	
	/**
	 * 終了していないゲームセッションがあるか確認する。
	 * @return oldGameがnullの場合は自身を、nullでない場合はoldGameを返す。
	 */
	public Game start(Game oldGame) {

		if (oldGame == null) {
			return this;
		} else {
			return oldGame;
		}
	}

	/**
	 * プレイヤーのすべての手札とディーラーの手札で点数を比較し、チップ収支の総計を求める。
	 * @return DBのhistoryテーブルへの記録に用いるHistoryオブジェクト
	 */
	public History comparePoints(User loginUser) {

		History history = new History();
		history.setUserId(loginUser.getUserId());
		
		int result = 0;
		for (Player player: getSplitPlayers().getList()) {

			if (player.isBurst()) {

				player.setEachResult(player.getBet() * (-1));

			} else if (dealer.isBurst()) {

				if (player.isNaturalBJ()) {
					//BET額を1.5倍して小数点以下切り捨て
					player.setEachResult((int) Math.floor(player.getBet() * 1.5));
				} else {
					player.setEachResult(player.getBet());
				}

			} else {

				if (dealer.getPlayerPoint() > player.getPlayerPoint()) {
					player.setEachResult(player.getBet() * (-1));

				} else if (dealer.getPlayerPoint() == player.getPlayerPoint()) {
					// 3枚以上のカードでの「21」がナチュラルBJに対して負けるというルールは適用しない。
					player.setEachResult(0);

				} else {
					if (player.isNaturalBJ()) {
						// ナチュラルBJ時はBET額を1.5倍して小数点以下切り捨て
						player.setEachResult((int) Math.floor(player.getBet() * 1.5));
					} else {
						player.setEachResult(player.getBet());
					}
				}
			}

			result += player.getEachResult();
		}
		history.setResult(result);

		return history;
	}
}
