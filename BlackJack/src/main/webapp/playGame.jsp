<%@page import="java.util.Map"%>
<%@page import="model.NullChecker"%>
<%@page import="model.Card"%>
<%@page import="model.Game"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>BlackJack</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");
		Game game = (Game)session.getAttribute("game");
		
		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {
%>	
			<p id="logout">
				<%=loginUser.getNickname()%>さんがログイン中<br>
				<a href="LoginLogoutServlet">ログアウト</a>
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
			<p>ディーラー</p>
			<table>
				<tr>
<%
					Card card0 = game.getDealer().getHand().getListOfHand().get(0);
%>
					<td class="card"><%=card0.getStrMark()%><br><%=card0.getStrNumber()%></td>
					<td class="card">？</td>
				</tr>
			</table>
			
			<p>あなた(<%=game.getPlayer().getPoint()%>点)</p>
				<table>
					<tr>
<%
					for(Card card: game.getPlayer().getHand().getListOfHand()) {
%>
						<td class="card"><%=card.getStrMark()%><br><%=card.getStrNumber()%></td>
<%
					}
%>
					</tr>
				</table>
		
			<table>
				<tr>
					<td>
						<a href="HitServlet">ヒット</a>
					</td>
					<td>
						<a href="StandServlet">スタンド</a>
					</td>
				</tr>
			</table>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher(map.get("nextPage"));
			rd.forward(request, response);
		}
%>
	</body>
</html>