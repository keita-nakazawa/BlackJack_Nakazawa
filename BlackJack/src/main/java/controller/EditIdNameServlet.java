package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.User;
import model.ValidatorBJ;

@WebServlet("/EditIdNameServlet")
public class EditIdNameServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("user_id");
		String nickname = request.getParameter("nickname");

		ValidatorBJ validatorBJ = new ValidatorBJ();
		validatorBJ.putStr("userId", userId);
		validatorBJ.putStr("nickname", nickname);
		validatorBJ.excuteValidation();

		// validatorBJからメッセージを抽出
		if (validatorBJ.getMessage() != null) {
			request.setAttribute("message", validatorBJ.getMessage());

		} else {
			UserDao userDao = new UserDao();
			HttpSession session = request.getSession();
			String sessionUserId = ((User) session.getAttribute("loginUser")).getUserId();
			User loginUser = userDao.editIdName(userId, nickname, sessionUserId);

			// userDaoからメッセージを抽出
			request.setAttribute("message", userDao.getMessage());
			session.setAttribute("loginUser", loginUser);
		}

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
