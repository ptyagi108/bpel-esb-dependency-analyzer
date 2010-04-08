package com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class ErrorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -8551806046326442886L;

	private Exception exception;

	private String errorText;

	private String wsdl;

	public ErrorNode(String errorText, String wsdl, Exception exception) {
		super(errorText);
		this.errorText = errorText;
		this.exception = exception;
		this.wsdl = wsdl;
	}

	public final String getWsdl() {
		return wsdl;
	}

	public final Exception getException() {
		return exception;
	}

	public final String getErrorText() {
		return errorText;
	}

}
