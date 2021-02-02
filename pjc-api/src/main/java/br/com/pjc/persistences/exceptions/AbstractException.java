package br.com.pjc.persistences.exceptions;

import java.util.HashMap;
import java.util.Map;

import util.ValidationUtil;
import util.exceptions.ErrorCode;


@SuppressWarnings("serial")
public abstract class AbstractException extends Exception {

	private ErrorCode errorCode;
	private Map<String, Object> data;

	public AbstractException() {
	}
	
	public AbstractException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public AbstractException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public AbstractException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	
	public AbstractException(Throwable cause) {
		super(cause);
	}
	

	/**
	 * @return Error code associated to exception.
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @return Dynamic data associated to the exception.
	 */
	public Map<String, Object> getData() {
		if( ValidationUtil.isNull(data) ){
			data = new HashMap<String, Object>();
		}
		return data;
	}
	
	/**
	 * <p>Add a key/value pair to the data associated to the exception.</p>
	 * 
	 * @param name Name of value. Required.
	 * @param value Value. Required.
	 */
	public void set( String name, Object value ){
		getData().put(name, value);
	}

}