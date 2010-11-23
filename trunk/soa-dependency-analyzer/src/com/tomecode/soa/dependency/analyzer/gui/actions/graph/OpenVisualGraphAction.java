package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

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
public final class OpenVisualGraphAction extends BasicActionForGraphEvent {

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
			setEnabled(false);
		}
	}

	// public final void setData(Object data) {
	// if (data instanceof Service) {
	// super.setData(data);
	// } else if (data instanceof BpelProject) {
	// super.setData(data);
	// } else {
	// super.setData(null);
	// }
	// }
}
