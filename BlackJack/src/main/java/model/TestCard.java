package model;

import java.util.List;

public class TestCard {
	public static void main(String[] args) {
		Deck deck1 = new Deck();
		int size = deck1.getListOfDeck().size();
		
		int count = 0;
		for(int i = 0; i < size; i++) {
			List<Card> list = deck1.getListOfDeck();
			System.out.println(list.get(0).getStrMark() + list.get(0).getStrNumber());
			count++;
			deck1.getListOfDeck().remove(0);
		}
		System.out.printf("合計 %d枚\n",count);
		System.out.println(deck1.getListOfDeck().size());
		
		
//		Card card = new Card(Mark.valueOf("SPADE"), Number.valueOf("ACE"));
//		Hand hand = new Hand();
//		hand.addCard(card);
//		for(Card handCard: hand.getHand()) {
//			System.out.println(handCard.getStrMark() + handCard.getNumber());
//		}
//		hand.addCard(card);
//		for(Card handCard: hand.getHand()) {
//			System.out.println(handCard.getStrMark() + handCard.getNumber());
//		}
	}
}
