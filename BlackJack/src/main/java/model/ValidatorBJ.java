package model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidatorBJ {

	// 半角英数字で20文字以内
	private static final Pattern userIdPattern = Pattern.compile("^[0-9A-Za-z]{1,20}$");
	// 「" & < >」以外の文字で20文字以内
	private static final Pattern nicknamePattern = Pattern.compile("^[^\"&<>]{1,20}$");
	// 半角英数字または「" < >」以外の記号で20文字以内
	private static final Pattern passwordPattern = Pattern.compile("^[!#-%'-;=?-~]{8,20}$");
	//1～10の半角数字
	private static final Pattern betPattern = Pattern.compile("^[1-9]$|^10$");
	//半角数字
	private static final Pattern indexPattern = Pattern.compile("[0-9]");
	
	private Map<String, String> strMap = new HashMap<>();
	private String message;

	public void excuteValidation() {

		boolean match;
		for (String key : strMap.keySet()) {

			String value = strMap.get(key);

			switch (key) {
			
			case "userId":
				match = userIdPattern.matcher(value).find();
				break;
				
			case "nickname":
				match = nicknamePattern.matcher(value).find();
				break;
				
			case "password":
			case "password2":
			case "newPassword":
			case "newPassword2":
				match = passwordPattern.matcher(value).find();
				break;
				
			case "bet":
				match = betPattern.matcher(value).find();
				break;
				
			case "index":
				match = indexPattern.matcher(value).find();
				break;
				
			default:
				match = false;
				message = "バリデーションを実行できませんでした。";
				break;
			}
			
			if (!match && (message == null)) {
				message = "指定されている形式で入力してください。";
			}
		}
	}

	public void putStr(String key, String value) {
		strMap.put(key, value);
	}

	public String getMessage() {
		return message;
	}
}
