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
	</head>

	<body>
<%
		Game game = (Game)session.getAttribute("game");
		String message = (String)session.getAttribute("message");
%>
	
		<h1>BlackJack</h1>
		
		<p><%=((User)session.getAttribute("loginUser")).getNickname()%>さんがログイン中</p>
		<form action="LoginLogoutServlet">
			<p><input type="submit" value="ログアウト"></p>
		</form>
		
		<br>
	
		<p>ディーラー</p>
		<table>
			<tr>
<%
			if(game.getGameEnd() == false) {
				
				Card card0 = game.getDealer().getHand().getListOfHand().get(0);
%>
				<td><%=card0.getStrMark()%><%=card0.getStrNumber()%></td>
				<td>?</td>
<%
			} else {
				for(Card dealerCard: game.getDealer().getHand().getListOfHand()) {
%>
					<td><%=dealerCard.getStrMark()%><%=dealerCard.getStrNumber()%></td>
<%
				}
			}
%>
			</tr>
		</table>
		
		<p>あなた(<%=game.getPlayer().getPoint()%>点)(burst = <%=game.getPlayer().getBurst()%>)</p>
			<table>
				<tr>
<%
				for(Card playerCard: game.getPlayer().getHand().getListOfHand()) {
%>
					<td><%=playerCard.getStrMark()%><%=playerCard.getStrNumber()%></td>
<%
				}
%>
				</tr>
			</table>
<%
		if(game.getGameEnd() == false) {
%>		
			<table>
				<tr>
					<td>
						<form action="HitServlet">
							<input type="submit" value="ヒット">
						</form>
					</td>
					<td>
						<form action="StandServlet">
							<input type="submit" value="スタンド">
						</form>
					</td>
				</tr>
			</table>
<%
		} else {
%>
			<p><%=message%></p>
			<table>
				<tr>
					<td>
						<form action="GameStartServlet">
							<input type="submit" value="再戦">
						</form>
					</td>
					<td>
						<form action="menu.jsp">
							<input type="submit" value="ゲーム終了">
						</form>
					</td>
				</tr>
			</table>
<%
		}
%>		
	</body>
</html>