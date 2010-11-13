package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * Open selected node in tree in new {@link VisualGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OpenVisualGraphAction extends Action {

	private Object selectedNode;

	public OpenVisualGraphAction() {
		setText("Open new in Visual Graph");
		setToolTipText("Open new Visaul Graph");
		setImageDescriptor(ImageFactory.visual_graph_view);
		setEnabled(false);
	}

	public final void run() {
		VisualGraphView graphView = GuiUtils.newVisualGraphView();
		if (graphView != null) {
			graphView.showGraph(selectedNode);
		}
	}

	/**
	 * set data for graph
	 * 
	 * @param selectedNode
	 *            if is not null then is enabled
	 */
	public final void setData(Object selectedNode) {
		this.selectedNode = selectedNode;
		setEnabled(selectedNode != null);
	}
}
