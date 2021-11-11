package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.*;

@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(game);

		if (map.isEmpty()) {

			// indexのバリデーション必要
			int index = Integer.parseInt(request.getParameter("index"));
			SplitPlayers splitPlayers = game.getSplitPlayers();

			if ((index >= 0) && (index < splitPlayers.getSize())) {
				Player player = splitPlayers.getPlayer(index);
				player.setEndFlag();
				player.setPlayerMessage();
				nextPage = "CheckEndFlagServlet";

			} else {
				request.setAttribute("message", "無効な操作です。");
				nextPage = "playGame.jsp";
			}

		} else {

			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
			LoginLogoutServlet lls = new LoginLogoutServlet();
			lls.doGet(request, response);
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("message", "不正な操作・URLを検知したため、強制ログアウトしました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
