package br.com.pjc.model.dao;

import java.util.HashMap;
import java.util.Map;


public class JdbcQuery{

	private String queryString;
	private Map<String, Object> parameters;
	private Integer maxResults = null;
	private Integer firstResult = null;


	public JdbcQuery(String queryString) {
		this.queryString = queryString;
	}
	

	public JdbcQuery(String queryString, Map<String, Object> parameters) {
		this.queryString = queryString;
		setParameters(parameters);
	}

	public String getQueryString() {
		return queryString;
	}


	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		return parameters;
	}
	
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public Integer getFirstResult() {
		return firstResult;
	}
	

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

}