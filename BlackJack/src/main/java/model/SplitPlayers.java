package model;

import java.util.ArrayList;
import java.util.List;

public class SplitPlayers {

	//スプリットにより増やせる手札の上限値。0の場合は上限値なし。1の場合はスプリット不可。
	private final static int MAX_SPLIT_PLAYER = 0;
	private List<Player> splitPlayerList = new ArrayList<>();
	
	public int getMAXSize() {
		return MAX_SPLIT_PLAYER;
	}
	
	public List<Player> getList() {
		return splitPlayerList;
	}
	
	public Player getPlayer(int index) {
		return splitPlayerList.get(index);
	}
	
	public int getSize() {
		return splitPlayerList.size();
	}

	public void addPlayer(Player player) {
		splitPlayerList.add(player);
	}
	
	public void setPlayer(int index, Player player) {
		splitPlayerList.set(index, player);
	}
	
	/**
	 * {@link Player#split()}により生成したPlayerオブジェクトを、<br>
	 * スプリット元のPlayerオブジェクトの次の要素として挿入
	 * @param index スプリット元のPlayerオブジェクトのインデックス
	 */
	public void splitPlayer(int index, Deck deck) {
		if ((splitPlayerList.size() < MAX_SPLIT_PLAYER) || (MAX_SPLIT_PLAYER == 0)) {
			Player splitPlayer = splitPlayerList.get(index).split(deck);
			if (splitPlayer != null) {
				splitPlayerList.add(index + 1, splitPlayer);
			}
		}
	}
}
