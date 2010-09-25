package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.tomecode.soa.dependency.analyzer.gui.actions.OpenWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RefreshAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RemoveMultiWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RemoveWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;

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

	private static final RefreshAction REFRESH_ACTION = new RefreshAction();

	private static final RemoveMultiWorkspaceAction REMOVE_MULTI_WORKSPACE_ACTION = new RemoveMultiWorkspaceAction();

	private static final RemoveWorkspaceAction REMOVE_WORKSPACE_ACTION = new RemoveWorkspaceAction();

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
		manager.add(OPEN_WORKSPACE_ACTION);
		manager.add(SEPARATOR);
		REMOVE_MULTI_WORKSPACE_ACTION.setEnableFor(selectedNode);
		manager.add(REMOVE_MULTI_WORKSPACE_ACTION);
		REMOVE_WORKSPACE_ACTION.setEnableFor(selectedNode);
		manager.add(REMOVE_WORKSPACE_ACTION);
		manager.add(SEPARATOR);
		manager.add(REFRESH_ACTION);
		REFRESH_ACTION.setSelectectedNode(selectedNode);
	}

	/**
	 * fill popupmenu for {@link WorkspacesNavigator}
	 * 
	 * @param manager
	 */
	public static final void fillEmptyWorksapceNavigator(IMenuManager manager) {
		manager.add(OPEN_WORKSPACE_ACTION);
	}
}
