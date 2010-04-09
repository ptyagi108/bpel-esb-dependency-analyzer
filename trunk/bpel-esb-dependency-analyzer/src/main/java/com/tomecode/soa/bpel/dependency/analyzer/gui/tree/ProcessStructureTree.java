package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import javax.swing.tree.TreePath;

import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;

/**
 * 
 * Display structure of selected process in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProcessStructureTree extends BasicTree {

	private static final long serialVersionUID = 1258506152291476233L;

	public ProcessStructureTree() {
		super();
	}

	/**
	 * display select process in {@link WorkspaceTree}
	 * 
	 * @param bpelProcessStrukture
	 */
	public final void addBpelProcessStrukture(BpelProcessStrukture bpelProcessStrukture) {
		treeModel.setRoot(bpelProcessStrukture);
		expandAllNodes(new TreePath(bpelProcessStrukture));
	}

}
