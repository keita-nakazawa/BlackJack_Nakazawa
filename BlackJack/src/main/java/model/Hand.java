package model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> hand = new ArrayList<>();

	public List<Card> getListOfHand(){
		return hand;
	}
	
	public int getSize() {
		return hand.size();
	}
	
	public Card getCard(int index) {
		return hand.get(index);
	}

	public void addCard(Card card) {
		hand.add(card);
	}
	
	public Card removeCard() {
		return hand.remove(1);
	}
}
