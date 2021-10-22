package manager_model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

public class RedirectManager {

	public static void registerOrNot(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (message != null) {

			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);

		} else {

			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}
	
	public static void loginOrNot(User loginUser, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(loginUser != null) {
			
			session.setAttribute("loginUser", loginUser);
			RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
			rd.forward(request, response);
			
		} else {
			
			request.setAttribute("message", "ログインに失敗しました。");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}
}
