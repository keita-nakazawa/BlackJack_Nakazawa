package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.User;


public class NullCheckServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String key = request.getParameter("key");

		switch (key) {
		
		case "loginUser":
			User loginUser = (User) session.getAttribute("loginUser");
			redirect(loginUser, "GameStartServlet", request, response);
			break;
			
		case "":
			
		}
	}

	// doPostはたぶんチップを導入した時に必要になってくる。
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	private void redirect(Object object, String servletName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (object != null) {
			
			RequestDispatcher rd = request.getRequestDispatcher(servletName);
			rd.forward(request, response);
		
		} else {
			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
	}
}
