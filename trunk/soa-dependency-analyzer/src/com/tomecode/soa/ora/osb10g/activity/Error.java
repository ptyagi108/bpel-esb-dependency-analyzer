package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

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

	public final String toString() {
		return "Raise Error";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_RAISE_ERROR;
	}
}
