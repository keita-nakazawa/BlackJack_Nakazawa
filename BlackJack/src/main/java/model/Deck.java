package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> deck;
	
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
	
	public void removeCard() {
		
	}
	
	public List<Card> getDeck() {
		return deck;
	}
	
	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}
}
