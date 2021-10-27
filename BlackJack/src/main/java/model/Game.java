package model;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private Deck deck;
	private Player player;
	private Dealer dealer;

	public Game() {
		
		Player player = new Player();
		Dealer dealer = new Dealer();
		Deck deck = new Deck();

		for (int i = 0; i < 2; i++) {
			player.drawCard(deck.removeCard());
			dealer.drawCard(deck.removeCard());
		}
		player.setPoint();
		dealer.setPoint();
		
		this.deck = deck;
		this.player = player;
		this.dealer = dealer;
	}

	public Deck getDeck() {
		return deck;
	}

	public Player getPlayer() {
		return player;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return Game<br>パラメータがnullの場合は自身を、nullでない場合はパラメータを返す。
	 */
	public Game start(Game oldGame) {
		
		if (oldGame == null) {
			return this;
		} else {
			return oldGame;
		}
	}
	
	/**
	 * プレイヤーとディーラーの点数を比較する。burstフラグも考慮する。
	 * 
	 * @return Map<br>
	 * ・"result"キーで勝敗結果を取得する。<br>
	 * -1: ディーラーの勝利<br>
	 * 0: 引き分け<br>
	 * 1: プレイヤーの勝利<br>
	 * ・"message"キーで勝敗結果に基づくメッセージを取得する。
	 */
	public Map<String, Object> comparePoints() {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(player.getBurst() == true) {
			
			resultMap.put("result", -1);
			resultMap.put("message", "バーストしました。</br>ディーラーの勝利です。");
		
		} else if (dealer.getBurst() == true) {
			
			resultMap.put("result", 1);
			resultMap.put("message", "ディーラーがバーストしました。</br>あなたの勝利です。");
		
		} else {
			
			if (dealer.getPoint() > player.getPoint()) {
				
				resultMap.put("result", -1);
				resultMap.put("message", "ディーラーの勝利です。");
			
			} else if (dealer.getPoint() == player.getPoint()) {
			
				resultMap.put("result", 0);
				resultMap.put("message", "引き分けです。");
			
			} else {
			
				resultMap.put("result", 1);
				resultMap.put("message", "あなたの勝利です。");
			}
		}
		return resultMap;
	}
}
