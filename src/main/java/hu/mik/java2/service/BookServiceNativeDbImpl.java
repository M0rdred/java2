package hu.mik.java2.service;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.plaf.basic.BasicTreeUI.TreeHomeAction;

import hu.mik.java2.book.bean.Book;

public class BookServiceNativeDbImpl implements BookService {

	private DataSource dataSource;

	public BookServiceNativeDbImpl() {
		try {
			InitialContext context = new InitialContext();
			this.dataSource = (DataSource) context.lookup("book/bookDatasource");

		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Book> listBooks() {
		List<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select id, author, title, description, pub_year from t_book order by id";

		try {
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Book book;
			while (resultSet.next()) {
				book = mapResultsetToBook(resultSet);
				books.add(book);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			// try-with-resources...
			closeResource(resultSet);
			closeResource(preparedStatement);
			closeResource(connection);
		}

		return books;

	}

	private void closeResource(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Book mapResultsetToBook(ResultSet resultSet) throws SQLException {
		Book book = new Book();

		book.setId(resultSet.getInt(1));
		book.setAuthor(resultSet.getString(2));
		book.setTitle(resultSet.getString(3));
		book.setDescription(resultSet.getString((4)));
		book.setPubYear(resultSet.getInt(5));

		return book;
	}

	@Override
	public Book getBookById(Integer id) {
		String sql = "select id, author, title, description, pub_year from T_BOOK where id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Book book = null;
		try {
			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();
			resultSet.next();

			book = new Book();

			book.setId(resultSet.getInt(1));
			book.setAuthor(resultSet.getString(2));
			book.setTitle(resultSet.getString(3));
			book.setDescription(resultSet.getString(4));
			book.setPubYear(resultSet.getInt(5));

			System.out.println("bookID: " + book.getId());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResource(resultSet);
			closeResource(preparedStatement);
			closeResource(connection);
		}
		return book;
	}

	@Override
	public Book saveBook(Book book) {
		System.out.println("saveBook - " + book);
		String sql = "insert into t_book (id, author, title, description, pub_year) values (s_book.nextval,?,?,?,?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			// select s_book.nextval from dual; --> getNextId();
			preparedStatement.setString(1, book.getAuthor());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setInt(4, book.getPubYear());

			preparedStatement.executeQuery();
			// connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null; // this.getBookById(book.getId());
	}

	@Override
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(Book book) {
		// TODO Auto-generated method stub

	}
}
