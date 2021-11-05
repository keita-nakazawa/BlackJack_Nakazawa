<%@page import="model.NullChecker"%>
<%@page import="java.util.Map"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ユーザ情報変更</title>
		<link rel="stylesheet" type="text/css" href="css/game.css">
	</head>

	<body>
		<h1>BlackJack</h1>
<%
		User loginUser = (User)session.getAttribute("loginUser");

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {
%>	
			<p id="logout">
				<%=loginUser.getNickname()%>さんがログイン中<br>
				<a href="LoginLogoutServlet">ログアウト</a>
			</p>
			
			<br>
<%
			String message = (String)request.getAttribute("message");
			if(message != null) {
%>
				<p id="message"><%=message%></p>
<%
			}
%>
			<h2>ユーザ情報変更</h2>
			<form action="EditIdNameServlet" method="POST">
				<table>
					<tr>
						<td>ユーザID</td>
						<td>
							<input type="text" name="user_id" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" value="<%=loginUser.getUserId()%>" required>
						</td>
					</tr>	
					<tr>	
						<td>ニックネーム</td>
						<td>
							<input type="text" name="nickname" pattern="^[^&quot;&amp;&lt;&gt;]{1,20}$" maxlength="20" placeholder="20文字以内" value="<%=loginUser.getNickname()%>" required>
						</td>
					</tr>
				</table>
				
				<p>使用不可記号...「&quot; &amp; &lt; &gt;」</p>
				<p><input type="submit" value="ユーザID、ニックネームを変更"></p>
			</form>	
			
			<form action="EditPassServlet" method="POST">	
				<table>
					<tr>	
						<td>古いパスワード</td>
						<td>
							<input type="password" name="old_password" required>
						</td>
					</tr>
					<tr>
						<td>新しいパスワード</td>
						<td>
							<input type="password" name="new_password" pattern="^[!#-%'-;=?-~]{8,20}$" maxlength="20" placeholder="半角英数字記号8～20文字以内" required>
						</td>
					</tr>	
					<tr>
						<td>新しいパスワード2回目</td>
						<td>
							<input type="password" name="new_password2" pattern="^[!#-%'-;=?-~]{8,20}$" maxlength="20" placeholder="再入力" required>
						</td>
					</tr>	
				</table>
				
				<p>使用不可記号...「&quot; &amp; &lt; &gt;」</p>
				<p><input type="submit" value="パスワードを変更"></p>
			</form>
			
			<br>
			
			<a href="menu.jsp">メニューへ戻る</a>
<%
		} else {
			request.setAttribute("message", map.get("message"));
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
%>
	</body>
</html>
