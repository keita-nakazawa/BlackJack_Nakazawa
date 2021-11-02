package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.HistoryDao;
import dao.UserDao;
import model.*;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {

			HistoryDao historyDao = new HistoryDao();
			UserDao userDao = new UserDao();

			// HistoryDaoのコンストラクタ実行時に作成されるメッセージを検出
			if (historyDao.getMessage() != null) {
				request.setAttribute("message", historyDao.getMessage());
				nextPage = "playGame.jsp";
				
			// UserDaoのコンストラクタ実行時に作成されるメッセージを検出
			} else if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "playGame.jsp";
				
			} else {

				History history = (History) request.getAttribute("history");
				User loginUser = (User) session.getAttribute("loginUser");
				historyDao.addHistory(history);
				userDao.updateResult(loginUser, history);
					
				//addHistoryメソッド実行時に作成されるメッセージを検出
				if (historyDao.getMessage() != null) {
					request.setAttribute("message", historyDao.getMessage());
					nextPage = "playGame.jsp";
					
				//updateメソッド実行時に作成されるメッセージを検出
				} else if (userDao.getMessage() != null) {
					request.setAttribute("message", userDao.getMessage());
					nextPage = "playGame.jsp";
					
				} else {
					request.setAttribute("game", game);
					session.setAttribute("game", null);
					nextPage = "gameEnd.jsp";
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
