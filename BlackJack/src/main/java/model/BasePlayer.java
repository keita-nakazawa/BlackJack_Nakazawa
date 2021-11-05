package model;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePlayer {
	
	protected Hand hand = new Hand();
	protected List<Integer> pointList = new ArrayList<>();
	protected List<Boolean> burstList = new ArrayList<>();
	
	public void drawCard(Card card) {
		hand.addCard(card);
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public List<Integer> getPointList() {
		return pointList;
	}
	
	public List<Boolean> getBurstList() {
		return burstList;
	}
	
	public void setPointList() {
		for(Card card: hand.getListOfHand()) {
			for (int point: card.getIntPointList()) {
				//
			}
		}
	}
	
	public void setBurstList() {
		 
	}
}
