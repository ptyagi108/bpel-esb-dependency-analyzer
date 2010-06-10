package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.components.UnknownPanel;
import com.tomecode.soa.bpel.dependency.analyzer.gui.components.UnknownPanel.UnknonwNode;
import com.tomecode.soa.oracle10g.bpel.BpelProject;

/**
 * 
 * simple tree for display activities in {@link BpelProject} which use
 * {@link UnknownPanel}
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelUnknownOperationTree extends BasicTree {

	private static final long serialVersionUID = -3855833816364776303L;

	/**
	 * Constructor
	 */
	public BpelUnknownOperationTree() {
		super();
		setCellRenderer(new IconTreeRenderer());
	}

	@Override
	public void showPopupMenu(int x, int y) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * set {@link UnknonwNode}
	 * 
	 * @param unknownNode
	 */
	public void setUnknownNode(UnknonwNode unknownNode) {
		treeModel.setRoot(unknownNode);
		expandAllNodes(new TreePath(unknownNode));
	}

}
