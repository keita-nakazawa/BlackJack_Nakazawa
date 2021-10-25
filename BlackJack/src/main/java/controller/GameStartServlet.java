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

		HttpSession session = request.getSession();

		// ページ更新を悪用して何度でも手札を入れ替えられる不正の対策として、
		//セッションの"game"キーが既にセットされている場合はそれを更新しない。
		// ただ、if文をサーブレットに書くのはよろしくないかも。要検討。
		if (session.getAttribute("game") == null) {

			Player player = new Player();
			Dealer dealer = new Dealer();
			Deck deck = new Deck();

			User loginUser = (User) session.getAttribute("loginUser");
			player.setLoginUser(loginUser);

			deck.createDeck();

			for (int i = 0; i < 2; i++) {
				player.drawCard(deck.removeCard());
				dealer.drawCard(deck.removeCard());
			}
			player.setPoint();
			dealer.setPoint();

			session.setAttribute("game", new Game(deck, player, dealer));
		}
		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
