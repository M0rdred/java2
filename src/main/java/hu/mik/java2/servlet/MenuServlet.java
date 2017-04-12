package hu.mik.java2.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher menuDispatcher = getServletContext().getRequestDispatcher("/menu.jsp");
		RequestDispatcher pageDispatcher = getServletContext().getRequestDispatcher("/menu.jsp");
		menuDispatcher.include(req, resp);
		
	}

}
