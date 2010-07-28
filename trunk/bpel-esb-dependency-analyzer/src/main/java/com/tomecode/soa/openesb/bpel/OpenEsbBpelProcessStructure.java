package com.tomecode.soa.openesb.bpel;

import com.tomecode.soa.bpel.structure.AbstractProcessStructure;

/**
 * 
 * Process structure which contain all activities in Open ESB - BPEL process
 * 
 * @author Frastia Tomas
 * 
 */
public final class OpenEsbBpelProcessStructure extends AbstractProcessStructure {

	private static final long serialVersionUID = 601785178780916919L;

	
	private OpenEsbBpelProcess process;

	public OpenEsbBpelProcessStructure(OpenEsbBpelProcess process) {
		this.process = process;
	}

	/**
	 * @return the process
	 */
	public final OpenEsbBpelProcess getProcess() {
		return process;
	}

}
