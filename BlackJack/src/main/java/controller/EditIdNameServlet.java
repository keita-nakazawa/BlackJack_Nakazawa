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

@WebServlet("/EditIdNameServlet")
public class EditIdNameServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

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
				int chip = loginUser.getChip();
				
				String sessionUserId = loginUser.getUserId();
				UserDao userDao = new UserDao(session);
				loginUser = userDao.editIdName(userId, nickname, sessionUserId);
				loginUser.setChip(chip);
				
				// userDaoからメッセージを抽出
				request.setAttribute("message", userDao.getMessage());
				session.setAttribute("loginUser", loginUser);
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
