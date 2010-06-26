package com.tomecode.soa.dependency.analyzer.gui.tab.event;

import java.awt.event.MouseEvent;
import java.util.EventListener;

import com.tomecode.soa.dependency.analyzer.gui.tab.CloseTabbedPane;

/**
 * listener for {@link CloseTabbedPane} if user clict to close button then send
 * event to this listener
 * 
 * @author Tomas Frastia
 * 
 */
public interface TabbedCloseButtonListener extends EventListener {

	/**
	 * Close tab event
	 * 
	 * @param e
	 * @param overTabIndex
	 */
	void closeTab(MouseEvent e, int overTabIndex);
}
