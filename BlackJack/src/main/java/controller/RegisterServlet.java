package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.ValidatorBJ;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter("user_id");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		ValidatorBJ validatorBJ = new ValidatorBJ();
		validatorBJ.putStr("userId", userId);
		validatorBJ.putStr("nickname", nickname);
		validatorBJ.putStr("password", password);
		validatorBJ.putStr("password2", password2);
		validatorBJ.excuteValidation();

		String nextPage = new String();
		
		//validatorBJからメッセージを抽出
		if (validatorBJ.getMessage() != null) {
			
			request.setAttribute("message", validatorBJ.getMessage());
			nextPage = "registerUser.jsp";
			
		} else {

			HttpSession session = request.getSession();
			UserDao userDao = new UserDao(session);
			userDao.doRegister(userId, nickname, password, password2);

			// userDaoからメッセージを抽出
			if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "registerUser.jsp";

			} else {
				request.setAttribute("message", "新規登録が完了しました");
				nextPage = "login.jsp";
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("registerUser.jsp");
		rd.forward(request, response);
	}
}
