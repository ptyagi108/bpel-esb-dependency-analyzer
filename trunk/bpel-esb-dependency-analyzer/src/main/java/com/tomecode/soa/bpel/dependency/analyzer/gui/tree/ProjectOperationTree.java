package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.model.BpelOperations;

/**
 * 
 * Display operation from selected proces in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectOperationTree extends BasicTree {

	private static final long serialVersionUID = -3750125284965106516L;

	public ProjectOperationTree() {
		super();
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
}
