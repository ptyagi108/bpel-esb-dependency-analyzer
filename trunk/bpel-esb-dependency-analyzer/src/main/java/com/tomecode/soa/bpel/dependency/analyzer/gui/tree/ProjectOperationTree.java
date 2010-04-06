package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.model.BpelOperations;

/**
 * 
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
	 * add bpel process to
	 * 
	 * @param bpelProcess
	 */
	public final void addBpelProcessOperations(BpelOperations bpelOperations) {
		treeModel.setRoot(bpelOperations);
		expandAllNodes(new TreePath(bpelOperations));
	}

}
