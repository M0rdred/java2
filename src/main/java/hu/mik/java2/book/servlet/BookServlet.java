package hu.mik.java2.book.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.mik.java2.service.BookService;
import hu.mik.java2.service.ServiceUtils;

@WebServlet(urlPatterns = "/book_list")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService bookService = ServiceUtils.getBookService();
		req.setAttribute("books", bookService.listBooks());
		req.setAttribute("username", req.getRemoteUser());
		req.setAttribute("editor", req.isUserInRole("editor"));

		RequestDispatcher dispatcher = req.getRequestDispatcher("/book_list.jsp");
		dispatcher.forward(req, resp);
	}
}
