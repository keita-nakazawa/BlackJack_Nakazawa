<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ユーザ情報変更</title>
		<style>
			#message {
				color : red;
			}
		</style>
	</head>

	<body>
<%
		User loginUser = (User)session.getAttribute("loginUser");
%>	
		<h1>BlackJack</h1>
		
		<p><%=loginUser.getNickname()%>さんがログイン中</p>
		<form action="LoginLogoutServlet">
			<p><input type="submit" value="ログアウト"></p>
		</form>
		
		<br>
<%
		if(request.getAttribute("message") != null) {
			String message = (String)request.getAttribute("message");
%>
			<p id="message"><%=message%></p>
<%
		}
%>
		<h2>ユーザ情報変更</h2>
		<form action="EditIdNameServlet" method="POST">
			<table>
				<tr>
					<td>ユーザID</td>
					<td>
						<input type="text" name="user_id" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" value="<%=loginUser.getUserId()%>" required>
					</td>
				</tr>	
				<tr>	
					<td>ニックネーム</td>
					<td>
						<input type="text" name="nickname" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" value="<%=loginUser.getNickname()%>" required>
					</td>
				</tr>
			</table>
			<p><input type="submit" value="ユーザID、ニックネームを変更"></p>
		</form>	
		
		<form action="EditPassServlet" method="POST">	
			<table>
				<tr>	
					<td>古いパスワード</td>
					<td>
						<input type="password" name="old_password" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" required>
					</td>
				</tr>
				<tr>
					<td>新しいパスワード</td>
					<td>
						<input type="password" name="new_password" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" required>
					</td>
				</tr>	
				<tr>
					<td>新しいパスワード2回目</td>
					<td>
						<input type="password" name="new_password2" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="再入力" required>
					</td>
				</tr>	
			</table>
			<p><input type="submit" value="パスワードを変更"></p>
		</form>
		
		<br>
		
		<form action="menu.jsp">
				<p><input type="submit" value="戻る"></p>
		</form>
	</body>
</html>
