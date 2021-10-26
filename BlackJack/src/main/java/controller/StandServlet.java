package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;


@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Game game = (Game)request.getAttribute("game");
		
		//スタンド処理
		
		
		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
