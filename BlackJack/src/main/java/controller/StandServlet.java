package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.Player;

@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		// indexのバリデーション必要
		int index = Integer.parseInt(request.getParameter("index"));
		int splitPlayerSize = game.getSplitPlayers().getSize();

		if ((index >= 0) && (index < splitPlayerSize)) {

			Player player = game.getSplitPlayers().getPlayer(index);
			player.setEndFlag();
			player.setPlayerMessage("結果待ち");
			game.setPlayer(index, player);
			nextPage = "CheckEndFlagServlet";

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
