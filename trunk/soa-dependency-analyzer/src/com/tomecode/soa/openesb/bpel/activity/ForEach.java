package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * forEach activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class ForEach extends Activity {

	private static final long serialVersionUID = -3718243497638584693L;

	private String parallel;
	private String counterName;
	private String startCounterValue;
	private String finalCounterValue;
	private String completionConditionBranches;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param parallel
	 * @param counterName
	 * @param startCounterValue
	 * @param finalCounterValue
	 * @param completionConditionBranches
	 */
	public ForEach(String name, String parallel, String counterName, String startCounterValue, String finalCounterValue, String completionConditionBranches) {
		super(ActivityType.OPEN_ESB_BPEL_FOREACH, name);
		this.parallel = parallel;
		this.counterName = counterName;
		this.startCounterValue = startCounterValue;
		this.finalCounterValue = finalCounterValue;
		this.completionConditionBranches = completionConditionBranches;
	}

	/**
	 * @return the parallel
	 */
	public final String getParallel() {
		return parallel;
	}

	/**
	 * @return the counterName
	 */
	public final String getCounterName() {
		return counterName;
	}

	/**
	 * @return the startCounterValue
	 */
	public final String getStartCounterValue() {
		return startCounterValue;
	}

	/**
	 * @return the finalCounterValue
	 */
	public final String getFinalCounterValue() {
		return finalCounterValue;
	}

	/**
	 * @return the completionConditionBranches
	 */
	public final String getCompletionConditionBranches() {
		return completionConditionBranches;
	}

}
