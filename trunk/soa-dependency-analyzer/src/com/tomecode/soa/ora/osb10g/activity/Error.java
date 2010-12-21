package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: Error
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Error extends OsbActivity {

	private String errCode;
	private String message;

	/**
	 * Constructor
	 * 
	 * @param errCode
	 * @param message
	 */
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

	public final String getToolTip() {
		return "Type: Error\nError Code: " + (errCode != null ? errCode : errCode) + "\nMessage: " + (message != null ? message : "");
	}
}
