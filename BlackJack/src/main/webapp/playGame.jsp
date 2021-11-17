<%@page import="model.Deck"%>
<%@page import="model.SplitPlayers"%>
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
		
		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {
			
			SplitPlayers splitPlayers = game.getSplitPlayers();
			Dealer dealer = game.getDealer();
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
			
			
			Card firstCard = dealer.getHand().getListOfHand().get(0);
%>				
			<p>ディーラー</p>
			
			<table>
				<tr>
					<td class="card"><%=firstCard.getMark()%><br><%=firstCard.getNumber()%></td>
					<td class="card">？</td>
					<td class="game_text">
						<%=firstCard.getPoint()%>点
<%
					if (firstCard.getNumber().equals("A")) {
%>
						or <%=firstCard.getPointAce()%>点
<%
					}
%>
						+ ?
					</td>
				</tr>
			</table>
			
			
			<p>あなた</p>
<%
			int index = 0;
			for(Player player: splitPlayers.getList()) {
%>	
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
						<td class="game_text">
<%
						if (!(player.isEnd())) {
%>
							<%=player.getPoint()%>点
<%
							if (!(player.getBurst2()) && (player.getPoint() != player.getPoint2())) {
%>
								or <%=player.getPoint2()%>点
<%
							}
							
						} else {
%>
							<%=player.getPlayerPoint()%>点
<%
						}
%>
						</td>
						<td class="game_text">(BET額：<%=player.getBet()%>)</td>
					</tr>
				</table>
<%
				if (!(player.isEnd())) {
%>
					<table>
						<tr>
							<td>
								<form action="HitServlet" method="POST">
									<input type="hidden" name="index" value="<%=index%>">
									<input type="submit" value="ヒット" class="game_button">
								</form>
							</td>
							<td>
								<form action="StandServlet" method="POST">
									<input type="hidden" name="index" value="<%=index%>">
									<input type="submit" value="スタンド" class="game_button">
								</form>
							</td>
<%
							if (player.canSplit() && splitPlayers.canSplit()) {
%>
							<td>
								<form action="SplitServlet" method="POST">
									<input type="hidden" name="index" value="<%=index%>">
									<input type="submit" value="スプリット" class="game_button">
								</form>
							</td>
<%						
							}
%>
						</tr>
					</table>
<%
				} else {
%>
					<p id="player_message"><%=player.getPlayerMessage()%></p>
<%
				}

				index++;
			}
%>


			<p>----テスト用に山札の中身を列挙----</p>
<%
			Deck deck = game.getDeck();

			for (Card card: deck.getListOfDeck()){
%>
				<p><%=card.getMark()%><%=card.getNumber()%></p>
<%
			}
%>


<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher(map.get("nextPage"));
			rd.forward(request, response);
		}
%>
	</body>
</html>