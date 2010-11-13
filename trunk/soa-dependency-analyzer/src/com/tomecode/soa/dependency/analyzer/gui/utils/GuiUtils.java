package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectFilesNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectServicesNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.graph.FlowGraphView;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

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

	// private static List<Integer> visualGraphInstances = null;
	// static {
	// if (visualGraphInstances == null) {
	// visualGraphInstances = new ArrayList<Integer>();
	// visualGraphInstances.add(0);
	// }
	// }

	// private static int visualGraphActivateInstance = 0;
	// private static int visualGraphInstanceNum = 0;

	// public final static String newInstanceVisualGraph() {
	// visualGraphInstanceNum++;
	// visualGraphActivateInstance = visualGraphInstanceNum;
	// visualGraphInstances.add(visualGraphActivateInstance);
	// return String.valueOf(visualGraphInstanceNum);
	// }
	//
	// public final static void dropInstanceVisualGraph() {
	// // TODO: fixnut mazanie aktivnych instacii grafu
	// if (visualGraphInstanceNum > 0) {
	// visualGraphInstanceNum--;
	// }
	//
	// if (!visualGraphInstances.isEmpty()) {
	// visualGraphInstances.remove(visualGraphActivateInstance);
	// }
	// }

	// public final static int getActivateViewId() {
	// return visualGraphActivateInstance;
	// }
	//
	// public final static void setActivateViewId(int i) {
	// visualGraphActivateInstance = i;
	// }

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
		IWorkbenchPage window = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = window.getActiveEditor();
		if (editorPart == null) {
			newVisualGraphView();
		}
		return (VisualGraphView) window.getActiveEditor();
	}

	public static final IEditorReference[] getEditors() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
	}

	/**
	 * find current {@link VisualGraphView}
	 * 
	 * @param instanceNumber
	 * @return
	 */
	public final static VisualGraphView getVisualGraphView(int instanceNumber) {
		IViewReference iViewReference = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findViewReference(VisualGraphView.ID, String.valueOf(instanceNumber));
		// if (iViewReference != null) {
		// return (VisualGraphView) iViewReference.getView(true);
		// }
		//
		// return (VisualGraphView) findView(VisualGraphView.ID);
		return null;
	}

	// public final static List<Integer> getAllVisualGraphInstances() {
	// return visualGraphInstances;
	// }

	public final static ServiceOperationsDepNavigator getServiceOperationsDepNavigator() {
		return (ServiceOperationsDepNavigator) findView(ServiceOperationsDepNavigator.ID);
	}

	/**
	 * find reference for {@link ProjectFilesNavigator}
	 * 
	 * @return
	 */
	public static final ProjectFilesNavigator getProjectStructureNavigator() {
		return (ProjectFilesNavigator) findView(ProjectFilesNavigator.ID);
	}

	/**
	 * find reference for {@link ProjectServicesNavigator}
	 * 
	 * @return
	 */
	public final static ProjectServicesNavigator getProjectServicesNavigator() {
		return (ProjectServicesNavigator) findView(ProjectServicesNavigator.ID);
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
				e.printStackTrace();
				MessageDialog.openError(null, "Error", "Opps...Error opening view:" + e.getMessage());
			}
		}

	}

	/**
	 * open new {@link VisualGraphView}
	 */
	public static final VisualGraphView newVisualGraphView() {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			page.openEditor(new VisualGraphView(), VisualGraphView.ID, false);
			return (VisualGraphView) page.getActiveEditor();
		} catch (PartInitException e) {
			MessageDialog.openError(null, "Error", "Opps...Error opening view:" + e.getMessage());
		}
		return null;
	}

	/**
	 * open new {@link FlowGraphView}
	 * 
	 * @return
	 */
	public static final FlowGraphView newFlowGraphView() {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			page.openEditor(new FlowGraphView(), FlowGraphView.ID, false);
			return (FlowGraphView) page.getActiveEditor();
		} catch (PartInitException e) {
			MessageDialog.openError(null, "Error", "Opps...Error opening view:" + e.getMessage());
		}
		return null;

	}

	/**
	 * return list of {@link VisualGraphView}
	 * 
	 * @return
	 */
	public final static java.util.List<VisualGraphView> getVisualGraphViews() {
		List<VisualGraphView> graphViews = new ArrayList<VisualGraphView>();
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editorReferences = page.getEditorReferences();
		for (IEditorReference editorReference : editorReferences) {
			IEditorPart editorPart = editorReference.getEditor(false);
			if (editorPart != null) {
				if (editorPart instanceof VisualGraphView) {
					graphViews.add((VisualGraphView) editorPart);
				}
			}
		}
		return graphViews;
	}

	/**
	 * return list of {@link FlowGraphView}
	 * 
	 * @return
	 */
	public final static java.util.List<FlowGraphView> getFlowGraphViews() {
		List<FlowGraphView> graphViews = new ArrayList<FlowGraphView>();
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editorReferences = page.getEditorReferences();
		for (IEditorReference editorReference : editorReferences) {
			IEditorPart editorPart = editorReference.getEditor(false);
			if (editorPart != null) {
				if (editorPart instanceof FlowGraphView) {
					graphViews.add((FlowGraphView) editorPart);
				}
			}
		}
		return graphViews;
	}

}
