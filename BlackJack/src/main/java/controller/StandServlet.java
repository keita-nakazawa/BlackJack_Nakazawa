package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Dealer;
import model.Deck;
import model.Game;

@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");

		if (game != null) {
		
			Dealer dealer = game.getDealer();
			Deck deck = game.getDeck();

			dealer.stand(deck);
			
			game.setDealer(dealer);
			game.setDeck(deck);
			
			request.setAttribute("resultMap", game.comparePoints());
			
			RequestDispatcher rd = request.getRequestDispatcher("ResultServlet");
			rd.forward(request, response);
			
		} else {

			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
	}
}
