package com.tomecode.soa.openesb.bpel;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Process structure which contain all activities in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class OpenEsbBpelProcessStructure extends Activity {

	private static final long serialVersionUID = 601785178780916919L;

	private final OpenEsbBpelProcess process;

	/**
	 * Constructor
	 * 
	 * @param process
	 */
	public OpenEsbBpelProcessStructure(OpenEsbBpelProcess process) {
		this.process = process;
	}

	/**
	 * @return the process
	 */
	public final OpenEsbBpelProcess getProcess() {
		return process;
	}

	public final String toString() {
		return "!!!structura!!!";
	}

	@Override
	public Image getImage() {
		return null;
	}

}
