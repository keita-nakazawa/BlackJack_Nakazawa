package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/HitServlet")
public class HitServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		//indexのバリデーション必要
		int index = Integer.parseInt(request.getParameter("index"));
		int splitPlayerSize = game.getSplitPlayers().getSize();
		
		if (index >= 0 && index < splitPlayerSize) {
			
			Player player = game.getSplitPlayers().getPlayer(index);
			
			if (!(player.isEnd())) {
				Deck deck = game.getDeck();
				player.hit(deck);
				player.setSplitFlag();

				game.setPlayer(index, player);
				game.setDeck(deck);

				if (player.isBurst()) {
					User loginUser = (User) session.getAttribute("loginUser");
					request.setAttribute("history", game.comparePoints(loginUser));
					nextPage = "ResultServlet";

				} else if (player.isBlackJack()) {
					session.setAttribute("game", game);
					nextPage = "StandServlet";

				} else {
					session.setAttribute("game", game);
					nextPage = "playGame.jsp";
				}
			}
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
