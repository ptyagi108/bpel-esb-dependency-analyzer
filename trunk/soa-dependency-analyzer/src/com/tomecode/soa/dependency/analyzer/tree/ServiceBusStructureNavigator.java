package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.ProxyStructure;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.services.SplitJoinStructure;
import com.tomecode.soa.ora.osb10g.services.dependnecies.OsbActivityDependency;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Show structure of selected service bus
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ServiceBusStructureNavigator extends ViewPart implements HideView {

	public static final String ID = "view.servicebusstructurenavigator";

	/***
	 * selected object/tree item in tree
	 */
	private TreeItem selectedItem;

	private final ServiceBusStructureContentProvider contentProvider;

	private final LabelProviderImpl labelProvider;

	private final EmptyNode rootNode;

	private TreeViewer tree;

	public ServiceBusStructureNavigator() {
		rootNode = new EmptyNode();
		contentProvider = new ServiceBusStructureContentProvider();
		labelProvider = new LabelProviderImpl();
		setTitleToolTip("Service Bus - (Proxy or SplitJoin) Structure");
		setTitleImage(ImageFactory.ESB_PROCESS_STRUCTURE_TREE);
	}

	@Override
	public void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
		ColumnViewerToolTipSupport.enableFor(tree);
	}

	@Override
	public void setFocus() {

	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	/**
	 * show service bus structure
	 * 
	 * @param source
	 */
	public final void show(Object source) {
		if (source == null) {
			clearTree();
		} else {
			if (source instanceof Proxy) {
				rootNode.set(((Proxy) source).getStructure());
				tree.setInput(rootNode);
				tree.expandAll();
			} else if (source instanceof OraSB10gProject) {
				rootNode.set((OraSB10gProject) source);
				tree.setInput(rootNode);
				tree.expandAll();
			} else if (source instanceof SplitJoin) {
				rootNode.set(((SplitJoin) source).getStructure());
				tree.setInput(rootNode);
				tree.expandAll();
			} else {
				clearTree();
			}
		}
	}

	private final void clearTree() {
		rootNode.set(null);
		tree.setInput(rootNode);
	}

	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		MultiWorkspace multiWorkspaceInTree = findMutliWorkspaceInTree();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			clearTree();
		}
	}

	private final MultiWorkspace findMutliWorkspaceInTree() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof ProxyStructure) {
				return ((ProxyStructure) objectInTree).getProxy().getProject().getWorkpsace().getMultiWorkspace();
			} else if (objectInTree instanceof OraSB10gProject) {
				return ((OraSB10gProject) objectInTree).getWorkpsace().getMultiWorkspace();
			}
		}
		return null;
	}

	private final Workspace findWorkspaceInTree() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof ProxyStructure) {
				return ((ProxyStructure) objectInTree).getProxy().getProject().getWorkpsace();
			} else if (objectInTree instanceof OraSB10gProject) {
				return ((OraSB10gProject) objectInTree).getWorkpsace();
			}
		}
		return null;
	}

	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceInTree();
		if (workspace.equals(workspaceInTree)) {
			clearTree();
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Label provider for {@link ServiceBusStructureNavigator}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 * 
	 */
	private static final class LabelProviderImpl extends CellLabelProvider {

		public final Image getImage(Object element) {
			if (element instanceof OsbActivity) {
				return ((OsbActivity) element).getImage();
			} else if (element instanceof ProxyStructure) {
				return ((ProxyStructure) element).getImage();
			} else if (element instanceof Proxy) {
				return ((Proxy) element).getImage();
			} else if (element instanceof OraSB10gProject) {
				return ImageFactory.OSB_10G_PROJECT;
			} else if (element instanceof SplitJoinStructure) {
				return ((SplitJoinStructure) element).getImage();
			} else if (element instanceof SplitJoin) {
				return ((SplitJoin) element).getImage();
			} else if (element instanceof ImageFace) {
				return ((ImageFace) element).getImage();
			}
			return null;
		}

		@Override
		public final void update(ViewerCell cell) {
			cell.setText(cell.getElement().toString());
			cell.setImage(getImage(cell.getElement()));
		}

		public final String getToolTipText(Object element) {
			if (element instanceof ImageFace) {
				return ((ImageFace) element).getToolTip();
			}
			return element.toString();
		}
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	/**
	 * show in tree
	 * 
	 * @param object
	 */
	public final void showInTree(Object object) {
		clearSelectedItem();

		if (object instanceof OsbActivityDependency) {
			OsbActivityDependency activityDependency = (OsbActivityDependency) object;
			selectItemInTree(tree.getTree().getItems(), activityDependency.getActivity());
		}

	}

	private final void selectItemInTree(TreeItem[] items, OsbActivity activity) {
		for (TreeItem item : items) {
			if (activity.equals(item.getData())) {
				OsbActivity activityInTree = (OsbActivity) item.getData();
				if (activity.getParent().equals(activityInTree.getParent())) {
					item.setFont(GuiUtils.FONT_ITALIC_BOLD);
					item.setForeground(GuiUtils.COLOR_RED);
					selectedItem = item;
					tree.getTree().showItem(item);
					return;
				}
			} else {
				selectItemInTree(item.getItems(), activity);
			}
		}
	}

	private final void clearSelectedItem() {
		if (selectedItem != null) {
			if (selectedItem.isDisposed()) {
				selectedItem = null;
			} else {
				selectedItem.setFont(null);
				selectedItem.setForeground(GuiUtils.COLOR_BLACK);
			}
		}
	}
}
