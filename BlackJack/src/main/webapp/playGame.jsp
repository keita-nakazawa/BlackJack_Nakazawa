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
		<h1>BlackJack</h1>
		
		<p><%=((User)session.getAttribute("loginUser")).getNickname()%>さんがログイン中</p>
		<form action="LoginLogoutServlet">
			<p><input type="submit" value="ログアウト"></p>
		</form>
		
		<br>
	
		<p>ディーラー( 点)</p>
		
		<p>あなた( 点)</p>
		
		<table>
			<tr>
				<td>
					<form action="HitServlet">
						<input type="submit" value="ヒット">
					</form>
				</td>
				<td>
					<form action="StandServlet">
						<input type="submit" value="スタンド">
					</form>
				</td>
			</tr>
		</table>
		
	</body>
</html>