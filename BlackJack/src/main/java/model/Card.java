package model;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Integer> getIntPointList() {
		return number.getPointList();
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
	
	ACE("A",new Integer[]{1, 11}),
	TWO("2",new Integer[]{2}),
	THREE("3",new Integer[]{3}),
	FOUR("4",new Integer[]{4}),
	FIVE("5",new Integer[]{5}),
	SIX("6",new Integer[]{6}),
	SEVEN("7",new Integer[]{7}),
	EIGHT("8",new Integer[]{8}),
	NINE("9",new Integer[]{9}),
	TEN("10",new Integer[]{10}),
	JACK("J",new Integer[]{10}),
	QUEEN("Q",new Integer[]{10}),
	KING("K",new Integer[]{10});
	
	private String number;
	private List<Integer> pointList = new ArrayList<>();
	
	private Number(String number, Integer[] pointArray) {
		this.number = number;
		for (int point: pointArray) {
			pointList.add(point);
		}
	}
	
	public String getNumber() {
		return number;
	}
	
	public List<Integer> getPointList() {
		return pointList;
	}
	
}