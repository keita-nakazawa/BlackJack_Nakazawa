<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ユーザ削除</title>
		<style>
			#message {
				color : red;
			}
		</style>
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");

		if (loginUser != null) {
%>	
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
	
			<br>
		
			<p>以下のユーザを削除しますか?</p>
			<table>
				<tr>
					<td>ID：</td>
					<td><%=loginUser.getUserId()%></td>
				</tr>
				<tr>
					<td>ニックネーム：</td>
					<td><%=loginUser.getNickname()%></td>
				</tr>
			</table>
			<form action="DeleteServlet">
				<p><input type="submit" value="削除"></p>
			</form>
			
			<form action="menu.jsp">
				<p><input type="submit" value="メニューへ戻る"></p>
			</form>
<%
		} else {
%>
			<p id="message">不正な操作、URLです。</p>
			<a href="login.jsp">ログインページへ</a>
<%
		}
%>
	</body>
</html>