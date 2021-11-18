<%@page import="model.Dealer"%>
<%@page import="model.SplitPlayers"%>
<%@page import="model.Player"%>
<%@page import="model.History"%>
<%@page import="model.NullChecker"%>
<%@page import="java.util.Map"%>
<%@page import="model.Card"%>
<%@page import="model.Game"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ゲーム終了</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");
		Game game = (Game)request.getAttribute("game");
		History history = (History)request.getAttribute("history");
		int previousBet = (Integer)request.getAttribute("previousBet");

		//gameのnullチェックを行えば十分である。
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
			
			<p>ディーラー</p>
			<table>
				<tr>
<%
				for(Card card: dealer.getHand().getListOfHand()) {
%>
					<td class="card">
						<%=card.getMark()%><br><%=card.getNumber()%>
					</td>
<%
				}
%>
				<td class="game_text">(<%=dealer.getPlayerPoint()%>点)</td>
				</tr>
			</table>
			
			<p>あなた</p>
			<table>
<%
			for(Player player: splitPlayers.getList()) {
%>
				<tr>
<%
				for(int i = 0; i < splitPlayers.getMaxHandSize(); i++) {
					if (i < player.getHand().getSize()) {
						Card card = player.getHand().getCard(i);
%>
						<td class="card">
							<%=card.getMark()%><br><%=card.getNumber()%>
						</td>
<%
					} else {
%>
						<td></td>
<%
					}
				}
%>
					<td class="game_text">(<%=player.getPlayerPoint()%>点)</td>
					<td class="game_text"><%=player.getResultMessage()%></td>
					<td class="game_text">　<%=player.getSignedEachResult()%></td>
				</tr>
<%
			}
%>
				<tr>
<%
				for(int i = 0; i < splitPlayers.getMaxHandSize() + 1; i++) {
%>
					<td></td>
<%
				}

				if (splitPlayers.getSize() > 1) {
%>
					<td class="game_text">合計チップ獲得数</td>
					<td class="game_text">　<%=history.getSignedResult()%></td>
<%
				}
%>
				</tr>
			</table>
			
			

			<br>

			<form action="GameStartServlet" method="POST">
				<input type="hidden" name="bet" value="<%=previousBet%>">
				<input type="submit" value="同じBET額(<%=previousBet%>)で再戦" class="game_button">
			</form>
		
			<p><a href="menu.jsp" class="button">メニューへ戻る</a></p>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>