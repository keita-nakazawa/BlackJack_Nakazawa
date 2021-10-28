package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;
import model.NullChecker;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		List<Object> objectList = new ArrayList<>();
		objectList.add(session.getAttribute("loginUser"));

		Map<String, String> map = NullChecker.createMap(objectList);

		String nextPage = new String();
		
		if (map.get("nextPage") == null) {

			Game oldGame = (Game) session.getAttribute("game");
			Game newGame = new Game();
			session.setAttribute("game", newGame.start(oldGame));

			nextPage = "playGame.jsp";

		} else {
			
			request.setAttribute("message", map.get("message"));
			nextPage = map.get("nextPage");
		}
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}

}
