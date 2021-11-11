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
		SplitPlayers splitPlayers = game.getSplitPlayers();
		Dealer dealer = game.getDealer();
		History history = (History)request.getAttribute("history");
		int previousBet = (Integer)request.getAttribute("previousBet");

		//gameのnullチェックを行えば十分である。
		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {
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
				<td>(<%=dealer.getPlayerPoint()%>点)</td>
				</tr>
			</table>
			
			<p>あなた</p>
			<table>
<%
			for(Player player: splitPlayers.getList()) {
%>
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
					<td>(<%=player.getPlayerPoint()%>点)</td>
					<td><%=player.getResultMessage()%></td>
					<td><%=player.getSignedEachResult()%></td>
				</tr>
<%
			}
%>
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