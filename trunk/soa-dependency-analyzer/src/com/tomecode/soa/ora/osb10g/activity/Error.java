package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: Error
 * 
 * @author Tomas Frastia
 * 
 */
public final class Error extends OsbActivity {

	private String errCode;
	private String message;

	public Error(String errCode, String message) {
		super();
		this.errCode = errCode;
		this.message = message;
	}

	public final String getErrCode() {
		return errCode;
	}

	public final String getMessage() {
		return message;
	}

}
