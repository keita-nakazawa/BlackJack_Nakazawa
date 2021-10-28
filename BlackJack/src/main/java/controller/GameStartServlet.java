package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;

@WebServlet("/GameStartServlet")
public class GameStartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		List<Object> objectList = new ArrayList<>();
		objectList.add("GameStartServlet");
		objectList.add(session.getAttribute("loginUser"));

		//RedirectServletにオブジェクトリストを渡す。
		//nullChecked?が空ならRedirectServletをまだ経由していないことを意味する。
		//RedirectServletからこのページに遷移してきた場合、このif文はスキップされる。
		if (request.getAttribute("nullChecked?") == null) {
			
			request.setAttribute("objectList", objectList);
			RequestDispatcher rd = request.getRequestDispatcher("RedirectServlet");
			rd.forward(request, response);
		}

		Game oldGame = (Game) session.getAttribute("game");
		Game newGame = new Game();
		session.setAttribute("game", newGame.start(oldGame));

		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
