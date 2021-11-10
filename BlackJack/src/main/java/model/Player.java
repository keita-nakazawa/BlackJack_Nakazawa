package model;

public class Player extends BasePlayer{

	private int bet;
	//ナチュラルBJの可能性があるならtrue
	private boolean naturalBJFlag = false;
	//スプリット可能な手札ならtrue
	private boolean splitFlag = false;
	//既に結果の出た手札ならtrue
	private boolean endFlag = false;
	private String playerMessage;
	
	public boolean isNaturalBJ() {
		return naturalBJFlag;
	}

	public void setNaturalBJFlag() {
		naturalBJFlag = true;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getBet() {
		return bet;
	}

	public boolean canSplit() {
		return splitFlag;
	}

	public void setSplitFlag() {
		Card firstCard = hand.getCard(0);
		Card secondCard = hand.getCard(1);
		if ((hand.getSize() == 2) && (firstCard.getPoint() == secondCard.getPoint())) {
			splitFlag = true;
		} else {
			splitFlag = false;
		}
	}
	
	public boolean isEnd() {
		return endFlag;
	}

	public void setEndFlag() {
		endFlag = true;
	}

	public String getPlayerMessage() {
		return playerMessage;
	}

	public void setPlayerMessage(String playerMessage) {
		this.playerMessage = playerMessage;
	}

	public void hit(Deck deck) {
		Card newCard = deck.removeCard();
		drawCard(newCard);
		setPoint(newCard);
		setBurst();
		setPlayerPoint();
	}
	
	/**
	 * 手札が2枚かつ同点時、2枚目を新たなPlayerオブジェクトに渡し、BET額もコピーする。
	 * @return 新たに生成したPlayerオブジェクト
	 */
	public Player split() {
		Player splitPlayer = new Player();
		
		if (splitFlag) {
			splitPlayer.drawCard(hand.removeCard());
			splitPlayer.bet = bet;
		} else {
			splitPlayer = null;
		}
		return splitPlayer;
	}
	
	public boolean isBlackJack() {
		if (getPlayerPoint() == BLACKJACK) {
			return true;
		} else {
			return false;
		}
	}
}
