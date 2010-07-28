package com.tomecode.soa.dependency.analyzer.gui.tab.event;

import java.awt.event.MouseEvent;
import java.util.EventListener;

/**
 * 
 * double click listener
 * 
 * @author Frastia Tomas
 * 
 */
public interface DoubleClickListener extends EventListener {
	/**
	 * event for double click
	 * 
	 * @param e
	 */
	void doubleClickOperation(MouseEvent e);
}
