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
		List<User> rankingList = (List)request.getAttribute("rankingList");
		Integer population = (Integer)request.getAttribute("population");

		Map<String, String> map = NullChecker.createMap(rankingList);

		if (map.isEmpty()) {
%>
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>
<%
			String message = (String)request.getAttribute("message");
			if(message != null) {
%>
				<p id="message"><%=message%></p>
<%
			}
%>
			<h2>勝率ランキングトップ5</h2>
			<p>現在の総プレイ人口：<%=population%>人</p>
			<table border="1">
				<thead>
					<tr>
						<td>順位</td>
						<td>ニックネーム</td>
						<td>勝率(%)</td>
					</tr>
				</thead>
				<tbody>
<%
					for(int row = 0; row < rankingList.size(); row++) {
%>
					<tr>
						<td><%=row + 1%></td>
						<td><%=rankingList.get(row).getNickname()%></td>
						<td><%=rankingList.get(row).getWinRate()%></td>
					</tr>
<%
					}
%>
				</tbody>
			</table>
			
			<form action="menu.jsp">
				<p><input type="submit" value="メニューへ戻る"></p>
			</form>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>