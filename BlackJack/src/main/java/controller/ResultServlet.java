package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Game;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		Map<String, Object> resultMap = (Map) request.getAttribute("resultMap");
		
		if (resultMap != null) {
			
			//HistoryDaoクラスを駆使してDBのhistoryテーブルに結果を記録
			
			request.setAttribute("game", game);
			request.setAttribute("resultMap", resultMap);
			session.setAttribute("game", null);
			RequestDispatcher rd = request.getRequestDispatcher("gameEnd.jsp");
			rd.forward(request, response);
			
		} else {

			request.setAttribute("message", "不正な操作、URLを検知しました。</br>ログアウト処理を実行しました。");
			RequestDispatcher rd = request.getRequestDispatcher("LoginLogoutServlet");
			rd.forward(request, response);
		}
	}
}
