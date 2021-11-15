package model;

import java.util.ArrayList;
import java.util.List;

public class SplitPlayers {

	//スプリットにより増やせる手札の上限値。0の場合は上限値なし。1の場合はスプリット不可を意味する。
	private final static int MAX_SPLIT_PLAYER = 0;
	private List<Player> splitPlayerList = new ArrayList<>();
	//手札の数が上限値未満ならtrue。
	private boolean allSplitFlag = true;
	//ゲーム終了時に返ってきたチップの枚数
	private int returnedChip;
	
	public List<Player> getList() {
		return splitPlayerList;
	}
	
	public Player getPlayer(int index) {
		return splitPlayerList.get(index);
	}
	
	public int getSize() {
		return splitPlayerList.size();
	}

	public boolean canSplit() {
		return allSplitFlag;
	}

	public int getReturnedChip() {
		return returnedChip;
	}

	public void addPlayer(Player player) {
		splitPlayerList.add(player);
	}
	
	public void setPlayer(int index, Player player) {
		splitPlayerList.set(index, player);
	}

	@SuppressWarnings("unused")
	public void setAllSplitFlag() {
		if ((splitPlayerList.size() < MAX_SPLIT_PLAYER) || (MAX_SPLIT_PLAYER == 0)) {
			allSplitFlag = true;
		} else {
			allSplitFlag = false;
		}
	}

	public void setReturnedChip(int returnedChip) {
		this.returnedChip = returnedChip;
	}

	/**
	 * {@link Player#split()}により生成したPlayerオブジェクトを、<br>
	 * スプリット元のPlayerオブジェクトの次の要素として挿入
	 * @param index スプリット元のPlayerオブジェクトのインデックス
	 */
	public void splitPlayer(int index, Deck deck) {
		if (allSplitFlag) {
			Player splitPlayer = splitPlayerList.get(index).split(deck);
			if (splitPlayer != null) {
				splitPlayerList.add(index + 1, splitPlayer);
			}
		}
	}
	
	/**
	 * @return プレイヤーのすべての手札をチェックし、最大枚数を求める。
	 */
	public int getMaxHandSize() {
		int max = 0;
		
		for (Player player: splitPlayerList) {
			int handSize = player.getHand().getSize();
			if (handSize > max) {
				max = handSize;
			}
		}
		
		return max;
	}
}
