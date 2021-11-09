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
				現在のチップ所持枚数：<%=loginUser.getChip()%><br>
				<a href="LoginLogoutServlet" class="button">ログアウト</a>
			</p>
			
			<br>
<%
			String message = (String)request.getAttribute("message");
			if(message != null) {
%>
				<p id="message"><%=message%></p>
<%
			}
%>
			<p>BET額を1～10の間で指定し、ゲーム開始ボタンを押してください。</p>
			<form action="GameStartServlet" method="POST">
				<select name="bet">
					<option selected>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
					<option>7</option>
					<option>8</option>
					<option>9</option>
					<option>10</option>
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
						<a href="RankingServlet" class="button">ランキング</a>
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