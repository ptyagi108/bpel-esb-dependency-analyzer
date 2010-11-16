package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import org.eclipse.jface.action.Action;

/**
 * Basic action for graph events
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public abstract class BasicActionForGraphEvent extends Action {

	protected Object selectedNode;

	/**
	 * set data for graph
	 * 
	 * @param selectedNode
	 *            if is not null then is enabled
	 */
	public void setData(Object selectedNode) {
		this.selectedNode = selectedNode;
		setEnabled(selectedNode != null);
	}

}
