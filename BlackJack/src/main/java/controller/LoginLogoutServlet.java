package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.User;

@WebServlet("/LoginLogoutServlet")
public class LoginLogoutServlet extends HttpServlet {

	// ログイン処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");

		UserDao userDao = new UserDao();
		User loginUser = userDao.getLoginUser(userId, password);
		
		String nextPage = new String();

		// userDaoからメッセージを抽出
		if (userDao.getMessage() != null) {
			request.setAttribute("message", userDao.getMessage());
			nextPage = "login.jsp";

		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			nextPage = "menu.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	// ログアウト処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}
}
