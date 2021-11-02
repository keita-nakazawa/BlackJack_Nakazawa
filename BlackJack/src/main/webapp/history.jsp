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
		<title>これまでの戦績</title>
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
		User userInfo = (User) request.getAttribute("userInfo");

		//historyListのnullチェックを行えば十分である。
		Map<String, String> map = NullChecker.createMap(historyList);

		if (map.isEmpty()) {
%>
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>

			<h2>これまでの戦績</h2>
			
			<p>総対戦回数：<%=historyList.size()%>回</p>
			<p>勝率：<%=userInfo.getWinRate()%>% (win...<%=userInfo.getWin()%>回、lose...<%=userInfo.getLose()%>回、draw...<%=userInfo.getDraw()%>回)</p>
			
			<table border="1">
				<thead>
					<tr>
						<td>対戦回数</td>
						<td>日時</td>
						<td>結果</td>
					</tr>
				</thead>
				<tbody>
<%
					for(int row = 0; row < historyList.size(); row++) {
%>
					<tr>
						<td><%=row + 1%></td>
						<td><%=historyList.get(row).getTimestamp()%></td>
						<td><%=historyList.get(row).getStrResult()%></td>
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