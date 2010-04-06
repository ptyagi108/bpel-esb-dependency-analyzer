package com.tomecode.soa.bpel.model;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * Partner link operation
 * 
 * @author Tomas Frastia
 * 
 */
public final class Operation implements TreeNode {

	private String activity;

	private String name;

	private String operation;

	private PartnerLinkBinding partnerLinkBinding;

	private final Vector<BpelProcess> bpelProcesses;

	/**
	 * Constructor
	 * 
	 * @param activity
	 * @param name
	 * @param operation
	 * @param partnerLinkBinding
	 */
	public Operation(String activity, String name, String operation, PartnerLinkBinding partnerLinkBinding) {
		this.bpelProcesses = new Vector<BpelProcess>();
		this.activity = activity;
		this.name = name;
		this.operation = operation;
		this.partnerLinkBinding = partnerLinkBinding;
		if (partnerLinkBinding != null) {
			bpelProcesses.add(partnerLinkBinding.getBpelProcess());
		}
	}

	public final String getOperation() {
		return operation;
	}

	public final String getActivity() {
		return activity;
	}

	public final String getName() {
		return name;
	}

	public final PartnerLinkBinding getPartnerLinkBinding() {
		return partnerLinkBinding;
	}

	@Override
	public Enumeration<?> children() {
		return bpelProcesses.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !bpelProcesses.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		BpelProcess bpelProcess = bpelProcesses.get(childIndex);
		return bpelProcess.getBpelOperations();
	}

	@Override
	public int getChildCount() {
		return bpelProcesses.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return bpelProcesses.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		if (partnerLinkBinding != null) {
			return partnerLinkBinding.getBpelProcess();
		}
		return null;
	}

	@Override
	public boolean isLeaf() {
		return bpelProcesses.isEmpty();
	}
	
	public final BpelProcess getBpelProcess(){
		return bpelProcesses.get(0);
	}

	public final String toString() {
		return activity + ":" + name + ":" + operation;
	}

}
