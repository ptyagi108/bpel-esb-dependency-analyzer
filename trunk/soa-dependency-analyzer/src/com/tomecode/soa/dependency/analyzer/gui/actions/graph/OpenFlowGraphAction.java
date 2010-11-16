package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.FlowGraphView;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * Open selected node in tree in new {@link FlowGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OpenFlowGraphAction extends BasicActionForGraphEvent {

	public OpenFlowGraphAction() {
		setText("Open new in Flow Graph");
		setToolTipText("Open new in Flow Graph");
		setImageDescriptor(ImageFactory.flow_graph_view);
	}

	public final void run() {
		FlowGraphView graphView = GuiUtils.newFlowGraphView();
		if (graphView != null) {
			graphView.showGraph(selectedNode);
			setEnabled(false);
		}
	}

	/**
	 * set data for graph
	 * 
	 * @param selectedNode
	 *            if is not null then action is enabled
	 */
	public final void setData(Object selectedNode) {
		this.selectedNode = selectedNode;
		setEnabled(false);
		if (selectedNode != null) {
			if (!(selectedNode instanceof Workspace) || !(selectedNode instanceof MultiWorkspace)) {
				setEnabled(true);
			}
		}
	}
}
