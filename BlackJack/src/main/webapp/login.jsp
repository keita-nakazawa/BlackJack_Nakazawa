<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ログインページ</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>ログインページ</h1>
<%
		String message = (String)request.getAttribute("message");
		if(message != null) {
%>
			<p id="message"><%=message%></p>
<%
		}
%>
		<form action="LoginLogoutServlet" method="POST">
			<table>
				<tr>
					<td>ユーザID</td>
					<td><input type="text" name="user_id"></td>
				</tr>
				<tr>
					<td>パスワード</td>
					<td><input type="password" name="password"></td>
				</tr>
			</table>
			
			<p><input type="submit" value="ログイン" class="button"></p>
		</form>
		
		<a href="registerUser.jsp" class="button">新規登録はこちら</a>
	</body>
</html>