package com.gcit.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.jdbc.entity.Publisher;

public class PublisherDAO extends BaseDAO {

	public void insert(Publisher auth) throws SQLException {
		save("insert into tbl_publisher (publisherName) values (?)",
				new Object[] { auth.getPublisherName() });
	}

	public void update(Publisher auth) throws SQLException {
		save("update tbl_publisher set publisherName = ? where publisherId = ?",
				new Object[] { auth.getPublisherName(), auth.getPublisherId() });
	}

	public void delete(Publisher auth) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { auth.getPublisherId() });
	}

	public Publisher readOne(int publisherId) throws SQLException {
		List<Publisher> publishers = (List<Publisher>) read(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });
		if (publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		} else {
			return null;
		}
	}

	public List<Publisher> readAll() throws SQLException {
		return (List<Publisher>) read("select * from tbl_publisher", null);
	}

	@Override
	protected List<Publisher> convertResult(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher auth = new Publisher();
			auth.setPublisherId(rs.getInt("publisherId"));
			auth.setPublisherName(rs.getString("publisherName"));
			publishers.add(auth);
		}

		return publishers;
	}
}
