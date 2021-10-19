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
		<br>
	
		<form action="GameStartServlet">
			<p><input type="submit" value="ゲーム開始"></p>
		</form>
		
		<table>
			<tr>
				<td>
					<form action="HistoryServlet">
						<input type="submit" value="これまでの戦績">
					</form>
				</td>
				<td>
					<form action="RankingServlet">
						<input type="submit" value="勝率ランキングトップ5">
					</form>
				</td>
				<td>
					<form action="EditServlet">
						<input type="submit" value="ユーザ情報変更">
					</form>
				</td>
				<td>
					<form action="DeleteServlet">
						<input type="submit" value="ユーザ削除">
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>