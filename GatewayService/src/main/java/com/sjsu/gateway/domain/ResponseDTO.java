package com.sjsu.gateway.domain;

import java.sql.Timestamp;

public class ResponseDTO {

	String url;
	Timestamp insertionTime;
	int reponseCode;
	int maxCount;

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getInsertionTime() {
		return insertionTime;
	}

	public void setInsertionTime(Timestamp insertionTime) {
		this.insertionTime = insertionTime;
	}

	public int getReponseCode() {
		return reponseCode;
	}

	public void setReponseCode(int reponseCode) {
		this.reponseCode = reponseCode;
	}

}
