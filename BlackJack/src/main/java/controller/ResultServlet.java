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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(request.getAttribute("gameEnd"));

		if (map.isEmpty()) {

			Dealer dealer = game.getDealer();
			SplitPlayers splitPlayers = game.getSplitPlayers();
			Deck deck = game.getDeck();

			dealer.action(splitPlayers, deck);
			game.setDealer(dealer);
			game.setDeck(deck);

			History history = game.comparePoints(loginUser);
			loginUser.addChip(history.getResult());

			HistoryDao historyDao = new HistoryDao(session);
			UserDao userDao = new UserDao(session);
			historyDao.addHistory(history);
			userDao.updateChip(loginUser);

			// historyDaoからメッセージを抽出
			if (historyDao.getMessage() != null) {
				request.setAttribute("message", historyDao.getMessage());
				nextPage = "playGame.jsp";

				// userDaoからメッセージを抽出
			} else if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "playGame.jsp";

			} else {
				session.setAttribute("loginUser", loginUser);
				request.setAttribute("previousBet", game.getSplitPlayers().getPlayer(0).getBet());
				request.setAttribute("game", game);
				session.setAttribute("game", null);
				nextPage = "gameEnd.jsp";
			}

		} else {

			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
