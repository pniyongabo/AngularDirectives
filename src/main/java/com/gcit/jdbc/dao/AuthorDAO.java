package com.gcit.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.jdbc.entity.Author;
import com.gcit.jdbc.entity.Book;

public class AuthorDAO extends BaseDAO {

	public void insert(Author auth) throws SQLException {
		save("insert into tbl_author (authorName) values (?)",
				new Object[] { auth.getAuthorName() });
	}

	public void update(Author auth) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { auth.getAuthorName(), auth.getAuthorId() });
	}

	public void delete(Author auth) throws SQLException {
		save("delete from tbl_author where authorId = ?",
				new Object[] { auth.getAuthorId() });
	}

	public Author readOne(int authorId) throws SQLException {
		List<Author> authors = (List<Author>) read(
				"select * from tbl_author where authorId = ?",
				new Object[] { authorId });
		if (authors != null && authors.size() > 0) {
			return authors.get(0);
		} else {
			return null;
		}
	}

	public int countAll() throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement("select count(*) from tbl_author");
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			return rs.getInt(1);
		else 
			return 0;
	}

	public List<Author> readAll() throws SQLException {
		return (List<Author>) read(setPageLimits("select * from tbl_author"), null);
	}

	public List<Author> readAllByBook(Book bk) throws SQLException {
		return (List<Author>) read(
				"select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
				new Object[] { bk.getBookId() });
	}

	public List<Author> readAllByName(String authorName) throws SQLException {
		
		String searchText = '%'+authorName+'%';
		
		return (List<Author>) read(
				"select * from tbl_author where authorName like ?",
				new Object[] { searchText });
	}

	@Override
	protected List<Author> convertResult(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while (rs.next()) {
			Author auth = new Author();
			auth.setAuthorId(rs.getInt("authorId"));
			auth.setAuthorName(rs.getString("authorName"));
			authors.add(auth);
		}

		return authors;
	}
}
