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
		Map<String, Object> resultMap = (Map)request.getAttribute("resultMap");
		
		if ((loginUser != null) && (game != null)) {
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

			<p id="message"><%=resultMap.get("message")%></p>
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
			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>