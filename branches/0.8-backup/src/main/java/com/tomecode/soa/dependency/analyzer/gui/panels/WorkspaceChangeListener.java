package com.tomecode.soa.dependency.analyzer.gui.panels;

import com.tomecode.soa.dependency.analyzer.gui.visual.WorkspaceVisualPanel;

/**
 * 
 * simple listener - for display/hide {@link WorkspaceVisualPanel}
 * 
 * @author Tomas Frastia
 * 
 */
public interface WorkspaceChangeListener {

	/**
	 * display visual panel
	 */
	void displayVisualPanel();

	/**
	 * hide visual panel
	 */
	void hideVisualPanel();
}
