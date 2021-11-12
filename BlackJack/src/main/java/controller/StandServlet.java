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

			String strIndex = request.getParameter("index");
			SplitPlayers splitPlayers = game.getSplitPlayers();
			
			ValidatorBJ validatorBJ = new ValidatorBJ();
			int index = validatorBJ.indexValidation(strIndex, splitPlayers);

			//validatorBJからメッセージを抽出
			if (validatorBJ.getMessage() != null) {
				request.setAttribute("message", validatorBJ.getMessage());
				nextPage = "playGame.jsp";
				
			} else {
				Player player = splitPlayers.getPlayer(index);
				player.setEndFlag();
				player.setPlayerMessage();
				nextPage = "CheckEndFlagServlet";
				
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

		request.setAttribute("message", "不正な操作・URLを検知したため、強制ログアウトしました。");
		RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
		rd.forward(request, response);
	}
}
