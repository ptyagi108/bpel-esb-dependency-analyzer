package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * onAlarm activity in Open ESB - BPEL process
 * 
 * @author Frastia Tomas
 * 
 */
public final class OnAlarm extends Activity {

	private static final long serialVersionUID = 6884449161162887427L;

	private String forr;

	private String until;

	private String repeatEvery;

	public OnAlarm(ActivityType type) {
		super(type, null);
	}

	/**
	 * @return the for
	 */
	public final String getFor() {
		return forr;
	}

	/**
	 * @param forr
	 *            the for to set
	 */
	public final void setFor(String forr) {
		this.forr = forr;
	}

	/**
	 * @return the until
	 */
	public final String getUntil() {
		return until;
	}

	/**
	 * @param until
	 *            the until to set
	 */
	public final void setUntil(String until) {
		this.until = until;
	}

	/**
	 * @return the repeatEvery
	 */
	public final String getRepeatEvery() {
		return repeatEvery;
	}

	/**
	 * @param repeatEvery
	 *            the repeatEvery to set
	 */
	public final void setRepeatEvery(String repeatEvery) {
		this.repeatEvery = repeatEvery;
	}

}
