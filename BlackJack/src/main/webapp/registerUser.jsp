<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>新規登録ページ</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>新規登録ページ</h1>
<%
		String message = (String)request.getAttribute("message");
		if(message != null) {
%>
			<p id="message"><%=message%></p>
<%
		}
%>
		<form action="RegisterServlet" method="POST">
			<table>
				<tr>
					<td>ユーザID</td>
					<td>
						<input type="text" name="user_id" placeholder="半角英数字20文字以内">
					</td>
				</tr>	
				<tr>	
					<td>ニックネーム</td>
					<td>
						<input type="text" name="nickname" placeholder="20文字以内">
					</td>
				</tr>
				<tr>	
					<td>パスワード</td>
					<td>
						<input type="password" name="password" placeholder="半角英数字記号8～20文字">
					</td>
				</tr>	
				<tr>
					<td>パスワード2回目</td>
					<td>
						<input type="password" name="password2" placeholder="再入力">
					</td>
				</tr>	
			</table>
			
			<p>使用不可記号...「&quot; &amp; &lt; &gt;」</p>
			<p><input type="submit" value="登録" class="button"></p>
		</form>
		
			<a href="login.jsp" class="button">ログインページへ戻る</a>
	</body>
</html>
