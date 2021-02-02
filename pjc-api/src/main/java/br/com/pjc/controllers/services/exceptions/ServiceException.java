package br.com.pjc.controllers.services.exceptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.pjc.persistences.exceptions.AbstractException;
import util.exceptions.ErrorCode;


@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceException extends AbstractException {

	private Timestamp timestamp;
	private String status;
	private String error;
	private String exception;
	private String message;
	private String path;
	private Map<String, Object> fieldErrors;
	private List<Map<String, Object>> globalErrors;

	public ServiceException() {
	}
	
	public ServiceException( Throwable cause ) {
		super(cause);
	}

	public ServiceException(ErrorCode errorCode) {
		super(errorCode);
		setMessage(errorCode.getCode());
	}

	public ServiceException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public ServiceException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, Object> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public List<Map<String, Object>> getGlobalErrors() {
		return globalErrors;
	}

	public void setGlobalErrors(List<Map<String, Object>> globalErrors) {
		this.globalErrors = globalErrors;
	}
	
}
