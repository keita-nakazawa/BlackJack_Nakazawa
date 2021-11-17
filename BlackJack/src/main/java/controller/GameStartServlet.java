package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDao;
import model.*;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(loginUser);

		if (map.isEmpty()) {

			// BET額のバリデーション
			ValidatorBJ validatorBJ = new ValidatorBJ();
			String strBet = request.getParameter("bet");
			validatorBJ.putStr("bet", strBet);
			validatorBJ.excuteValidation();

			// validatorBJからメッセージを抽出
			if (validatorBJ.getMessage() != null) {

				request.setAttribute("message", validatorBJ.getMessage());
				nextPage = "menu.jsp";

			} else {

				int bet = Integer.parseInt(strBet);
				UserDao userDao = new UserDao(session);
				//ゲーム開始前にBET額をチップ所持枚数から差し引く。
				userDao.addChip(loginUser, -bet);

				// userDaoからメッセージを抽出
				if (userDao.getMessage() != null) {
					request.setAttribute("message", userDao.getMessage());
					nextPage = "menu.jsp";

				} else {

					//ゲーム開始前にBET額をチップ所持枚数から差し引く。
					loginUser.addChip(-bet);
					
					Game oldGame = (Game) session.getAttribute("game");
					Game newGame = new Game(bet);
					Game game = newGame.start(oldGame);
					Player player = game.getSplitPlayers().getPlayer(0);
					if (player.isEnd()) {
						nextPage = "CheckEndFlagServlet";
					} else {
						nextPage = "playGame.jsp";
					}
					session.setAttribute("game", game);
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

		request.setAttribute("message", "不正な操作・URLを検知、もしくはセッションタイムアウトです。<br>強制ログアウトしました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
