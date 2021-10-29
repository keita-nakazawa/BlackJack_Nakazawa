package model;

import java.util.HashMap;
import java.util.Map;

public class NullChecker {

	/**
	 * パラメータのオブジェクトのnullチェックを行い、<br>
	 * nullの場合は警告メッセージと遷移先名を生成する。
	 * 
	 * @param object nullチェックの対象となるオブジェクト。<br>
	 * 
	 * @return Map&lt;String, String><br>
	 *         "message"キーで警告メッセージ取得。<br>
	 *         "nextPage"キーで遷移先名"LoginLogoutServlet"を取得。
	 */
	public static Map<String, String> createMap(Object object) {

		Map<String, String> map = new HashMap<>();

		if (object == null) {
			map.put("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			map.put("nextPage", "LoginLogoutServlet");
		}

		return map;
	}
}
