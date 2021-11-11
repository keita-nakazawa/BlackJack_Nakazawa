package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.HistoryDao;
import model.History;
import model.NullChecker;
import model.User;

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

			HistoryDao historyDao = new HistoryDao(session);
			List<History> historyList = historyDao.getHistoryList(loginUser);

			// historyDaoからメッセージを抽出
			if (historyDao.getMessage() != null) {
				request.setAttribute("message", historyDao.getMessage());
				nextPage = "menu.jsp";

			} else {
				request.setAttribute("historyList", historyList);
				nextPage = "history.jsp";
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
