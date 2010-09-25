package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.zest.core.widgets.Graph;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * 
 * Refresh current graph layout
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 * 
 */
public final class RefreshGraphLayout extends Action {

	private final Graph graph;

	public RefreshGraphLayout(Graph graph) {
		this.graph = graph;
		setText("Reload layout");
		setToolTipText("Reload layout");
		setImageDescriptor(ImageFactory.reload_layout);
	}

	public final void run() {
		graph.applyLayout();
	}
}
