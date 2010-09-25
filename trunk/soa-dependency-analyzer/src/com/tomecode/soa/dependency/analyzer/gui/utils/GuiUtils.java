package com.tomecode.soa.dependency.analyzer.gui.utils;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;

/**
 * 
 * GUI utilities - helper class for find views, editors etc.
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class GuiUtils {

	private static int visualGraphActivateInstance = 0;
	private static int visualGraphInstanceNum = 0;

	public final static String newInstanceVisualGraph() {
		visualGraphInstanceNum++;
		visualGraphActivateInstance = visualGraphInstanceNum;
		return String.valueOf(visualGraphInstanceNum);
	}

	public final static void dropInstanceVisualGraph() {
		visualGraphInstanceNum--;
	}

	public final static int getActivateViewId() {
		return visualGraphActivateInstance;
	}

	public final static void setActivateViewId(int i) {
		visualGraphActivateInstance = i;
	}

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
	 * find reference for {@link VisualGraphView}
	 * 
	 * @return
	 */
	public static final VisualGraphView getVisualGraphView() {
		IViewReference iViewReference = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findViewReference(VisualGraphView.ID, String.valueOf(visualGraphActivateInstance));
		if (iViewReference == null) {
			return (VisualGraphView) findView(VisualGraphView.ID);
		}
		return (VisualGraphView) iViewReference.getView(true);

	}

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

	/**
	 * hide view
	 * 
	 * @param viewId
	 */
	public final static void hideView(String viewId) {
		HideView hideView = (HideView) findView(viewId);
		if (hideView != null) {
			hideView.hideMe();
		}
	}

	/**
	 * show view
	 * 
	 * @param viewId
	 */
	public final static void showView(String viewId) {
		IViewPart viewPart = findView(viewId);
		if (viewPart == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
			} catch (PartInitException e) {
				MessageDialog.openError(null, "Error", "Opps...Error opening view:" + e.getMessage());
			}
		}

	}
}
