package model;

public class Card {
	
	private Mark mark;
	private Number number;
	
	public Card(Mark mark, Number number) {
		this.mark = mark;
		this.number = number;
	}
	
	public String getStrMark() {
		return mark.getMark();
	}
	
	public String getStrNumber() {
		return number.getNumber();
	}
	
	public int getIntPoint() {
		return number.getPoint();
	}
}

enum Mark {
	
	SPADE("♠"),
	HEART("♥"),
	DIAMOND("♦"),
	CLUB("♣");
	
	private String mark;
	
	private Mark(String mark) {
		this.mark = mark;
	}
	
	public String getMark() {
		return mark;
	}
}

enum Number {
	
	ACE("A",1),
	TWO("2",2),
	THREE("3",3),
	FOUR("4",4),
	FIVE("5",5),
	SIX("6",6),
	SEVEN("7",7),
	EIGHT("8",8),
	NINE("9",9),
	TEN("10",10),
	JACK("J",10),
	QUEEN("Q",10),
	KING("K",10);
	
	private String number;
	private int point;
	
	private Number(String number,int point) {
		this.number = number;
		this.point = point;
	}
	
	public String getNumber() {
		return number;
	}
	
	public int getPoint() {
		return point;
	}
}