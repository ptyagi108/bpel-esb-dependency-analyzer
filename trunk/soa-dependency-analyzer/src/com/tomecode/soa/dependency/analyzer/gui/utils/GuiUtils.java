package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;

/**
 * 
 * GUI utilities - helper class for find views, editors etc.
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class GuiUtils {

	/**
	 * find reference for {@link WorkspacesNavigator} in application
	 * 
	 * @return null if {@link WorkspacesNavigator} not found
	 */
	public static final WorkspacesNavigator getWorkspacesNavigator() {
		IWorkbenchWindow[] workbenchs = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow window : workbenchs) {
			WorkspacesNavigator workspacesNavigator = (WorkspacesNavigator) window.getActivePage().findView(WorkspacesNavigator.ID);
			if (workspacesNavigator != null) {
				return workspacesNavigator;
			}
		}

		return null;
	}
}
