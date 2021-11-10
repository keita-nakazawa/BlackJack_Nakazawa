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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		//indexのバリデーション必要
		int index = Integer.parseInt(request.getParameter("index"));
		int splitPlayerSize = game.getSplitPlayers().getSize();
		
		if ((index >= 0) && (index < splitPlayerSize)) {
			
			Player player = game.getSplitPlayers().getPlayer(index);
			
			if (!(player.isEnd())) {
				Deck deck = game.getDeck();
				player.hit(deck);
				//ヒット時のsetSplitFlagは必ずfalseを返す。
				player.setSplitFlag();

				if (player.isBurst()) {
					player.setEndFlag();
					player.setPlayerMessage("バースト");
					nextPage = "CheckEndFlagServlet";
					
				} else if (player.isBlackJack()) {
					player.setEndFlag();
					player.setPlayerMessage("結果待ち");
					nextPage = "CheckEndFlagServlet";
					
				} else {
					nextPage = "playGame.jsp";
				}

				game.setPlayer(index, player);
				game.setDeck(deck);
				
			} else {
				game.setGameMessage("無効な操作です。");
				nextPage = "playGame.jsp";
			}
			
		} else {
			game.setGameMessage("無効な操作です。");
			nextPage = "playGame.jsp";
		}
		
		session.setAttribute("game", game);
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
