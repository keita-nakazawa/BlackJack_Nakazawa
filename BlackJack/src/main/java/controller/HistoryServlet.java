package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.HistoryDao;
import dao.UserDao;
import model.History;
import model.NullChecker;
import model.User;

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

			HistoryDao historyDao = new HistoryDao();
			UserDao userDao = new UserDao();

			// HistoryDaoのコンストラクタ実行時に作成されるメッセージを検出
			if (historyDao.getMessage() != null) {
				request.setAttribute("message", historyDao.getMessage());
				nextPage = "menu.jsp";

			} else {
				
				List<History> historyList =  historyDao.getHistoryList(loginUser);
				User userInfo = userDao.getUserInfo(loginUser);
				
				// getHistoryListメソッド実行時に作成されるメッセージを検出
				if (historyDao.getMessage() != null) {
					request.setAttribute("message", historyDao.getMessage());
					nextPage = "menu.jsp";

				// getUserInfoメソッド実行時に作成されるメッセージを検出
				} else if (userDao.getMessage() != null) {
					request.setAttribute("message", userDao.getMessage());
					nextPage = "menu.jsp";
				
				} else {
					request.setAttribute("historyList", historyList);
					request.setAttribute("userInfo", userInfo);
					nextPage = "history.jsp";
				}
			}
		} else {
			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}
