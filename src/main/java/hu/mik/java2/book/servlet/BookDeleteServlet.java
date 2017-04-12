package hu.mik.java2.book.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import hu.mik.java2.book.bean.Book;
import hu.mik.java2.service.BookService;
import hu.mik.java2.service.ServiceUtils;

@WebServlet(urlPatterns = "/book_delete")
public class BookDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService bookService = ServiceUtils.getBookService();
		Book book = new Book();
		if (req.getParameter("bookId") != null) {
			System.out.println("book ID in get: " + req.getParameter("bookId"));
			Integer bookId = new Integer(req.getParameter("bookId"));
			book = bookService.getBookById(bookId);
		}

		req.setAttribute("book", book);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/book_delete.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BookService bookService = ServiceUtils.getBookService();
		Book book = new Book();
		// BeanUtils.populate(book, req.getParameterMap());
		// System.out.println(book);
		System.out.println("book ID in post: " + req.getParameter("bookId"));
		System.out.println("request parameters: " + req.getParameterMap());
		book = bookService.getBookById(new Integer(req.getParameter("bookId")));
		book = (Book) req.getAttribute("book");

		if (book.getId() != 0) {
			bookService.deleteBook(book);
		} else {
			throw new RuntimeException("Hiba a törlésben");
		}
	}

}
