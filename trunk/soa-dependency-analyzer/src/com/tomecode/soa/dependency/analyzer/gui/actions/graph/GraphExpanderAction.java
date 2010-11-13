package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * 
 * Graph expander action
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class GraphExpanderAction extends Action {

	private boolean isExpandInNewGraph = true;

	private ExpandChangeListener listener;

	public GraphExpanderAction() {
		setToolTipText("Expand in current graph");
		setImageDescriptor(ImageFactory.expand_in_exists_graph);
	}

	public final void run() {
		if (isExpandInNewGraph) {
			isExpandInNewGraph = false;
			setImageDescriptor(ImageFactory.expand_in_new_graph);
		} else {
			isExpandInNewGraph = true;
			setImageDescriptor(ImageFactory.expand_in_exists_graph);
		}
		listener.change(isExpandInNewGraph);
	}

	public final void expandChangeListener(ExpandChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 * 
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public interface ExpandChangeListener {

		void change(boolean isExpandInNewGraph);
	}
}
