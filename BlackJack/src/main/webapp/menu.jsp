<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>メニュー</title>
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
						<form action="edit.jsp">
							<input type="submit" value="ユーザ情報変更">
						</form>
					</td>
					<td>
						<form action="delete.jsp">
							<input type="submit" value="ユーザ削除">
						</form>
					</td>
				</tr>
			</table>
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