package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/HitServlet")
public class HitServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(game);
		
		if (map.isEmpty()) {

			Player player = game.getPlayer();
			Deck deck = game.getDeck();
			player.hit(deck);

			game.setPlayer(player);
			game.setDeck(deck);
			game.addTurnCount();

			if (player.isBurst()) {
				User loginUser = (User)session.getAttribute("loginUser");
				request.setAttribute("history", game.comparePoints(loginUser));
				nextPage = "ResultServlet";

			} else if (player.isBlackJack()) {
				session.setAttribute("game", game);
				nextPage = "StandServlet";
				
			} else {
				session.setAttribute("game", game);
				nextPage = "playGame.jsp";
			}
			
		} else {
			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}
