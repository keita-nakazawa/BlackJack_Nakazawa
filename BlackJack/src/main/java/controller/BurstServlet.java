package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;

@WebServlet("/BurstServlet")
public class BurstServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Game game = (Game) request.getAttribute("game");
		
		if ((game != null) && (game.getPlayer().getBurst() == true)) {
			
			game.setGameEnd();

			HttpSession session = request.getSession();
			session.setAttribute("game", game);
		}

		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
