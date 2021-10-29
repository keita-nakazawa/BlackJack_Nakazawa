<%@page import="model.History"%>
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
		List<History> historyList = (List) request.getAttribute("historyList");

		Map<String, String> map = NullChecker.createMap("");

		if (map.isEmpty()) {
%>
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>

			<h2>これまでの戦績</h2>
			
			<p>総対戦回数：回</p>
			<p>勝率：% (回勝利、回敗北)</p>
			
			<table border="1">
				<thead>
					<tr>
						<td>対戦回数</td>
						<td>日時</td>
						<td>勝敗</td>
					</tr>
				</thead>
				<tbody>
<%
					for(int rank = 0; rank < rankingList.size(); rank++) {
%>
					<tr>
						<td><%=rank + 1%></td>
						<td><%=rankingList.get(rank).getNickname()%></td>
						<td><%=rankingList.get(rank).getWinRate()%></td>
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