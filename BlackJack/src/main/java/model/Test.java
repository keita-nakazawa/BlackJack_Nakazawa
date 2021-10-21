package model;

public class Test {
	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.createDeck();
		
		int count = 0;
		for(Card card: deck.getDeck()) {
			System.out.println(card.getStrMark() + card.getNumber());
			count++;
		}
		System.out.printf("合計 %d枚\n",count);
	}
}
