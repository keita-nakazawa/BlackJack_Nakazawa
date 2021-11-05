<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>新規登録ページ</title>
		<style>
			#message {
				color : red;
			}
		</style>
	</head>

	<body>
		<h1>新規登録ページ</h1>
<%
		String message = (String)request.getAttribute("message");
		if(message != null) {
%>
			<p id="message"><%=message%></p>
<%
		}
%>
		<form action="RegisterServlet" method="POST">
			<table>
				<tr>
					<td>ユーザID</td>
					<td>
						<input type="text" name="user_id" pattern="^[0-9A-Za-z]{1,20}$" maxlength="20" placeholder="半角英数字20文字以内" required>
					</td>
				</tr>	
				<tr>	
					<td>ニックネーム</td>
					<td>
						<input type="text" name="nickname" pattern="^[^&quot;&amp;&lt;&gt;]{1,20}$" maxlength="20" placeholder="20文字以内" required>
					</td>
				</tr>
				<tr>	
					<td>パスワード</td>
					<td>
						<input type="password" name="password" pattern="^[!#-%'-;=?-~]{8,20}$" maxlength="20" placeholder="半角英数字記号8～20文字" required>
					</td>
				</tr>	
				<tr>
					<td>パスワード2回目</td>
					<td>
						<input type="password" name="password2" pattern="^[!#-%'-;=?-~]{8,20}$" maxlength="20" placeholder="再入力" required>
					</td>
				</tr>	
			</table>
			
			<p>使用不可記号...「&quot; &amp; &lt; &gt;」</p>
			<p><input type="submit" value="登録"></p>
		</form>
		
			<a href="login.jsp">メニューへ戻る</a>
	</body>
</html>
