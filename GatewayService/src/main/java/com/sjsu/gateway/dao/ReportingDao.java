package com.sjsu.gateway.dao;

import java.util.List;

import com.sjsu.gateway.domain.ResponseDTO;
import com.sjsu.gateway.exception.DataAccessException;

public interface ReportingDao {

	/**
	 * @return
	 * @throws Exception
	 */
	int retriveSuccessCount() throws DataAccessException;

	/**
	 * @return
	 * @throws Exception
	 */
	int retrieveFailureCount() throws DataAccessException;

	/**
	 * @return
	 * @throws Exception
	 */
	int retrieveAuthenticationFailureCount() throws DataAccessException;

	/**
	 * @return
	 * @throws DataAccessException
	 */
	List<ResponseDTO> retrieveResponse() throws DataAccessException;

	/**
	 * @return
	 * @throws DataAccessException
	 */
	ResponseDTO retriveMaxHit() throws DataAccessException;
}
