package com.sjsu.gateway.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.sjsu.gateway.dao.ReportingDao;
import com.sjsu.gateway.dao.StatusDao;
import com.sjsu.gateway.domain.ResponseDTO;

@Controller
@RequestMapping(value = "/")
public class GatewayController extends HttpServlet {

	static Properties prop = new Properties();
	InputStream in;
	StatusDao statusDao;
	ReportingDao reportDao;
	

	private void initialize() throws Exception {

		try {
			in = this.getClass().getResourceAsStream("/link.properties");
			if (in == null) {
				throw new Exception("File not loaded");
			}
			prop.load(in);
			Enumeration enuKeys = prop.keys();
			if (enuKeys != null) {
				while (enuKeys.hasMoreElements()) {
					String key = (String) enuKeys.nextElement();
					String value = prop.getProperty(key);
					System.out.println(key + ": " + value);
				}
			} else {
				System.out.println("No keys found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public void doPos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String appendUrl = "?";
		HttpResponse responsetemp = null;
		String url = null;
		Enumeration<String> parameterNames = request.getParameterNames();


		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			appendUrl = appendUrl + paramName + "=";
			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				paramValue = paramValue.replace(" ", "+");
				appendUrl = appendUrl +paramValue + "&";
				if(paramValues.length > 1){
					appendUrl = appendUrl+ paramName + "=";
				}
			}
		}
		appendUrl = appendUrl.substring(0, appendUrl.length() -1);
		try {
			url = prop.getProperty("Google_http");
			url = url + restOfTheUrl;
			if(appendUrl != null)
			{
				url = url + appendUrl;
			}
			Enumeration headerNames = request.getHeaderNames();
			HttpGet requestTmp = new HttpGet(url);

			Header headers[] = requestTmp.getAllHeaders();
			System.out.println(requestTmp.getURI());
			System.out.println("New header");
			for (Header h : headers) {
				System.out.println(h.getName() + ": " + h.getValue());
			}

			HttpClient client = new DefaultHttpClient();
			responsetemp = client.execute(requestTmp);

			int statusCode = responsetemp.getStatusLine().getStatusCode();
			statusDao.saveStatus(url, statusCode);         
			System.out.println(statusCode);

			response.setStatus(statusCode, responsetemp.getStatusLine().getReasonPhrase());
			copyResponseHeaders(responsetemp, request, response);
			copyResponseEntity(responsetemp, response);



		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/*", method = RequestMethod.POST)
	public void doPosT(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String appendUrl = "?";
		HttpResponse responsetemp = null;
		String url = null;
		Enumeration<String> parameterNames = request.getParameterNames();


		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			appendUrl = appendUrl + paramName + "=";
			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				paramValue = paramValue.replace(" ", "+");
				appendUrl = appendUrl +paramValue + "&";
				if(paramValues.length > 1){
					appendUrl = appendUrl+ paramName + "=";
				}
			}
		}
		appendUrl = appendUrl.substring(0, appendUrl.length() -1);
		try {
			url = prop.getProperty("Google_http");
			url = url + restOfTheUrl;
			if(appendUrl != null)
			{
				url = url + appendUrl;
			}
			Enumeration headerNames = request.getHeaderNames();
			HttpGet requestTmp = new HttpGet(url);

			Header headers[] = requestTmp.getAllHeaders();
			System.out.println(requestTmp.getURI());
			System.out.println("New header");
			for (Header h : headers) {
				System.out.println(h.getName() + ": " + h.getValue());
			}

			HttpClient client = new DefaultHttpClient();
			responsetemp = client.execute(requestTmp);

			int statusCode = responsetemp.getStatusLine().getStatusCode();
			statusDao.saveStatus(url, statusCode);         
			System.out.println(statusCode);

			response.setStatus(statusCode, responsetemp.getStatusLine().getReasonPhrase());
			copyResponseHeaders(responsetemp, request, response);
			copyResponseEntity(responsetemp, response);



		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}



	/** Copy proxied response headers back to the servlet client. */
	protected void copyResponseHeaders(HttpResponse proxyResponse, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		for (Header header : proxyResponse.getAllHeaders()) {
			System.out.println(header.getName() + ": " + header.getValue());
			servletResponse.addHeader(header.getName(), header.getValue());
		}
	}


	/** Copy response body data (the entity) from the proxy to the servlet client. */
	protected void copyResponseEntity(HttpResponse proxyResponse, HttpServletResponse servletResponse) throws IOException {
		HttpEntity entity = proxyResponse.getEntity();
		if (entity != null) {
			OutputStream servletOutputStream = servletResponse.getOutputStream();
			entity.writeTo(servletOutputStream);
		}
	}

	public void setStatusDao(StatusDao statusDao) {
		this.statusDao = statusDao;
	}

	public void setReportDao(ReportingDao reportDao) {
		this.reportDao = reportDao;
	}


	@RequestMapping(value = "/analytics", method = RequestMethod.GET)
	public void doAnalyse(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("SuccessCount");
		int successCount = reportDao.retriveSuccessCount();
		System.out.println(successCount);
		System.out.println("FailureCount");
		int FailureCount = reportDao.retrieveAuthenticationFailureCount();
		System.out.println("MaxHit");
		ResponseDTO resp = reportDao.retriveMaxHit();
		System.out.println("url:"+resp.getUrl()+" max hits:"+resp.getMaxCount()+" response Code:"+resp.getReponseCode());
		
	}


}
