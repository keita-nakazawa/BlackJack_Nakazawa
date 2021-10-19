package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import exception.MessageManager;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		//入力内容制限を無理やり破っていないかここでチェックする必要がある。
		
		UserDao userDao = new UserDao();
		userDao.doRegister(id, nickname, password, password2);
		
		String nextPage = MessageManager.checkMessage("login.jsp", "register.jsp", request);
		MessageManager.resetMessage();
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	
	}
}
