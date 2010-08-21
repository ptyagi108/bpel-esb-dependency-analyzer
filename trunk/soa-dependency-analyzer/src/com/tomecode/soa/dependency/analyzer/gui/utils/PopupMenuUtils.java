package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.tomecode.soa.dependency.analyzer.gui.actions.OpenWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RefreshAction;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;

/**
 * 
 * Helper class for fill popup menus
 * 
 * @author Tomas Frastia
 * 
 */
public final class PopupMenuUtils {

	private static final OpenWorkspaceAction OPEN_WORKSPACE_ACTION = new OpenWorkspaceAction();

	private static final Separator SEPARATOR = new Separator();

	private static final RefreshAction REFRESH_ACTION = new RefreshAction();

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
		manager.add(REFRESH_ACTION);
		REFRESH_ACTION.setSelectectedNode(selectedNode);
	}
}
