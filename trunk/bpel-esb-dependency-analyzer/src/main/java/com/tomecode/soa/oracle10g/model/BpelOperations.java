package com.tomecode.soa.oracle10g.model;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelOperations implements TreeNode {
	private BpelProcess bpelProcess;

	/**
	 * {@link Operation}
	 */
	private final Vector<Operation> operations;

	public BpelOperations() {
		this.operations = new Vector<Operation>();
	}

	public BpelOperations(BpelProcess process) {
		this();
		this.bpelProcess = process;

	}

	public final void addOperation(Operation operation) {
		operations.add(operation);
	}

	public final Vector<Operation> getOperations() {
		return operations;
	}

	public final BpelProcess getBpelProcess() {
		return bpelProcess;
	}

	@Override
	public Enumeration<?> children() {
		return operations.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !operations.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return operations.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return operations.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return operations.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return operations.isEmpty();
	}

	public final String toString() {
		return bpelProcess.toString();
	}
}
