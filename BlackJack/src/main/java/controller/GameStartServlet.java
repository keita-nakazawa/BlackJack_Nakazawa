package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.NullChecker;
import model.ValidatorBJ;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(session.getAttribute("loginUser"));

		if (map.isEmpty()) {

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
				session.setAttribute("game", game);

				
				
				if (game.getPlayer().isBlackJack()) {
					nextPage = "StandServlet";
				} else {
					nextPage = "playGame.jsp";
				}
			}
		} else {

			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
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
