package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;

/**
 * Display all BPEL/ESB projects in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceTree extends BasicTree {

	private static final long serialVersionUID = -14952772269846358L;

	public WorkspaceTree() {
		super();
		setRootVisible(false);
		setCellRenderer(new WorkspaceTreeRenderer());
	}

	public WorkspaceTree(Workspace workspace) {
		this();
		treeModel.setRoot(workspace);
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
			}

			return rnd;
		}
	}
}
