package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Game;

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Game game = (Game) request.getAttribute("game");
		String message = (String) request.getAttribute("message");
		
		if (game != null) {
			
			//HistoryDaoクラスを駆使してDBのhistoryテーブルに結果を記録
			
			request.setAttribute("game", game);
			request.setAttribute("message", message);
			RequestDispatcher rd = request.getRequestDispatcher("gameEnd.jsp");
			rd.forward(request, response);
			
		} else {

			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
	}
}
