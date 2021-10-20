package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao userDao = new UserDao();
		List<User> rankingList = userDao.getRankingList();
		request.setAttribute("rankingList", rankingList);
		
		RequestDispatcher rd = request.getRequestDispatcher("ranking.jsp");
		rd.forward(request, response);
	}
}
