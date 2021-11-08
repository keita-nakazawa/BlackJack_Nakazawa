<%@page import="model.NullChecker"%>
<%@page import="java.util.Map"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>メニュー</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {
%>	
			<p id="logout">
				<%=loginUser.getNickname()%>さんがログイン中<br>
				<a href="LoginLogoutServlet" class="button">ログアウト</a>
			</p>
			
			<br>
			
			<p>現在のチップ所持枚数：</p>
<%
			String message = (String)request.getAttribute("message");
			if(message != null) {
%>
				<p id="message"><%=message%></p>
<%
			}
%>
			<form action="GameStartServlet" method="POST">
				<select name="chip">
					<option value="" selected disabled>--BET額を指定してください--</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
				</select>
				<p><input type="submit" value="ゲーム開始" class="game_button"></p>
			</form>
			
			<br>
			
			<table>
				<tr>
					<td>
						<a href="HistoryServlet" class="button">これまでの戦績</a>
					</td>
					<td>
						<a href="RankingServlet" class="button">勝率ランキング</a>
					</td>
					<td>
						<a href="edit.jsp" class="button">ユーザ情報変更</a>
					</td>
					<td>
						<a href="delete.jsp" class="button">ユーザ削除</a>
					</td>
				</tr>
			</table>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>