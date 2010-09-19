package com.tomecode.soa.openesb.bpel.dependencies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelActivityDependency {

	private String partnerLink;
	private Activity activity;

	private final List<BpelProcess> bpelProcesses;

	public BpelActivityDependency() {
		this.bpelProcesses = new ArrayList<BpelProcess>();
	}

	public BpelActivityDependency(Activity activity, String partnerLink) {
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
	 * @return the dependencies
	 */
	public final List<BpelProcess> getDependencies() {
		return bpelProcesses;
	}

	public final String toString() {
		return activity.toString();
	}

	/**
	 * @return the partnerLink
	 */
	public final String getPartnerLink() {
		return partnerLink;
	}

	/**
	 * @return the bpelProcesses
	 */
	public final List<BpelProcess> getBpelProcesses() {
		return bpelProcesses;
	}

	public final Image getImage() {
		return activity.getActivtyType().getImage();
	}

	public final void addDependencyProcess(OpenEsbBpelProcess process) {
		this.bpelProcesses.add(process);
	}

}
