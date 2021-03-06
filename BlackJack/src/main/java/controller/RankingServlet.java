package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.NullChecker;
import model.User;

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(session.getAttribute("loginUser"));

		if (map.isEmpty()) {

			UserDao userDao = new UserDao(session);
			int population = userDao.getPopulation();
			List<User> rankingList = userDao.getRankingList();

			// uesrDaoからメッセージを抽出
			if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "menu.jsp";
				
			} else {
				request.setAttribute("population", population);
				request.setAttribute("rankingList", rankingList);
				nextPage = "ranking.jsp";
			}
		} else {
			
			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
