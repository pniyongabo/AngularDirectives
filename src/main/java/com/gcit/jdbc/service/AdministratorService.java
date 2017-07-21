package com.gcit.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gcit.jdbc.dao.AuthorDAO;
import com.gcit.jdbc.dao.BookDAO;
import com.gcit.jdbc.entity.Author;
import com.gcit.jdbc.entity.Book;

public class AdministratorService {
	
	@Autowired
	@Qualifier("bkDao1")
	BookDAO bd;

	@Autowired
	AuthorDAO ad;
	
	public List<Book> getAllBooks() throws SQLException {
		List<Book> books = bd.readAll();
		
		for(Book b : books) {
			b.setAuthors(ad.readAllByBook(b));
		}
		
		return books;
	}
	
	public int countAuthors() throws SQLException {
		return ad.countAll();
	}

	public List<Author> getAllAuthors(int pageNo) throws SQLException {
		
		if(pageNo <= 0)
			pageNo = 1;
		ad.setPageNo(pageNo);
		List<Author> authors = ad.readAll();
		
		return authors;
	}
	
	public List<Author> searchAuthors(String searchText) throws SQLException {
		
		List<Author> authors = ad.readAllByName(searchText);
		
		return authors;
	}

	public void editAuthor(Author author) throws SQLException {
		ad.update(author);
	}

	public void addAuthor(Author author) throws SQLException {
		ad.insert(author);
	}

	public void deleteAuthor(Author author) throws SQLException {
		ad.delete(author);
	}
}
