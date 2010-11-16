package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * 
 * Delete selected node in graph
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class DeleteSelectedNodeInGraph extends BasicActionForGraphEvent {

	public DeleteSelectedNodeInGraph() {
		setText("Hide selected dependency");
		setToolTipText("Hide selected dependency");
		setEnabled(false);
	}

	public final void run() {
		VisualGraphView graphView = GuiUtils.getVisualGraphView();
		if (graphView != null) {
			graphView.deleteSelectedNode(selectedNode);
			setEnabled(false);
		}
	}
}
