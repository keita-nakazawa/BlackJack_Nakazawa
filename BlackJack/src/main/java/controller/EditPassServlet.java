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
import model.ValidatorBJ;

@WebServlet("/EditPassServlet")
public class EditPassServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

			String oldPassword = request.getParameter("old_password");
			String newPassword = request.getParameter("new_password");
			String newPassword2 = request.getParameter("new_password2");

			ValidatorBJ validatorBJ = new ValidatorBJ();
			validatorBJ.putStr("newPassword", newPassword);
			validatorBJ.putStr("newPassword2", newPassword2);
			validatorBJ.excuteValidation();

			// validatorBJからメッセージを抽出
			if (validatorBJ.getMessage() != null) {
				request.setAttribute("message", validatorBJ.getMessage());

			} else {
				UserDao userDao = new UserDao(session);
				String sessionUserId = loginUser.getUserId();
				userDao.editPassword(oldPassword, newPassword, newPassword2, sessionUserId);

				// userDaoからメッセージを抽出
				request.setAttribute("message", userDao.getMessage());
			}
			nextPage = "edit.jsp";

		} else {

			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
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
