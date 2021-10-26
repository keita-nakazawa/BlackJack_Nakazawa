package model;

public class Game {
	
	private Deck deck;
	private Player player;
	private Dealer dealer;
	
	public Game(Deck deck, Player player, Dealer dealer) {
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
	 * プレイヤーとディーラーの点数を比較
	 * @return 
	 * -1: ディーラーの勝利<br>
	 * 0: 引き分け<br>
	 * 1: プレイヤーの勝利<br>
	 */
	public int comparePoints() {
		
		dealer.stand(deck);
		
		if(dealer.getBurst() == false) {
			if(dealer.getPoint() > player.getPoint()) {
				return -1;
			} else if(dealer.getPoint() == player.getPoint()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}
}
