package exception;

import javax.servlet.http.HttpServletRequest;

/**
 * 発生した例外内容をメッセージとして保管するクラス
 */
public class MessageManager{
	
	private static String message = null;
	
	/**
	 * コンストラクタ<br>
	 * 画面に表示するメッセージ用に引数で受け取った値をセットします
	 * @param message 発生した内容を示すメッセージ
	 */
	public MessageManager(String message) {
		MessageManager.message = message;
	}
	
	public static String getMessage() {
		return message;
	}
	
	public static void resetMessage() {
		MessageManager.message = null;
	}
	
	public static String checkMessage(String nextPage, String thisPage, HttpServletRequest request) {
		if(MessageManager.message == null) {
			return nextPage;
		}else {
			request.setAttribute("message", MessageManager.message);
			return thisPage;
		}
	}
	
}
