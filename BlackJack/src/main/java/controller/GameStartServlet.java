package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.Player;
import model.ValidatorBJ;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nextPage = new String();

		ValidatorBJ validatorBJ = new ValidatorBJ();
		validatorBJ.putStr("bet", request.getParameter("bet"));
		validatorBJ.excuteValidation();

		// validatorBJからメッセージを抽出
		if (validatorBJ.getMessage() != null) {

			request.setAttribute("message", validatorBJ.getMessage());
			nextPage = "menu.jsp";

		} else {

			int bet = Integer.parseInt(request.getParameter("bet"));
			Game oldGame = (Game) session.getAttribute("game");
			Game newGame = new Game(bet);
			Game game = newGame.start(oldGame);

			Player player = game.getSplitPlayers().getList().get(0);
			if (player.isBlackJack()) {
				player.setNaturalBJFlag();
				player.setEndFlag();
				game.setPlayer(0, player);
				nextPage = "CheckEndFlagServlet";
			} else {
				nextPage = "playGame.jsp";
			}
			
			session.setAttribute("game", game);
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
