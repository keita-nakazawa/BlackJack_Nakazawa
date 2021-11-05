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
				<a type="button" href="LoginLogoutServlet">ログアウト</a>
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
			<a href="GameStartServlet">ゲーム開始</a>
			
			<table>
				<tr>
					<td>
						<a href="HistoryServlet">これまでの戦績</a>
					</td>
					<td>
						<a href="RankingServlet">勝率ランキング</a>
					</td>
					<td>
						<a href="edit.jsp">ユーザ情報変更</a>
					</td>
					<td>
						<a href="delete.jsp">ユーザ削除</a>
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