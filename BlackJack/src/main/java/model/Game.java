package model;

public class Game {
	
	private Deck deck;
	private Player player;
	private Dealer dealer;
	private int turnCount;
	
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
	
	public int getTurnCount() {
		return turnCount;
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
	
	public void addTurnCount() {
		turnCount += 1;
	}
	
//	public String checkNull(String nextPage, String thisPage) {
//		if(this.equals(null)) {
//			return nextPage;
//		} else {
//			return thisPage;
//		}
//	}
	
	/**
	 * ブラウザの戻るボタンを悪用して無限にスタンドする不正行為対策として(たぶん)使えるメソッド。
	 */
	public void checkTurnCount(int correctValue) {
		if(turnCount != correctValue) {
			
		}
	}
}
