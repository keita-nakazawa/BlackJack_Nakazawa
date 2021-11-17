package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.NullChecker;
import model.User;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

			UserDao userDao = new UserDao(session);
			userDao.doDelete(loginUser);

			// userDaoからメッセージを抽出
			if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "deleteUser.jsp";

			} else {
				request.setAttribute("message", loginUser.getNickname() + "さんのユーザ情報を削除しました");
				session.invalidate();
				nextPage = "login.jsp";
			}

		} else {

			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("message", "不正な操作・URLを検知、もしくはセッションタイムアウトです。<br>強制ログアウトしました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
