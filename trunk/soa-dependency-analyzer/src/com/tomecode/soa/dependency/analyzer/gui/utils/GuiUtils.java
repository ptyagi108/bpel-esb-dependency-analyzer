package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;

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
		return (WorkspacesNavigator) findView(WorkspacesNavigator.ID);
	}

	/**
	 * find reference for {@link PropertiesView} in application
	 * 
	 * @return
	 */
	public static final PropertiesView getPropertiesView() {
		return (PropertiesView) findView(PropertiesView.ID);
	}

	/**
	 * find {@link IViewPart} by ID
	 * 
	 * @param id
	 * @return
	 */
	private static final IViewPart findView(String id) {
		IWorkbenchWindow[] workbenchs = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow window : workbenchs) {
			return window.getActivePage().findView(id);
		}
		return null;
	}

	/**
	 * find reference for {@link BpelProcessStructureNavigator}
	 * 
	 * @return
	 */
	public final static BpelProcessStructureNavigator getBpelProcessStructureNavigator() {
		return (BpelProcessStructureNavigator) findView(BpelProcessStructureNavigator.ID);
	}

	/**
	 * find reference for {@link ServiceBusStructureNavigator}
	 * 
	 * @return
	 */
	public static final ServiceBusStructureNavigator getServiceBusStructureNavigator() {
		return (ServiceBusStructureNavigator) findView(ServiceBusStructureNavigator.ID);
	}

	/**
	 * find reference for {@link ServiceOperationsDepNavigator}
	 * 
	 * @return
	 */
	public final static ServiceOperationsDepNavigator getServiceOperationsDepNavigator() {
		return (ServiceOperationsDepNavigator) findView(ServiceOperationsDepNavigator.ID);
	}

	/**
	 * find reference for {@link ProjectStructureNavigator}
	 * 
	 * @return
	 */
	public static final ProjectStructureNavigator getProjectStructureNavigator() {
		return (ProjectStructureNavigator) findView(ProjectStructureNavigator.ID);
	}
}
