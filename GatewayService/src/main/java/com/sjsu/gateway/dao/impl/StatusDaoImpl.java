package com.sjsu.gateway.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.sjsu.gateway.dao.StatusDao;
import com.sjsu.gateway.exception.DataAccessException;

public class StatusDaoImpl implements StatusDao {

	private DataSource statusConnection;

	public void setStatusConnection(DataSource statusConnection) {
		this.statusConnection = statusConnection;
	}

	@Override
	public void saveStatus(final String url, final int code)
			throws DataAccessException {
		Statement statement = null;
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String sql = "insert into response.response VALUES ('" + url
					+ "','" + time + "','" + code + "');";
			statement = statusConnection.getConnection().createStatement();
			statement.execute(sql);
		} catch (SQLException ex) {
			throw new DataAccessException(ex);
		} finally {

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ex) {
					throw new DataAccessException(ex);
				}

		}
	}

}
