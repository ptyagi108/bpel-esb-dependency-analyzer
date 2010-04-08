package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.model.BpelProcessStrukture;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProcessStructureTree extends BasicTree {

	private static final long serialVersionUID = 1258506152291476233L;

	public ProcessStructureTree() {
		super();
	}

	public final void addBpelProcessStrukture(BpelProcessStrukture bpelProcessStrukture) {
		treeModel.setRoot(bpelProcessStrukture);
		expandAllNodes(new TreePath(bpelProcessStrukture));
	}

	public final void clear() {
		treeModel.setRoot(null);
		
	}
}
