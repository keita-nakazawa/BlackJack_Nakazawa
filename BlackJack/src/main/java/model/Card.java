package model;

public class Card {
	
	private Mark mark;
	private Number number;
	
	public Card(Mark mark, Number number) {
		this.mark = mark;
		this.number = number;
	}
	
	public String getMark() {
		return mark.mark;
	}
	
	public String getNumber() {
		return number.number;
	}
	
	public int getPoint() {
		return number.point;
	}

	public int getPointAce() {
		return number.pointAce;
	}
}

enum Mark {
	
	SPADE("♠"),
	HEART("♥"),
	DIAMOND("♦"),
	CLUB("♣");
	
	protected String mark;
	
	private Mark(String mark) {
		this.mark = mark;
	}
}

enum Number {
	
	ACE("A",1, 11),
//	TWO("2",2),
//	THREE("3",3),
//	FOUR("4",4),
//	FIVE("5",5),
//	SIX("6",6),
//	SEVEN("7",7),
//	EIGHT("8",8),
	NINE("9",9),
//	TEN("10",10),
//	JACK("J",10),
//	QUEEN("Q",10),
	KING("K",10);
	
	protected String number;
	protected int point;
	protected int pointAce;
	
	private Number(String number, int point) {
		this.number = number;
		this.point = point;
	}
	
	private Number(String number, int point, int pointAce) {
		this.number = number;
		this.point = point;
		this.pointAce = pointAce;
	}
}