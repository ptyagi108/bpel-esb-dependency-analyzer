package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import com.tomecode.soa.oracle10g.bpel.Workspace;

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

}
