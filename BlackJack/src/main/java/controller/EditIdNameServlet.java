package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import exception.MessageManager;
import model.User;

@WebServlet("/EditIdNameServlet")
public class EditIdNameServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		String nickname = request.getParameter("nickname");

		HttpSession session = request.getSession();
		String sessionUserId = ((User) session.getAttribute("loginUser")).getUserId();

		UserDao userDao = new UserDao();
		User loginUser = userDao.editIdName(userId, nickname, sessionUserId);
		session.setAttribute("loginUser", loginUser);
		
		request.setAttribute("message", MessageManager.getMessage());
		MessageManager.resetMessage();

		RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
		rd.forward(request, response);
	}
}
