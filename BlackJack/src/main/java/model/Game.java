package model;

public class Game extends Object{
	
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
}
