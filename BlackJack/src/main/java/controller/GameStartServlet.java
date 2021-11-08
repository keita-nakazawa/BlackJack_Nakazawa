package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.NullChecker;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(session.getAttribute("loginUser"));

		if (map.isEmpty()) {
			
			Game oldGame = (Game) session.getAttribute("game");
			Game newGame = new Game();
			Game game = newGame.start(oldGame);
			session.setAttribute("game", game);
			
			if (game.getPlayer().getPlayerPoint() == 21) {
				nextPage = "StandServlet";
			} else {
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
