<%@page import="model.NullChecker"%>
<%@page import="java.util.Map"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ユーザ削除</title>
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

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {
%>	
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
	
			<br>
<%
			if(request.getAttribute("message") != null) {
				String message = (String)request.getAttribute("message");
%>
				<p id="message"><%=message%></p>
<%
			}
%>
			<p>以下のユーザを削除しますか?</p>
			<table>
				<tr>
					<td>ID：</td>
					<td><%=loginUser.getUserId()%></td>
				</tr>
				<tr>
					<td>ニックネーム：</td>
					<td><%=loginUser.getNickname()%></td>
				</tr>
			</table>
			<form action="DeleteServlet" method="POST">
				<p><input type="submit" value="削除"></p>
			</form>
			
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