package exception;

import javax.servlet.http.HttpServletRequest;

/**
 * 発生した例外内容をメッセージとして保管するクラス
 */
public class MessageManager{
	
	private String message = null;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void resetMessage() {
		message = null;
	}
	
	public String checkMessage(String nextPage, String thisPage, HttpServletRequest request) {
		if(message == null) {
			return nextPage;
		}else {
			request.setAttribute("message", message);
			return thisPage;
		}
	}
	
}
