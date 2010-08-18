package com.tomecode.soa.dependency.analyzer.tree;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.tree.node.WorkspaceRootNode;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;
import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * 
 * 
 * Tree navigator for workspaces, contains all workspaces and show dependencies
 * between
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspacesNavigator extends ViewPart implements ISelectionChangedListener {

	public static final String ID = "view.workspacenavigator";

	private final WorkspaceRootNode rootNode;

	private TreeViewer tree;

	public WorkspacesNavigator() {
		rootNode = new WorkspaceRootNode();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.addSelectionChangedListener(this);
		tree.setLabelProvider(new WorkspacesLabelProvider());
		tree.setContentProvider(new WorkspacesContentProvider());

		// hookContextMenu();

		List<MultiWorkspace> multiWorkspaces = ApplicationManager.getInstance().getMultiWorkspaces();
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			rootNode.add(multiWorkspace);
		}

		tree.setInput(rootNode);
		// new ISelectionChangedListener() {
		//
		// @Override
		// public final void selectionChanged(SelectionChangedEvent event) {
		// System.out.println(event);
		//
		// IViewDescriptor descriptor =
		// PlatformUI.getWorkbench().getViewRegistry().find(BpelProcessStructureNavigator.ID);
		// IViewPart iViewPart =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(BpelProcessStructureNavigator.ID);
		// iViewPart.toString();
		// }
		// });
	}

	/**
	 * hook context menu
	 */
	private final void hookContextMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			@Override
			public final void menuAboutToShow(IMenuManager manager) {
				OpenNewWorkspaceAction action = new OpenNewWorkspaceAction();
				action.setText("Add new workspace");
				manager.add(action);

			}
		});

		Menu menu = menuManager.createContextMenu(tree.getControl());
		tree.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tree);
	}

	public final class OpenNewWorkspaceAction extends Action {
		public final void run() {
			tree.setInput(new String("222"));
		}
	}

	@Override
	public final void setFocus() {

	}

	@Override
	public final void selectionChanged(SelectionChangedEvent event) {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart iViewPart = workbenchPage.findView(BpelProcessStructureNavigator.ID);

		IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
		if (!selection.isEmpty()) {

			if (iViewPart != null) {
				BpelProcessStructureNavigator navigator = (BpelProcessStructureNavigator) iViewPart;
				navigator.showProcess(selection.getFirstElement());
			}

			iViewPart = workbenchPage.findView(ServiceOperationsDepNavigator.ID);
			if (iViewPart != null) {
				ServiceOperationsDepNavigator navigator = (ServiceOperationsDepNavigator) iViewPart;
				navigator.showOperationDepenendecies(selection.getFirstElement());
			}

			iViewPart = workbenchPage.findView(VisualGraphView.ID);
			if (iViewPart != null) {
				VisualGraphView graphView = (VisualGraphView) iViewPart;
				graphView.showGraph(selection.getFirstElement());
			}

		}

	}

	public void getMWorkspaceNames() {

	}

	public final void newMultiWorkspace(final MultiWorkspace multiWorkspace) {
		rootNode.add(multiWorkspace);
		tree.refresh(rootNode);
	}

	public final void updateMultiWorkspace(MultiWorkspace multiWorkspace) {
		rootNode.add(multiWorkspace);
		tree.refresh(rootNode);
	}
}
