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
		Game game = (Game)session.getAttribute("game");

		if (loginUser != null) {
%>	
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>
<%
			if (game != null) {
%>	
				<p>ディーラー</p>
				<table>
					<tr>
<%
						Card card0 = game.getDealer().getHand().getListOfHand().get(0);
%>
						<td><%=card0.getStrMark()%><%=card0.getStrNumber()%></td>
						<td>?</td>
					</tr>
				</table>
				
				<p>あなた(<%=game.getPlayer().getPoint()%>点)(burst = <%=game.getPlayer().getBurst()%>)</p>
					<table>
						<tr>
<%
						for(Card card: game.getPlayer().getHand().getListOfHand()) {
%>
							<td><%=card.getStrMark()%><%=card.getStrNumber()%></td>
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
<%
			} else {
%>
				<p id="message">不正な操作、URLです。</p>
<%
			}
		} else {
%>
			<p id="message">不正な操作、URLです。</p>
			<a href="login.jsp">ログインページへ</a>
<%
		}
%>
	</body>
</html>