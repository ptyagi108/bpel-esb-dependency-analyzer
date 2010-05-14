package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.process.Project;

/**
 * Display all BPEL/ESB projects in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceTree extends BasicTree {

	private static final long serialVersionUID = -14952772269846358L;

	/**
	 * Constructor
	 */
	public WorkspaceTree() {
		super();
		setRootVisible(false);
		setCellRenderer(new WorkspaceTreeRenderer());
		createMenuItem("Find Usage for BPEL project", "findUsageBpelProject");
	}

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspaceTree(MultiWorkspace multiWorkspace) {
		this();
		treeModel.setRoot(multiWorkspace);
		expandProjectNodes(new TreePath(multiWorkspace));
	}

	/**
	 * expand all nodes
	 * 
	 * @param parent
	 */
	protected final void expandProjectNodes(TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (int i = 0; i <= node.getChildCount() - 1; i++) {
				TreePath path = parent.pathByAddingChild(node.getChildAt(i));
				if (path.getLastPathComponent() instanceof Project) {
					expandPath(parent);
				} else {
					expandProjectNodes(path);
				}
			}
		}
		expandPath(parent);
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class WorkspaceTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof BpelProject) {
				rnd.setIcon(IconFactory.PROCESS);
			} else if (value instanceof EsbProject) {
				rnd.setIcon(IconFactory.ESB);
			}
			if (value instanceof Project) {
				if (!((Project) value).isInJws()) {
					rnd.setForeground(Color.BLACK);
					rnd.setFont(rnd.getFont().deriveFont(Font.BOLD));

				}
			}

			return rnd;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof BpelProject) {
				enableMenuItem("findUsageBpelProject", true);
			} else {
				enableMenuItem(null, false);
			}
		}
		popupMenu.show(this, x, y);
	}
}
