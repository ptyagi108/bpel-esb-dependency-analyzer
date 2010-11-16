package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
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
import com.tomecode.soa.dependency.analyzer.view.PropertiesViewOsbAdapter;
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
	 * find reference for {@link PropertiesViewOsbAdapter}
	 * 
	 * @return
	 */
	public static final PropertiesViewOsbAdapter getPropertiesViewOsbAdapter() {
		return (PropertiesViewOsbAdapter) findView(PropertiesViewOsbAdapter.ID);
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
	 * find reference for {@link ServiceOperationsDepNavigator}
	 * 
	 * @return
	 */
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

	// ********************************************************************
	// ********************************************************************
	// ********************************************************************
	/**
	 * create {@link Composite} with {@link GridLayout}
	 * 
	 * @param sc
	 * @return
	 */
	public final static Composite createCompositeWithGrid(ScrolledComposite sc) {
		Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

		return composite;
	}

	/**
	 * create composite for grid
	 * 
	 * @param parent
	 * @param numColumns
	 * @return
	 */
	public final static Composite createCompositeWithGrid(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(numColumns, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

		return composite;
	}

	/**
	 * create new group for grid
	 * 
	 * @param composite
	 * @param title
	 * @param numColumns
	 * @return
	 */
	public static final Group createGroupWithGrid(Composite composite, String title, int numColumns) {
		Group group = new Group(composite, SWT.SHADOW_IN);
		group.setText(title);
		group.setLayout(new GridLayout(numColumns, false));
		group.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
		return group;
	}

	/**
	 * create label
	 * 
	 * @param parent
	 * @param title
	 * @return
	 */
	public final static Label createLabelWithGrid(Composite parent, String title) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(title);
		return label;
	}

	/**
	 * create text for grid
	 * 
	 * @param parent
	 * @return
	 */
	public static final Text createTextWithGrid(Composite parent) {
		return createTextWithGrid(parent, "");
	}

	/**
	 * create text for grid
	 * 
	 * @param parent
	 * @param text
	 * @return
	 */
	public static final Text createTextWithGrid(Composite parent, String text) {
		Text textComponent = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		textComponent.setLayoutData(gridData);
		textComponent.setText(text);
		return textComponent;
	}

	/**
	 * create new group for grid
	 * 
	 * @param composite
	 * @param title
	 * @return
	 */
	public static final Group createGroupWithGrid(Composite composite, String title) {
		return createGroupWithGrid(composite, title, 2);
	}

	/**
	 * create bold font
	 * 
	 * @param font
	 * @return
	 */
	public final static Font createFont() {
		Font font = Display.getCurrent().getSystemFont();
		FontData fontData = font.getFontData()[0];
		return new Font(Display.getCurrent(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));

	}

	public final static org.eclipse.draw2d.Label createLabel2dBold(String text) {
		org.eclipse.draw2d.Label label = new org.eclipse.draw2d.Label(text);
		label.setFont(createFont());
		return label;
	}
}
