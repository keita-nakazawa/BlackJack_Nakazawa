package model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> hand = new ArrayList<>();
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public List<Card> getListOfHand(){
		return hand;
	}
}
