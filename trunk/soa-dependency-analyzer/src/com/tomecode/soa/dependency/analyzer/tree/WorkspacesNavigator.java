package com.tomecode.soa.dependency.analyzer.tree;

import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.actions.LinkWithNavigatorAction;
import com.tomecode.soa.dependency.analyzer.gui.utils.PopupMenuUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.WorkspaceRootNode;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

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

	/**
	 * if is true then if click in {@link VisualGraphView} then show in tree
	 */
	private LinkWithNavigatorAction linkWithNavigatorAction;
	private TreeViewer tree;

	public WorkspacesNavigator() {
		rootNode = new WorkspaceRootNode();
		setTitleToolTip("Workspace Navigator");
		setTitleImage(ImageFactory.WORKSPACE_NAVIGATOR);
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.addSelectionChangedListener(this);
		tree.setLabelProvider(new WorkspacesLabelProvider());
		tree.setContentProvider(new WorkspacesContentProvider());

		hookContextMenu();

		List<MultiWorkspace> multiWorkspaces = ApplicationManager.getInstance().getMultiWorkspaces();
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			rootNode.add(multiWorkspace);
		}

		tree.setInput(rootNode);

		// linkWithNavigatorAction = new LinkWithNavigatorAction();
		// IActionBars actionBars = getViewSite().getActionBars();
		// actionBars.getToolBarManager().add(linkWithNavigatorAction);
		// actionBars.getMenuManager().add(linkWithNavigatorAction);
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

				IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
				if (!selection.isEmpty()) {
					PopupMenuUtils.fillWorksapceNavigator(selection.getFirstElement(), manager);
				} else {
					PopupMenuUtils.fillEmptyWorksapceNavigator(manager);
				}
			}
		});

		Menu menu = menuManager.createContextMenu(tree.getControl());
		tree.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tree);
	}

	@Override
	public final void setFocus() {
		tree.getTree().setFocus();
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
				graphView.showGraph(selection.getFirstElement(), true);
			}

			iViewPart = workbenchPage.findView(PropertiesView.ID);
			if (iViewPart != null) {
				PropertiesView propertiesView = (PropertiesView) iViewPart;
				propertiesView.showProperties(selection.getFirstElement());
			}
			iViewPart = workbenchPage.findView(ProjectStructureNavigator.ID);
			if (iViewPart != null) {
				ProjectStructureNavigator projectStructureNavigator = (ProjectStructureNavigator) iViewPart;
				projectStructureNavigator.showProjectFiles(selection.getFirstElement());
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

	/**
	 * show selected node in {@link VisualGraphView} if
	 * {@link #linkWithNavigatorAction} is checked
	 * 
	 * @param data
	 */
	public final void showInTree(Object data) {
		if (linkWithNavigatorAction.isChecked()) {
			if (data instanceof Workspace) {
				Workspace workspace = (Workspace) data;
				TreePath treePath = new TreePath(new Object[] { workspace.getMultiWorkspace(), data });
				// tree.expandToLevel(treePath, 2);
				// tree.getTree().sc
				tree.setExpandedState(treePath, true);
			}
		}
	}
}
