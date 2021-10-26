package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Deck;
import model.Game;
import model.Player;


@WebServlet("/StandServlet")
public class StandServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Game game = (Game)request.getAttribute("game");
		Player player = game.getPlayer();
		Deck deck = game.getDeck();
		
		int result = game.comparePoints();
		
		
		RequestDispatcher rd = request.getRequestDispatcher("playGame.jsp");
		rd.forward(request, response);
	}
}
