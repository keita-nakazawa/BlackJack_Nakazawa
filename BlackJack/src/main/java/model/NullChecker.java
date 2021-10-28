package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullChecker {

	/**
	 * RedirectServletにて使用。<br>
	 * パラメータのリスト内にnullが含まれているかチェックし、<br>
	 * その結果に応じてメッセージと遷移先名を生成する。
	 * 
	 * @param objectList nullチェックの対象となるオブジェクトのリスト。<br>
	 * ただし、0番目の要素は遷移前のサーブレット名またはページ名。
	 * 
	 * @return Map&lt;String, String><br>
	 * "message"キーでメッセージ取得。<br>
	 * "nextPage"キーで遷移先のサーブレット名またはページ名取得。
	 */
	public static Map<String, String> createMap(List<Object> objectList) {

		Map<String, String> map = new HashMap<>();

		int nullFlag = 0;

		for (Object object : objectList) {
			if (object == null) {
				nullFlag = 1;
				break;
			}
		}

		if (nullFlag == 1) {
			map.put("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			map.put("nextPage", "LoginLogoutServlet");
		} else {
			String thisPage = (String)objectList.get(0);
			map.put("message", null);
			map.put("nextPage", thisPage);
		}
		return map;
	}
}
