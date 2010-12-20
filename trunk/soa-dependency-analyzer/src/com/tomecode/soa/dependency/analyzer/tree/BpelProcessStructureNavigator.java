package com.tomecode.soa.dependency.analyzer.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.Operation;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProcessStrukture;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Show BPEL process structure (tree structure of activities)
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BpelProcessStructureNavigator extends ViewPart implements HideView {

	public static final String ID = "view.bpelprocessstructurenavigator";

	private TreeItem selectedFont;

	private final BpelProcessStructureContentProvider contentProvider;

	private final BpelProcessStructureLabelProvider labelProvider;

	private final EmptyNode rootNode;

	private TreeViewer tree;

	/**
	 * Constructor
	 */
	public BpelProcessStructureNavigator() {
		setTitleImage(ImageFactory.BPEL_PROCESS_STRUCTURE_TREE);
		setTitleToolTip("BPEL process structure");
		rootNode = new EmptyNode();
		contentProvider = new BpelProcessStructureContentProvider();
		labelProvider = new BpelProcessStructureLabelProvider();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLabelProvider(labelProvider);
		tree.setContentProvider(contentProvider);
		ColumnViewerToolTipSupport.enableFor(tree);
	}

	@Override
	public final void setFocus() {

	}

	public final void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	/**
	 * show process structure
	 * 
	 * @param source
	 */
	public final void show(Object source) {

		if (source instanceof BpelOperations) {
			source = ((BpelOperations) source).getBpelProcess();
		}
		if (source instanceof Ora10gBpelProject) {
			rootNode.set(((Ora10gBpelProject) source).getBpelProcessStrukture());
			tree.setInput(rootNode);
			tree.expandAll();
		} else if (source instanceof OpenEsbBpelProject) {
			rootNode.set(source);
			tree.setInput(rootNode);
			tree.expandAll();
		} else if (source instanceof OpenEsbBpelProcess) {
			rootNode.set(source);
			tree.setInput(rootNode);
			tree.expandAll();
		} else {
			clearTree();
		}
	}

	/**
	 * if user remove {@link MultiWorkspace} from {@link WorkspacesNavigator}
	 * then check whether, has a reference
	 * 
	 * @param multiWorkspace
	 */
	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		MultiWorkspace multiWorkspaceInTree = findMultiWorkspaceForStructure();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			clearTree();
		}
	}

	/**
	 * if user remove {@link Workspace} from {@link WorkspacesNavigator} then
	 * check whether, has a reference
	 * 
	 * @param workspace
	 */
	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceForStructure();
		if (workspaceInTree != null) {
			if (workspace.equals(workspaceInTree)) {
				clearTree();
			}
		}
	}

	private final void clearTree() {
		rootNode.set(null);
		tree.setInput(null);
	}

	private final Workspace findWorkspaceForStructure() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof Ora10gBpelProcessStrukture) {
				return ((Ora10gBpelProcessStrukture) objectInTree).getProject().getWorkpsace();
			} else if (objectInTree instanceof OpenEsbBpelProject) {
				return ((OpenEsbBpelProject) objectInTree).getWorkpsace();
			} else if (objectInTree instanceof OpenEsbBpelProcess) {
				return ((OpenEsbBpelProcess) objectInTree).getProject().getWorkpsace();
			}
		}

		return null;
	}

	private final MultiWorkspace findMultiWorkspaceForStructure() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof Ora10gBpelProcessStrukture) {
				return ((Ora10gBpelProcessStrukture) objectInTree).getProject().getWorkpsace().getMultiWorkspace();
			} else if (objectInTree instanceof OpenEsbBpelProject) {
				return ((OpenEsbBpelProject) objectInTree).getWorkpsace().getMultiWorkspace();
			} else if (objectInTree instanceof OpenEsbBpelProcess) {
				return ((OpenEsbBpelProcess) objectInTree).getProject().getWorkpsace().getMultiWorkspace();
			}
		}

		return null;
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	public final void showActvityInTree(Object object) {
		clearSelectedItem();
		selectedFont = null;
		if (object instanceof Operation) {
			Operation operation = (Operation) object;
			List<Activity> activities = findParentActivity(operation.getActivity());

			TreeItem[] items = tree.getTree().getItems();
			Iterator<Activity> iterator = activities.iterator();
			while (iterator.hasNext()) {

				Activity activity = iterator.next();
				TreeItem item = findActivityInItem(activity, items);
				if (item != null) {
					items = item.getItems();
				}

				if (!iterator.hasNext()) {
					if (item != null) {
						item.setFont(GuiUtils.FONT_ITALIC_BOLD);
						item.setForeground(GuiUtils.COLOR_RED);
					}
					selectedFont = item;
				}
			}

		}
	}

	private final void clearSelectedItem() {
		if (selectedFont != null) {
			if (selectedFont.isDisposed()) {
				selectedFont = null;
			} else {
				selectedFont.setFont(null);
				selectedFont.setForeground(GuiUtils.COLOR_BLACK);
			}
		}
	}

	private final TreeItem findActivityInItem(Activity activity, TreeItem[] items) {
		for (TreeItem item : items) {
			if (activity.equals(item.getData())) {
				return item;
			}
		}
		return null;
	}

	/**
	 * find parent activity
	 * 
	 * @param activity
	 * @return
	 */
	private final List<Activity> findParentActivity(Activity activity) {
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(activity);
		while (activity.getParent() != null) {
			activity = activity.getParent();
			activities.add(activity);
		}
		Collections.reverse(activities);
		return activities;
	}

}
