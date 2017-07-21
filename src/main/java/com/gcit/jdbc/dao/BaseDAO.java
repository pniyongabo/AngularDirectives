package com.gcit.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO {
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	protected int pageNo;
	protected int pageSize = 10;

	protected abstract Object convertResult(ResultSet rs) throws SQLException;
	
	protected Connection getConnection() throws SQLException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/library", 
				"root", "password");
		return conn;
	}
	
	protected int saveWithId(String query, Object[] values) throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		int count = 1;
		for(Object obj : values) {
			stmt.setObject(count, obj);
			count++;
		}
		
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next())
			return rs.getInt("bookId");
		else 
			return -1;
	}

	protected void save(String query, Object[] values) throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement(query);
		
		int count = 1;
		for(Object obj : values) {
			stmt.setObject(count, obj);
			count++;
		}
		
		stmt.executeUpdate();
	}
	
	protected Object read(String query, Object[] values) throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement(query);

		if(values != null) {
			int count = 1;
			for(Object obj : values) {
				stmt.setObject(count, obj);
				count++;
			}
		}
		
		ResultSet rs = stmt.executeQuery();

		return this.convertResult(rs);

	}
	
	protected String setPageLimits(String query) {
		StringBuilder sb = new StringBuilder(query);
		sb.append("  LIMIT " + (pageNo - 1)*10 + "," + pageSize);
		
		return sb.toString();
	}

}






















