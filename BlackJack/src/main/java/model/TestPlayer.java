package model;

import java.util.List;

public class TestPlayer {
	public static void main(String[] args) {
		Player player = new Player();
		Card card = new Card(Mark.valueOf("SPADE"), Number.valueOf("ACE"));
		player.getHand().addCard(card);
		List<Card> listOfHand = player.getHand().getListOfHand();
		for(Card handCard: listOfHand) {
			System.out.println(handCard.getStrMark() + handCard.getStrNumber());
		}
	}
}
