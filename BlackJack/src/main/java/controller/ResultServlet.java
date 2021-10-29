package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.NullChecker;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		Map<String, Object> resultMap = (Map) request.getAttribute("resultMap");
		String nextPage = new String();

		Map<String, String> map = NullChecker.createMap(game);
		
		if (map.isEmpty()) {
			//HistoryDaoクラスを駆使してDBのhistoryテーブルに結果を記録
			
			request.setAttribute("game", game);
			request.setAttribute("resultMap", resultMap);
			session.setAttribute("game", null);
			nextPage = "gameEnd.jsp";
			
		} else {
			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}
