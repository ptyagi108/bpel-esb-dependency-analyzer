package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * onAlarm activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OnAlarm extends Activity {

	private static final long serialVersionUID = 6884449161162887427L;

	private String forr;

	private String until;

	private String repeatEvery;

	public OnAlarm() {
		super(ActivityType.OPEN_ESB_BPEL_ONALARM, null);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_ONALARM;
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
