package org.xson.common.validate;

public class XCOValidateException extends RuntimeException {

	private static final long	serialVersionUID	= 4225315797257223806L;

	private int					errorCode			= -1;

	private String				errorMessage		= "数据验证错误";

	public XCOValidateException() {
		super();
	}

	public XCOValidateException(String message) {
		super(message);
	}

	public XCOValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public XCOValidateException(Throwable cause) {
		super(cause);
	}

	public XCOValidateException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
