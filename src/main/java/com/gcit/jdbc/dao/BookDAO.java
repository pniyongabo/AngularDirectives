package com.gcit.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.jdbc.entity.Author;
import com.gcit.jdbc.entity.Book;
import com.gcit.jdbc.entity.Genre;

public class BookDAO extends BaseDAO {

	public void insert(Book book) throws SQLException {
		int bookId = saveWithId(
				"insert into tbl_book (title, pubId) values (?,?)",
				new Object[] { book.getTitle(),
						book.getPublisher().getPublisherId() });
		for (Author auth : book.getAuthors()) {
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
					new Object[] { bookId, auth.getAuthorId() });
		}

		for (Genre genre : book.getGenres()) {
			save("insert into tbl_book_genres (bookId, genreId) values (?,?)",
					new Object[] { bookId, genre.getGenreId() });
		}
	}

	public void update(Book book) throws SQLException {
		save("update tbl_book  set title = ?, pubId = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId() });

		save("delete from tbl_book_authors where bookId = ?",
				new Object[] { book.getBookId() });
		for (Author auth : book.getAuthors()) {
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)",
					new Object[] { book.getBookId(), auth.getAuthorId() });
		}

		save("delete from tbl_book_genres where bookId = ?",
				new Object[] { book.getBookId() });
		for (Genre genre : book.getGenres()) {
			save("insert into tbl_book_genres (bookId, genreId) values (?,?)",
					new Object[] { book.getBookId(), genre.getGenreId() });
		}
	}

	public void delete(Author auth) throws SQLException {
	}

	// public Author readOne(int authorId) throws SQLException {
	// }
	//
	 public List<Book> readAll() throws SQLException {
		 return null;
	 }

	@Override
	protected List<Author> convertResult(ResultSet rs) throws SQLException {
		return null;
	}
}
