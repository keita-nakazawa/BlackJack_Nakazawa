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

		if (loginUser != null) {
%>
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>
<%
				if (rankingList != null) {
%>
					<h2>勝率ランキングトップ5</h2>
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
<%
				} else {
%>
					<p id="message">不正な操作、URLです。</p>
<%
				}
%>
				<form action="menu.jsp">
					<p><input type="submit" value="メニューへ戻る"></p>
				</form>
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