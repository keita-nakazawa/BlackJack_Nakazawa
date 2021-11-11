package model;

public class Player extends BasePlayer{

	//BET額
	private int bet;
	//ナチュラルBJの可能性があるならtrue
	private boolean naturalBJFlag = false;
	//スプリット可能な手札ならtrue
	private boolean splitFlag = false;
	//既に結果の出た手札ならtrue
	private boolean endFlag = false;
	//チップ収支
	private int eachResult;
	//"バースト" or "結果待ち" 
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

	public int getEachResult() {
		return eachResult;
	}

	public String getSignedEachResult() {
		if (eachResult > 0) {
			return "+" + String.valueOf(eachResult);
		} else {
			return String.valueOf(eachResult);
		}
	}

	public void setEachResult(int eachResult) {
		this.eachResult = eachResult;
	}

	public String getPlayerMessage() {
		return playerMessage;
	}

	public void setPlayerMessage() {
		if (playerBurst) {
			playerMessage = "バースト";
		} else {
			playerMessage = "結果待ち";
		}
	}

	public boolean isBlackJack() {
		if (getPlayerPoint() == BLACKJACK) {
			return true;
		} else {
			return false;
		}
	}

	public void hit(Deck deck) {
		if (!endFlag) {
			Card newCard = deck.removeCard();
			drawCard(newCard);
			addPoint(newCard);
			setBurst();
			setPlayerPoint();
		}
	}
	
	/**
	 * 手札が2枚かつ同点時、2枚目を新たなPlayerオブジェクトに渡し、<br>
	 * 山札から1枚ずつ引いて点数を再計算。BET額もコピーする。
	 * @return 新たに生成したPlayerオブジェクト
	 */
	public Player split(Deck deck) {
		Player splitPlayer = new Player();
		
		if (splitFlag && !endFlag) {
			Card removedCard = hand.removeCard();
			splitPlayer.drawCard(removedCard);
			
			this.point = 0;
			this.point2 = 0;
			this.addPoint(removedCard);
			Card deckCard = deck.removeCard();
			this.drawCard(deckCard);
			this.addPoint(deckCard);
			this.setBurst();
			this.setPlayerPoint();
			this.setSplitFlag();
			if (this.playerPoint == BLACKJACK) {
				this.setEndFlag();
				this.setPlayerMessage();
			}
			
			splitPlayer.addPoint(removedCard);
			deckCard = deck.removeCard();
			splitPlayer.drawCard(deckCard);
			splitPlayer.addPoint(deckCard);
			splitPlayer.setBurst();
			splitPlayer.setPlayerPoint();
			splitPlayer.setSplitFlag();
			if (splitPlayer.playerPoint == BLACKJACK) {
				splitPlayer.setEndFlag();
				splitPlayer.setPlayerMessage();
			}
			splitPlayer.bet = bet;
			
		} else {
			splitPlayer = null;
		}
		return splitPlayer;
	}
	
	public String getResultMessage() {
		if (eachResult > 0) {
			if (naturalBJFlag) {
				return "NaturalBJ!!";
			} else {
				return "Win";
			}
		} else if (eachResult < 0) {
			return "Lose";
		} else {
			return "Draw";
		}
	}

}
