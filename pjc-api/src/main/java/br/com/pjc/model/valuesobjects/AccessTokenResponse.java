package br.com.pjc.model.valuesobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private final String tokenType = "bearer";
	
	private String redirectTo;

	public AccessTokenResponse() {
	}
	

	public String getAccessToken() {
		return accessToken;
	}

	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getTokenType() {
		return tokenType;
	}


	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
}
	
	