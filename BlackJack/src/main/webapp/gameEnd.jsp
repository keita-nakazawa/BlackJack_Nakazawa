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
		Game game = (Game)request.getAttribute("game");
		String gameMessage = (String)request.getAttribute("gameMessage");

		//gameのnullチェックを行えば十分である。
		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {
%>
			<p><%=loginUser.getNickname()%>さんがログイン中</p>
			<form action="LoginLogoutServlet">
				<p><input type="submit" value="ログアウト"></p>
			</form>
			
			<br>
			
			<p>ディーラー(<%=game.getDealer().getPoint()%>点)</p>
			<table>
				<tr>
<%
				for(Card card: game.getDealer().getHand().getListOfHand()) {
%>
					<td><%=card.getStrMark()%><%=card.getStrNumber()%></td>
<%
				}
%>
				</tr>
			</table>
			
			<p>あなた(<%=game.getPlayer().getPoint()%>点)</p>
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

			<p id="message"><%=gameMessage%></p>
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
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>