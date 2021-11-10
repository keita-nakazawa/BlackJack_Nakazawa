package model;

public class Game {

	private Deck deck;
	private SplitPlayers splitPlayers = new SplitPlayers();
	private Dealer dealer;
	private boolean allEndFlag = false;
	private String gameMessage;
	
	public Game(int bet) {

		Player player = new Player();
		Dealer dealer = new Dealer();
		Deck deck = new Deck();

		player.setBet(bet);
		
		for (int i = 0; i < 2; i++) {
			Card newCard = deck.removeCard();
			player.drawCard(newCard);
			player.setPoint(newCard);
			
			newCard = deck.removeCard();
			dealer.drawCard(newCard);
			dealer.setPoint(newCard);
		}
		
		player.setPlayerPoint();
		dealer.setPlayerPoint();
		player.setSplitFlag();

		this.deck = deck;
		splitPlayers.getList().add(player);
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

	public String getGameMessage() {
		return gameMessage;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	
	public void setPlayer(int index, Player player) {
		splitPlayers.getList().set(index, player);
	}
	
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
	public void setAllEndFlag() {
		for (Player player: splitPlayers.getList()) {
			if (player.isEnd()) {
				allEndFlag = true;
			} else {
				allEndFlag = false;
				break;
			}
		}
	}
	
	/**
	 * 終了していないゲームセッションがあるか確認する。
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
	 * プレイヤーとディーラーの点数を比較する。バースト判定フラグも考慮する。
	 */
	public History comparePoints(User loginUser) {
 
		History history = new History();
		history.setUserId(loginUser.getUserId());
		
		if (player.isBurst()) {
			
			history.setResult(player.getBet() * (-1));

		} else if (dealer.isBurst()) {
			
			if (player.isBlackJack() && (turnCount == 0)) {
				//ナチュラルBJ時はBET額を1.5倍して小数点以下切り上げ
				history.setNaturalBJ();
				history.setResult((int) Math.ceil(player.getBet() * 1.5));
			} else {
				history.setResult(player.getBet());
			}

		} else {

			if (dealer.getPlayerPoint() > player.getPlayerPoint()) {
				history.setResult(player.getBet() * (-1));

			} else if (dealer.getPlayerPoint() == player.getPlayerPoint()) {
				//3枚以上のカードでの「21」がナチュラルBJに対して負けるというルールは適用しない。
				history.setResult(0);

			} else {
				if (player.isBlackJack() && (turnCount == 0)) {
					//ナチュラルBJ時はBET額を1.5倍して小数点以下切り捨て
					history.setNaturalBJ();
					history.setResult((int) Math.floor(player.getBet() * 1.5));
				} else {
					history.setResult(player.getBet());
				}
			}
		}
		return history;
	}
}
