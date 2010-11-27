package com.tomecode.soa.openesb.bpel.dependencies;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class ProcessDependency {

	private Activity activity;

	private final List<BpelProcess> bpelProcesses;

	private String partnerLink;

	public ProcessDependency() {
		this.bpelProcesses = new ArrayList<BpelProcess>();
	}

	public ProcessDependency(Activity activity, String partnerLink) {
		this();
		this.activity = activity;
		this.partnerLink = partnerLink;
	}

	/**
	 * @return the activity
	 */
	public final Activity getActivity() {
		return activity;
	}

	/**
	 * @return the bpelProcesses
	 */
	public final List<BpelProcess> getBpelProcesses() {
		return bpelProcesses;
	}

	/**
	 * @return the partnerLink
	 */
	public final String getPartnerLink() {
		return partnerLink;
	}

}
