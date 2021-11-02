package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// 入力内容制限を無理やり破っていないかここでチェックする必要がある。
		ValidatorBJ validatorBJ = new ValidatorBJ();
		
		
		
		
		
		UserDao userDao = new UserDao();
		String nextPage = new String();

		userDao.doRegister(userId, nickname, password, password2);

		// userDaoからメッセージを抽出
		if (userDao.getMessage() != null) {
			request.setAttribute("message", userDao.getMessage());
			nextPage = "register.jsp";

		} else {
			request.setAttribute("message", "新規登録が完了しました");
			nextPage = "login.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
		rd.forward(request, response);
	}
}
