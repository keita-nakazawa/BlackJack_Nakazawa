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
%>
	
		<h1>BlackJack</h1>
		
		<p><%=((User)session.getAttribute("loginUser")).getNickname()%>さんがログイン中</p>
		<form action="LoginLogoutServlet">
			<p><input type="submit" value="ログアウト"></p>
		</form>
		
		<br>
	
		<p>ディーラー( 点)</p>
		<table>
			<tr>
<%
				for(Card card: game.getDealer().getHand().getListOfHand()) {
%>
					<td><%=card.getStrMark()%><%=card.getNumber()%></td>
<%
				}
%>
			</tr>
		</table>
		<p>あなた( 点)</p>
			<table>
			<tr>
<%
				for(Card card: game.getPlayer().getHand().getListOfHand()) {
%>
					<td><%=card.getStrMark()%><%=card.getNumber()%></td>
<%
				}
%>
			</tr>
		</table>
		
		
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
		
	</body>
</html>