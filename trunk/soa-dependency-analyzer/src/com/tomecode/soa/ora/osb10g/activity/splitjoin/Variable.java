package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: variable
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/*
 */
public final class Variable extends OsbActivity {

	private String messageType;

	private boolean isPrivate;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param messageType
	 * @param isPrivate
	 */
	public Variable(String name, String messageType, boolean isPrivate) {
		super(name, null);
		this.messageType = messageType;
		this.isPrivate = isPrivate;
	}

	/**
	 * @return the messageType
	 */
	public final String getMessageType() {
		return messageType;
	}

	/**
	 * @return the isPrivate
	 */
	public final boolean isPrivate() {
		return isPrivate;
	}

	@Override
	public final Image getImage() {
		if (isPrivate) {
			return ImageFactory.OSB_10G_SPLITJOIN_VARIABLE_PRIVATE;
		}
		return ImageFactory.OSB_10G_SPLITJOIN_VARIABLE;
	}

}
