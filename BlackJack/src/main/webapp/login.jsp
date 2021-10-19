<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ログインページ</title>
		<style>
			#message {
				color : red;
			}
		</style>
	</head>

	<body>
		<h1>ログインページ</h1>
<%
		if(request.getAttribute("message") != null) {
			String message = (String)request.getAttribute("message");
%>
			<p id="message"><%=message%></p>
<%
		}
%>	
		<form action="LoginLogoutServlet" method="POST">
			<table>
				<tr>
					<td>ID</td>
					<td><input type="text" name="id" pattern="^[0-9A-Za-z]+$" placeholder="半角英数字" required></td>
				</tr>
				<tr>
					<td>パスワード</td>
					<td><input type="password" name="password" pattern="^[0-9A-Za-z]+$" placeholder="半角英数字" required></td>
				</tr>
			</table>
			<p><input type="submit" value="ログイン"></p>
		</form>
		
		<form action="register.jsp">
			 <p><input type="submit" value="新規登録はこちら"></p>
		</form>
	</body>
</html>