package com.sjsu.gateway.dao;

import com.sjsu.gateway.exception.DataAccessException;

public interface StatusDao {

	/**
	 * @param url
	 * @param code
	 * @throws Exception
	 */
	void saveStatus(final String url, final int code)
			throws DataAccessException;

}
