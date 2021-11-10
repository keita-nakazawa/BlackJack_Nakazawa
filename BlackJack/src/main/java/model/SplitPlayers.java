package model;

import java.util.ArrayList;
import java.util.List;

public class SplitPlayers {

	private List<Player> splitPlayerList = new ArrayList<>();
	
	public List<Player> getList() {
		return splitPlayerList;
	}
	
	public Player getPlayer(int index) {
		return splitPlayerList.get(index);
	}
	
	public int getSize() {
		return splitPlayerList.size();
	}
	
	/**
	 * スプリット処理により生成したPlayerオブジェクトを、<br>
	 * スプリット元のPlayerオブジェクトの次の要素として挿入
	 * @param playerIndex スプリット元のPlayerオブジェクトを示すインデックス
	 */
	public void splitPlayer(int playerIndex) {
		Player splitPlayer = splitPlayerList.get(playerIndex).split();
		if (splitPlayer != null) {
			splitPlayerList.add(playerIndex + 1, splitPlayer);
		}
	}
}
