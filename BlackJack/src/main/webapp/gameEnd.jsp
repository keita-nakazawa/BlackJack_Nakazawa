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
		String gameMessage = (String)request.getAttribute("gameMessage");

		//gameのnullチェックを行えば十分である。
		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {
%>
			<p id="logout">
				<%=loginUser.getNickname()%>さんがログイン中<br>
				<a href="LoginLogoutServlet">ログアウト</a>
			</p>
			
			<br>
			
			<p>ディーラー(<%=game.getDealer().getPoint()%>点)</p>
			<table>
				<tr>
<%
				for(Card card: game.getDealer().getHand().getListOfHand()) {
%>
					<td class="card"><%=card.getStrMark()%><br><%=card.getStrNumber()%></td>
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
					<td class="card"><%=card.getStrMark()%><br><%=card.getStrNumber()%></td>
<%
				}
%>
				</tr>
			</table>

			<p id="message"><%=gameMessage%></p>
			<table>
				<tr>
					<td>
						<a href="GameStartServlet">再戦</a>
					</td>
					<td>
						<a href="menu.jsp">ゲーム終了</a>
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