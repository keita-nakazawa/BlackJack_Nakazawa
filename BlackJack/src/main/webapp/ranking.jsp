<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ランキング</title>
	</head>

	<body>
<%
		List<User> rankingList = (List)request.getAttribute("rankingList");
%>
	
		<h1>BlackJack</h1>
		
		<p><%=((User)session.getAttribute("loginUser")).getNickname()%>さんがログイン中</p>
		<form action="LoginLogoutServlet">
			<p><input type="submit" value="ログアウト"></p>
		</form>
		
		<br>
		
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
				for(int rank = 1; rank <= 5; rank++) {
%>
				<tr>
					<td><%=rank%></td>
					<td><%=rankingList.get(rank-1).getNickname()%></td>
					<td><%=rankingList.get(rank-1).getWinRate()%></td>
				</tr>
<%
				}
%>
			</tbody>
		</table>
		
		<form action="menu.jsp">
			<p><input type="submit" value="メニューへ戻る"></p>
		</form>
	</body>
</html>