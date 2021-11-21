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
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(request.getAttribute("gameEnd"));

		if (map.isEmpty()) {
			
			User loginUser = (User) session.getAttribute("loginUser");
			Game game = (Game) session.getAttribute("game");
			Dealer dealer = game.getDealer();
			SplitPlayers splitPlayers = game.getSplitPlayers();
			Deck deck = game.getDeck();

			dealer.action(splitPlayers, deck);

			History history = game.comparePoints(loginUser);

			HistoryDao historyDao = new HistoryDao(session);
			UserDao userDao = new UserDao(session);
			historyDao.addHistory(history);
			userDao.addChip(loginUser, splitPlayers.getReturnedChip());

			// historyDaoからメッセージを抽出
			if (historyDao.getMessage() != null) {
				request.setAttribute("message", historyDao.getMessage());
				nextPage = "playGame.jsp";

			// userDaoからメッセージを抽出
			} else if (userDao.getMessage() != null) {
				request.setAttribute("message", userDao.getMessage());
				nextPage = "playGame.jsp";

			} else {
				loginUser.addChip(splitPlayers.getReturnedChip());
				
				request.setAttribute("previousBet", splitPlayers.getPlayer(0).getBet());
				request.setAttribute("history", history);
				request.setAttribute("game", game);
				//Gameオブジェクトはセッションから削除
				session.removeAttribute("game");
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
		doPost(request, response);
	}
}
