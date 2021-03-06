<%@page import="model.NullChecker"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ランキング</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");
		List<User> rankingList = (List)request.getAttribute("rankingList");
		Integer population = (Integer)request.getAttribute("population");

		Map<String, String> map = NullChecker.createMap(rankingList);

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
			<h2>ランキングトップ5</h2>
			<p>現在の総プレイ人口：<%=population%>人</p>
			<table border="1">
				<thead>
					<tr>
						<td>順位</td>
						<td>ニックネーム</td>
						<td>チップ所持枚数</td>
					</tr>
				</thead>
				<tbody>
<%
					for(int row = 0; row < rankingList.size(); row++) {
%>
					<tr>
						<td><%=row + 1%></td>
						<td><%=rankingList.get(row).getNickname()%></td>
						<td><%=rankingList.get(row).getChip()%></td>
					</tr>
<%
					}
%>
				</tbody>
			</table>
			
			<br>
			
			<a href="menu.jsp" class="button">メニューへ戻る</a>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>