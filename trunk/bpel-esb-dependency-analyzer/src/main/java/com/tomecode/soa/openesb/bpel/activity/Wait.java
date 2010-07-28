package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * wait activity in Open ESB - BPEL process
 * 
 * @author Frastia Tomas
 * 
 */
public final class Wait extends Activity {

	private static final long serialVersionUID = -244298324165649381L;

	private String forr;

	private String until;

	public Wait() {
		super();
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

}
