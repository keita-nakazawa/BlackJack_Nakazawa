package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.User;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserDao userDao = new UserDao();
		String nextPage = new String();

		//UserDaoのコンストラクタ実行時に作成されるメッセージを検出
		if (userDao.getMessage() != null) {
			
			request.setAttribute("message", userDao.getMessage());
			nextPage = "delete.jsp";

		} else {

			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			userDao.doDelete(loginUser);

			//doDeleteメソッド実行時に作成されるメッセージを検出
			if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "delete.jsp";
			
			} else {
				request.setAttribute("message", loginUser.getNickname() + "さんのユーザ情報を削除しました");
				session.invalidate();
				nextPage = "login.jsp";
			}
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
