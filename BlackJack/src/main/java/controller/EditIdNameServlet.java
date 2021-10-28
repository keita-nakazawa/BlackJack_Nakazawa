package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.User;

@WebServlet("/EditIdNameServlet")
public class EditIdNameServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = request.getParameter("user_id");
		String nickname = request.getParameter("nickname");

		HttpSession session = request.getSession();
		String sessionUserId = ((User) session.getAttribute("loginUser")).getUserId();

		UserDao userDao = new UserDao();
		Map<String, Object> map = userDao.editIdName(userId, nickname, sessionUserId);
		session.setAttribute("loginUser", map.get("loginUser"));
		request.setAttribute("message", map.get("message"));

		RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
		rd.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
