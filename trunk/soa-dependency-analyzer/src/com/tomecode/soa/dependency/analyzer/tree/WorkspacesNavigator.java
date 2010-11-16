package com.tomecode.soa.dependency.analyzer.tree;

import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.actions.LinkWithNavigatorAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.OpenNewVisualViewAction;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PopupMenuUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.WorkspaceRootNode;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * 
 * Tree navigator for workspaces, contains all workspaces and show dependencies
 * between
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class WorkspacesNavigator extends ViewPart implements ISelectionChangedListener, IDoubleClickListener, HideView {

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
		tree.addDoubleClickListener(this);
		tree.setLabelProvider(new WorkspacesLabelProvider());
		tree.setContentProvider(new WorkspacesContentProvider());

		hookContextMenu();

		List<MultiWorkspace> multiWorkspaces = ApplicationManager.getInstance().getMultiWorkspaces();
		for (MultiWorkspace multiWorkspace : multiWorkspaces) {
			rootNode.add(multiWorkspace);
		}

		tree.setInput(rootNode);
		IToolBarManager barManager = getViewSite().getActionBars().getToolBarManager();
		barManager.add(new OpenNewVisualViewAction());
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

	public final void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	/**
	 * add new {@link MultiWorkspace} to tree
	 * 
	 * @param multiWorkspace
	 */
	public final void newMultiWorkspace(MultiWorkspace multiWorkspace) {
		rootNode.add(multiWorkspace);
		tree.refresh(rootNode);
	}

	/**
	 * refresh multi workspace nodes
	 * 
	 * @param multiWorkspace
	 */
	public final void updateMultiWorkspace(MultiWorkspace oldMultiWorkspace, MultiWorkspace newMultiWorkspace) {
		rootNode.refreshMultiWorkspaceNode(oldMultiWorkspace, newMultiWorkspace);
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

	/**
	 * remove selected {@link MultiWorkspace}
	 * 
	 * @return
	 */
	public final MultiWorkspace removeMultiWorkspace() {
		IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
		if (!selection.isEmpty()) {
			rootNode.remove(selection.getFirstElement());
			tree.refresh();
			return ApplicationManager.getInstance().removeMultiWorkspace(selection.getFirstElement());
		}
		return null;
	}

	/**
	 * remove selected {@link Workspace}
	 * 
	 * @return
	 */
	public final Workspace removeSelectedWorkspace() {
		IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
		if (!selection.isEmpty()) {
			return (Workspace) selection.getFirstElement();
		}
		return null;
	}

	public final void refreshTree() {
		tree.refresh();
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	@Override
	public final void doubleClick(DoubleClickEvent event) {
		try {
			IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
			if (!selection.isEmpty()) {
				BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
				if (bpelProcessStructureNavigator != null) {
					bpelProcessStructureNavigator.showProcessStructure(selection.getFirstElement());
				}
				ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
				if (serviceOperationsDepNavigator != null) {
					serviceOperationsDepNavigator.show(selection.getFirstElement());
				}
				VisualGraphView visualGraphView = GuiUtils.getVisualGraphView();
				if (visualGraphView != null) {
					visualGraphView.showGraph(selection.getFirstElement());
				}

				showServices(selection.getFirstElement());
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "oops2 error:" + e.getMessage());
		}
	}

	@Override
	public final void selectionChanged(SelectionChangedEvent event) {
		try {
			IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
			if (!selection.isEmpty()) {
				showServices(selection.getFirstElement());
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "oops2 error:" + e.getMessage());
		}
	}

	/**
	 * show information about selected node in {@link ProjectServicesNavigator}
	 * and {@link ProjectFilesNavigator} and {@link PropertiesView}
	 * 
	 * @param selectedNode
	 */
	private final void showServices(Object selectedNode) {
		ProjectFilesNavigator projectFilesNavigator = GuiUtils.getProjectStructureNavigator();
		if (projectFilesNavigator != null) {
			projectFilesNavigator.showProjectFiles(selectedNode);
		}
		ProjectServicesNavigator projectServicesNavigator = GuiUtils.getProjectServicesNavigator();
		if (projectServicesNavigator != null) {
			projectServicesNavigator.show(selectedNode);
		}
		PropertiesView propertiesView = GuiUtils.getPropertiesView();
		if (propertiesView != null) {
			propertiesView.show(selectedNode);
		}
	}

	/**
	 * {@link LabelProvider} for {@link WorkspacesNavigator}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	final class WorkspacesLabelProvider extends LabelProvider {

		public final Image getImage(Object element) {
			if (element instanceof ImageFace) {
				return ((ImageFace) element).getImage();
			}
			return null;
		}
	}
}
