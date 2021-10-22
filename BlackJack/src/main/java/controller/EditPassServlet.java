package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.User;

@WebServlet("/EditPassServlet")
public class EditPassServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("new_password");
		String newPassword2 = request.getParameter("new_password2");

		HttpSession session = request.getSession();
		String sessionUserId = ((User) session.getAttribute("loginUser")).getUserId();

		UserDao userDao = new UserDao();
		String message = userDao.editPassword(oldPassword, newPassword, newPassword2, sessionUserId);
		request.setAttribute("message", message);
		
		RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
		rd.forward(request, response);
	}
}
