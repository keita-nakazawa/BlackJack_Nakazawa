package model;

public class Player extends BasePlayer{

	//BET額
	private int bet;
	//ナチュラルBJの可能性があるならtrue
	private boolean naturalBJFlag = false;
	//スプリット可能な手札ならtrue
	private boolean splitFlag = false;
	//バーストまたは結果待ち状態の手札ならtrue
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
		int signedEachResult = eachResult - bet;
		if (signedEachResult > 0) {
			return "+" + String.valueOf(signedEachResult);
		} else {
			return String.valueOf(signedEachResult);
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

	/**
	 * バーストまたは結果待ち状態の手札ではない場合、山札からカードを1枚引く。<br>
	 * このヒットにより発生するバーストとブラックジャックの判定も行う。
	 */
	public void hit(Deck deck) {
		if (!endFlag) {
			Card newCard = deck.removeCard();
			drawCard(newCard);
			addPoint(newCard);
			setBurst();
			setPlayerPoint();
			setSplitFlag();
			
			if (playerBurst || isBlackJack()) {
				setEndFlag();
				setPlayerMessage();
			}
		}
	}
	
	/**
	 * スプリット可能な手札なら、その2枚目を新たなPlayerオブジェクトに渡し、<br>
	 * 山札から1枚ずつ引いて点数を再計算。BET額も新たなPlayerオブジェクトにコピーする。<br>
	 * ただし、Aのカードをスプリットした場合は強制的にスタンドを行う。
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
			this.hit(deck);
			if (removedCard.getNumber().equals("A")) {
				this.setEndFlag();
				this.setPlayerMessage();
			}
			
			splitPlayer.addPoint(removedCard);
			splitPlayer.hit(deck);
			splitPlayer.bet = bet;
			if (removedCard.getNumber().equals("A")) {
				splitPlayer.setEndFlag();
				splitPlayer.setPlayerMessage();
			}
			
		} else {
			splitPlayer = null;
		}
		return splitPlayer;
	}
	
	public String getResultMessage() {
		int signedEachResult = eachResult - bet;
		
		if (signedEachResult > 0) {
			if (naturalBJFlag) {
				return "NaturalBJ!!";
			} else {
				return "Win";
			}
		} else if (signedEachResult < 0) {
			return "Lose";
		} else {
			return "Draw";
		}
	}

}
