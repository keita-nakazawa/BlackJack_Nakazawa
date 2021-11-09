package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(game);
		
		if (map.isEmpty()) {
			Dealer dealer = game.getDealer();
			Deck deck = game.getDeck();
			dealer.stand(deck);
			
			game.setDealer(dealer);
			game.setDeck(deck);
			
			User loginUser = (User)session.getAttribute("loginUser");
			request.setAttribute("history", game.comparePoints(loginUser));
			nextPage = "ResultServlet";
			
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
