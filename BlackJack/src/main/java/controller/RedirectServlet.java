package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.NullChecker;

@WebServlet("/RedirectServlet")
public class RedirectServlet extends HttpServlet {

	//RedirectServlet自身に不正アクセスされた場合にも対応している。
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Object> objectList = (List)request.getAttribute("objectList");

		Map<String, String> map = NullChecker.createMap(objectList);
		
		request.setAttribute("message", map.get("message"));	
		request.setAttribute("nullChecked?", true);
		
		RequestDispatcher rd = request.getRequestDispatcher(map.get("nextPage"));
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Object> objectList = (List) request.getAttribute("objectList");

		Map<String, String> map = NullChecker.createMap(objectList);
		
		request.setAttribute("message", map.get("message"));	
		request.setAttribute("nullChecked?", "yes");
		
		RequestDispatcher rd = request.getRequestDispatcher(map.get("nextPage"));
		rd.forward(request, response);
	}
}
