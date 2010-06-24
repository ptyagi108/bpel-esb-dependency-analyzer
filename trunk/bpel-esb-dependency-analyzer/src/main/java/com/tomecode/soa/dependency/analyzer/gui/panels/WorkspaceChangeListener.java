package com.tomecode.soa.dependency.analyzer.gui.panels;

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
