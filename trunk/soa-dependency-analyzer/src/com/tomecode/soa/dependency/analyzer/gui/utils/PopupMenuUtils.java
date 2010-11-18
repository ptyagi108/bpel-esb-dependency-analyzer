package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.tomecode.soa.dependency.analyzer.gui.actions.AddNewProjectToWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.OpenWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RemoveMultiWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RemoveWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.OpenFlowGraphAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.OpenVisualGraphAction;
import com.tomecode.soa.dependency.analyzer.tree.ProjectServicesNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.graph.FlowGraphView;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * 
 * helper class for making better menu
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class PopupMenuUtils {

	private static final OpenWorkspaceAction OPEN_WORKSPACE_ACTION = new OpenWorkspaceAction();

	private static final Separator SEPARATOR = new Separator();

	// private static final RefreshAction REFRESH_ACTION = new RefreshAction();

	private static final RemoveMultiWorkspaceAction REMOVE_MULTI_WORKSPACE_ACTION = new RemoveMultiWorkspaceAction();

	private static final RemoveWorkspaceAction REMOVE_WORKSPACE_ACTION = new RemoveWorkspaceAction();

	/**
	 * open new {@link VisualGraphView}
	 */
	private static final OpenVisualGraphAction OPEN_VISAUL_GRAPH_ACTION = new OpenVisualGraphAction();
	/**
	 * open new {@link FlowGraphView}
	 */
	private static final OpenFlowGraphAction OPEN_FLOW_GRAPH_ACTION = new OpenFlowGraphAction();

	private static final AddNewProjectToWorkspaceAction ADD_NEW_PROJECT_TO_WORKSPACE = new AddNewProjectToWorkspaceAction();

	private PopupMenuUtils() {

	}

	/**
	 * fill popupMenu for {@link WorkspacesNavigator}
	 * 
	 * @param selectedNode
	 *            selected node in {@link WorkspacesNavigator}
	 * @param manager
	 *            menu manager
	 */
	public static final void fillWorksapceNavigator(Object selectedNode, IMenuManager manager) {
		OPEN_VISAUL_GRAPH_ACTION.setData(selectedNode);
		manager.add(OPEN_VISAUL_GRAPH_ACTION);
		OPEN_FLOW_GRAPH_ACTION.setData(selectedNode);
		manager.add(OPEN_FLOW_GRAPH_ACTION);
		manager.add(SEPARATOR);
		manager.add(OPEN_WORKSPACE_ACTION);
		manager.add(SEPARATOR);
		manager.add(ADD_NEW_PROJECT_TO_WORKSPACE);
		manager.add(SEPARATOR);
		REMOVE_MULTI_WORKSPACE_ACTION.setEnableFor(selectedNode);
		manager.add(REMOVE_MULTI_WORKSPACE_ACTION);
		REMOVE_WORKSPACE_ACTION.setEnableFor(selectedNode);
		manager.add(REMOVE_WORKSPACE_ACTION);
		manager.add(SEPARATOR);
		// manager.add(REFRESH_ACTION);
		// REFRESH_ACTION.setSelectectedNode(selectedNode);
	}

	/**
	 * fill popupmenu for {@link WorkspacesNavigator}
	 * 
	 * @param manager
	 */
	public static final void fillEmptyWorksapceNavigator(IMenuManager manager) {
		manager.add(OPEN_WORKSPACE_ACTION);
	}

	/**
	 * fill menu for {@link ProjectServicesNavigator}
	 * 
	 * @param selectedNode
	 * @param manager
	 */
	public static final void fillServicesNavigator(Object selectedNode, IMenuManager manager) {
		OPEN_VISAUL_GRAPH_ACTION.setData(selectedNode);
		manager.add(OPEN_VISAUL_GRAPH_ACTION);
		OPEN_FLOW_GRAPH_ACTION.setData(selectedNode);
		manager.add(OPEN_FLOW_GRAPH_ACTION);
	}
}
