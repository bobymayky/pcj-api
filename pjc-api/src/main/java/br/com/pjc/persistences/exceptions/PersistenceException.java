package br.com.pjc.persistences.exceptions;

import util.exceptions.ErrorCode;

@SuppressWarnings("serial")
public class PersistenceException extends AbstractException {

	public PersistenceException(ErrorCode errorCode) {
		super(errorCode);
	}

	public PersistenceException(ErrorCode errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public PersistenceException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}