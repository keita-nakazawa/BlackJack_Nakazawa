package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Deck;
import model.Game;
import model.Player;

@WebServlet("/HitServlet")
public class HitServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");

		if (game != null) {

			Player player = game.getPlayer();
			Deck deck = game.getDeck();

			player.drawCard(deck.removeCard());
			player.setPoint();
			player.setBurst();

			game.setPlayer(player);
			game.setDeck(deck);
			game.addTurnCount();
			System.out.println(game.getTurnCount());

			if (player.getBurst() == true) {
				
				request.setAttribute("game", game);
				RequestDispatcher rd = request.getRequestDispatcher("BurstServlet");
				rd.forward(request, response);
			
			} else {
				session.setAttribute("game", game);
				RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
				rd.forward(request, response);
			}
		} else {

			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
	}
}
