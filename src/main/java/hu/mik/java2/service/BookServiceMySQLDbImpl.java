package hu.mik.java2.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.mik.java2.book.bean.Book;

public class BookServiceMySQLDbImpl implements BookService {

	String url = "jdbc:mysql://localhost:3306/book";

	@Override
	public List<Book> listBooks() {
		List<Book> books = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select id, author, title, description, pub_year from t_book order by id";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "root", "root");
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			Book book;
			while (resultSet.next()) {
				book = mapResultsetToBook(resultSet);
				books.add(book);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
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
			connection = DriverManager.getConnection(url, "root", "root");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				book = mapResultsetToBook(resultSet);
			}

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
		String sql = "insert into t_book (author, title, description, pub_year) values (?,?,?,?)";
		try (Connection connection = DriverManager.getConnection(url, "root", "root");
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, book.getAuthor());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setInt(4, book.getPubYear());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return getBookById(book.getId());
	}

	@Override
	public Book updateBook(Book book) {
		String sql = "update t_book set author = ?, title = ?, description = ?, pub_year = ? where id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DriverManager.getConnection(url, "root", "root");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, book.getAuthor());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setInt(4, book.getPubYear());
			preparedStatement.setInt(5, book.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResource(preparedStatement);
			closeResource(connection);
		}
		return getBookById(book.getId());
	}

	@Override
	public void deleteBook(Book book) {
		String sql = "delete from t_book where id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DriverManager.getConnection(url, "root", "root");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, book.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeResource(preparedStatement);
			closeResource(connection);
		}

	}
}
