package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.util.Hashtable;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * window change listener
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class WindowChangeListener {

	private final Hashtable<String, CloseViewListener> actionsTable;

	private static WindowChangeListener me;

	private WindowChangeListener() {
		actionsTable = new Hashtable<String, CloseViewListener>();
	}

	public static final WindowChangeListener getInstance() {
		if (me == null) {
			me = new WindowChangeListener();
		}
		return me;
	}

	public final void register(String wiewId, CloseViewListener action) {
		actionsTable.put(wiewId, action);
	}

	/**
	 * closing view
	 * 
	 * @param id
	 */
	public final void hideFromView(String id) {
		CloseViewListener listener = actionsTable.get(id);
		if (listener != null) {
			listener.userClose();
		} else {
			toString();
		}
	}

}
