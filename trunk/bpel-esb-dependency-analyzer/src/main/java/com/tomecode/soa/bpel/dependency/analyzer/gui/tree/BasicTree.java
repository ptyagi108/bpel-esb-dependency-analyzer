package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Basic tree
 * 
 * @author Tomas Frastia
 * 
 */
public class BasicTree extends JTree {

	private static final long serialVersionUID = 2719679725572727785L;
	/**
	 * default tree model
	 */
	protected final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("root"));

	public BasicTree() {
		setModel(treeModel);
		treeModel.setRoot(null);
	}

	/**
	 * expand all nodes in tree
	 * 
	 * @param parent
	 */
	protected final void expandAllNodes(TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				Object o = e.nextElement();
				if (o instanceof TreeNode) {
					TreePath path = parent.pathByAddingChild((TreeNode) o);
					expandAllNodes(path);
				}
			}
		}
		expandPath(parent);
	}
}
