<%@page import="model.Dealer"%>
<%@page import="model.Player"%>
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
		Player player = game.getPlayer();
		Dealer dealer = game.getDealer();
		
		Map<String, String> map = NullChecker.createMap(game);

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
			
			
			Card card0 = dealer.getHand().getListOfHand().get(0);
%>				
			<p>ディーラー</p>
			<table>
				<tr>
					<td class="card"><%=card0.getMark()%><br><%=card0.getNumber()%></td>
					<td class="card">？</td>
					<td>
						　(<%=card0.getPoint()%>点
<%
						if (card0.getNumber().equals("A")) {
%>
							or <%=card0.getPointAce()%>点
<%
						}
%>
						+ ？点)
					</td>
				</tr>
			</table>
			
			<p>あなた</p>
			<table>
				<tr>
<%
					for(Card card: player.getHand().getListOfHand()) {
%>
						<td class="card">
							<%=card.getMark()%><br><%=card.getNumber()%>
						</td>
<%
					}
%>
					<td>
						　(<%=player.getPoint()%>点
<%
						if ((player.getBurst2() == false) && (player.getPoint() != player.getPoint2())) {
%>
							or <%=player.getPoint2()%>点
<%
						}
%>
						)
					</td>
					<td>　BET額：<%=player.getBet()%></td>
				</tr>
			</table>

			<br>

			<table>
				<tr>
					<td>
						<a href="HitServlet" class="game_button">ヒット</a>
					</td>
					<td>
						<a href="StandServlet" class="game_button">スタンド</a>
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