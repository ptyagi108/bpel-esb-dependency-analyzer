package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import com.tomecode.soa.bpel.model.Workspace;

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
	}

	public WorkspaceTree(Workspace workspace) {
		this();
		treeModel.setRoot(workspace);
	}

//	/**
//	 * 
//	 * @author Tomas Frastia
//	 * 
//	 */
	// private final class ProcessTreeCellRender implements TreeCellRenderer {
	//
	// private static final long serialVersionUID = -6314674946997479687L;
	//
	// public final Component getTreeCellRendererComponent(JTree tree, Object
	// value, boolean selected, boolean expanded, boolean leaf, int row, boolean
	// hasFocus) {
	//
	// DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new
	// DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value,
	// selected, expanded, leaf, row, hasFocus);
	//
	// if (value instanceof BpelProcess) {
	//
	// BpelProcess bpelProcess = (BpelProcess) value;
	// if (bpelProcess.getParent() != null) {
	//
	// // if (rootBpelProcess.equals(bpelProcess)) {
	// rnd.setForeground(Color.red);
	// } else {
	// rnd.setForeground(Color.black);
	// }
	// rnd.setText(value.toString());
	//
	// }
	//
	// return rnd;
	// }
	// }
}
