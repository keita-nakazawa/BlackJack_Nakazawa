<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>BlackJack</title>
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
			<p><input type="submit" value="戻る"></p>
		</form>
	</body>
</html>