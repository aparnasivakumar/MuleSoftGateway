package com.sjsu.gateway.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sjsu.gateway.dao.ReportingDao;
import com.sjsu.gateway.domain.ResponseDTO;
import com.sjsu.gateway.exception.DataAccessException;

public class ReportingDaoImpl implements ReportingDao {

	private DataSource statusConnection;

	public void setStatusConnection(DataSource statusConnection) {
		this.statusConnection = statusConnection;
	}

	@Override
	public int retriveSuccessCount() throws DataAccessException {
		Statement statement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String sql = "select count(*) from response where code = '200';";
			statement = statusConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			count = resultSet.getInt(1);
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
		return count;
	}
	
	@Override
	public ResponseDTO retriveMaxHit() throws DataAccessException {
		Statement statement = null;
		ResultSet resultSet = null;
		ResponseDTO response = null;
		try {
			String sql = "select url,max(counted), code from (select url,count(*) as counted, code from response group by code) as counts;";
			statement = statusConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			response = new ResponseDTO();
			response.setUrl(resultSet.getString("url"));
			response.setMaxCount(resultSet.getInt(2));
			response.setReponseCode(resultSet.getInt("code"));
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
		return response;
	}
	
	
	//select url,max(counted), code from (select url,count(*) as counted, code from response group by code) as counts;


	@Override
	public int retrieveFailureCount() throws DataAccessException {
		Statement statement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String sql = "select code from response";
			statement = statusConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int code = resultSet.getInt(1);
				if (code != 200) {
					count = count + 1;
				}
			}

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
		return count;
	}

	@Override
	public int retrieveAuthenticationFailureCount() throws DataAccessException {
		Statement statement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			String sql = "select count(*) from response where code = '401';";
			statement = statusConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			count = resultSet.getInt(1);
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
		return count;
	}

	@Override
	public List<ResponseDTO> retrieveResponse() throws DataAccessException {
		Statement statement = null;
		ResultSet resultSet = null;
		List<ResponseDTO> responseList = null;
		try {
			String sql = "select * from response;";
			statement = statusConnection.getConnection().createStatement();
			resultSet = statement.executeQuery(sql);
			responseList = new ArrayList<ResponseDTO>();
			while (resultSet.next()) {
				ResponseDTO response = new ResponseDTO();
				response.setUrl(resultSet.getString("url"));
				response.setInsertionTime(resultSet.getTimestamp("inserttime"));
				response.setReponseCode(resultSet.getInt("code"));
				responseList.add(response);
			}

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
		return responseList;
	}

}
