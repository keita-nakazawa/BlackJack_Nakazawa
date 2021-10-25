package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Player player = new Player();
		Dealer dealer = new Dealer();
		Deck deck = new Deck();

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		player.setLoginUser(loginUser);

		deck.createDeck();

		for (int i = 0; i < 2; i++) {
			player.drawCard(deck.removeCard());
			dealer.drawCard(deck.removeCard());
		}
		
		player.setPoint();
		dealer.setPoint();

		Game game = new Game(deck, player, dealer);
		session.setAttribute("game", game);

		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
