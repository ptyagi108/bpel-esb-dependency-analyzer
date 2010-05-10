package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EmptyNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.esb.EsbProject;

/**
 * 
 * Display operation from selected proces in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectOperationTree extends BasicTree {

	private static final long serialVersionUID = -3750125284965106516L;

	/**
	 * Constructor
	 */
	public ProjectOperationTree() {
		super();
		setCellRenderer(new OperationTreeRenderer());
	}

	/**
	 * display selected bpel proces in {@link WorkspaceTree}
	 * 
	 * @param bpelProcess
	 */
	public final void addBpelProcessOperations(BpelOperations bpelOperations) {
		treeModel.setRoot(bpelOperations);
		expandPath(new TreePath(bpelOperations));
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class OperationTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof BpelOperations) {
				rnd.setIcon(IconFactory.PROCESS);
			} else if (value instanceof Operation) {
				rnd.setIcon(((Operation) value).getActivtyType().getImageIcon());
			} else if (value instanceof EsbProject) {
				rnd.setIcon(IconFactory.ESB);
			} else if (value instanceof EmptyNode) {
				rnd.setIcon(IconFactory.ESB);
			}

			return rnd;
		}
	}
}
