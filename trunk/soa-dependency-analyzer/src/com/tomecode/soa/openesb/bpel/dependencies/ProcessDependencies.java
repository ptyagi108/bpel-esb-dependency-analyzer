package com.tomecode.soa.openesb.bpel.dependencies;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class ProcessDependencies {

	private BpelProcess bpelProcess;

	private final List<ProcessDependency> processDependencies;

	public ProcessDependencies(BpelProcess bpelProcess) {
		this.processDependencies = new ArrayList<ProcessDependency>();
		this.bpelProcess = bpelProcess;
	}

	/**
	 * @return the bpelProcess
	 */
	public final BpelProcess getBpelProcess() {
		return bpelProcess;
	}

	/**
	 * @return the processDependencies
	 */
	public final List<ProcessDependency> getProcessDependencies() {
		return processDependencies;
	}

	public final void addDependency(ProcessDependency processDependency) {
		this.processDependencies.add(processDependency);
	}

}
