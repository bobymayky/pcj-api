package br.com.pjc.controllers.services.exceptions;

import util.exceptions.ErrorCode;

@SuppressWarnings("serial")
public class ErroAutenticacaoException extends ServiceException {

	public ErroAutenticacaoException(String message, Throwable cause) {
		super(ExceptionCode.ERRO_AUTENTICACAO, message, cause);
	}

	public ErroAutenticacaoException(Throwable cause) {
		super(ExceptionCode.ERRO_AUTENTICACAO, cause);
	}

	public ErroAutenticacaoException() {
		super(ExceptionCode.ERRO_AUTENTICACAO);
	}

	public enum ExceptionCode implements ErrorCode {

		ERRO_AUTENTICACAO("ERRO_AUTENTICACAO");

		private String code;

		private ExceptionCode(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}

	}
	

}
