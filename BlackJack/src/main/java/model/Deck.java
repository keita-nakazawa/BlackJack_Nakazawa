package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> deck = new ArrayList<>();
	
	public void createDeck() {
		List<Card> deck = new ArrayList<Card>();
		
		for(Mark mark: Mark.values()) {
			for(Number number: Number.values()) {
				deck.add(new Card(mark, number));
			}
		}
		Collections.shuffle(deck);
		this.deck = deck;
	}
	
	public Card removeCard() {
		return deck.remove(0);
	}
	
	public List<Card> getListOfDeck() {
		return deck;
	}
	
//	public void setDeck(List<Card> deck) {
//		this.deck = deck;
//	}
}
