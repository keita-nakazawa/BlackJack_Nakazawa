package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("user_id");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		//入力内容制限を無理やり破っていないかここでチェックする必要がある。
		
		UserDao userDao = new UserDao();
		String message = userDao.getRegisterMessage(userId, nickname, password, password2);
		
		if (message != null) {

			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);

		} else {

			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}
}
