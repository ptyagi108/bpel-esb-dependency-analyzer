package com.tomecode.soa.oracle10g.bpel;

import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.ErrorNode;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * Partner link operation
 * 
 * @author Tomas Frastia
 * 
 */
public final class Operation implements TreeNode {

	private final ActivtyType activtyType;
	private String activity;

	private String name;

	private String operation;

	private PartnerLinkBinding partnerLinkBinding;

	private List<Activity> activities;

	/**
	 * Constructor
	 * 
	 * @param activity
	 * @param name
	 * @param operation
	 * @param partnerLinkBinding
	 */
	public Operation(String activity, String name, String operation, PartnerLinkBinding partnerLinkBinding, List<Activity> activities) {
		this.activity = activity;
		this.activtyType = ActivtyType.parseActivtyType(activity);
		this.name = name;
		this.operation = operation;
		this.partnerLinkBinding = partnerLinkBinding;
		this.activities = activities;
	}

	public final List<Activity> getActivities() {
		return activities;
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
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		if (partnerLinkBinding != null) {
			return (partnerLinkBinding.getDependencyProject() != null);
		}
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {

		Project project = (Project) partnerLinkBinding.getDependencyProject();
		if (project.getType() == ProjectType.ORACLE10G_BPEL) {
			BpelProject bpelProcess = (BpelProject) partnerLinkBinding.getDependencyProject();
			if (bpelProcess == null || bpelProcess.getBpelOperations() == null) {
				return new ErrorNode("ERROR:not found " + partnerLinkBinding.getName(), partnerLinkBinding.getWsdlLocation(), null);
			}
			return bpelProcess.getBpelOperations();
		}
		return project;

	}

	@Override
	public int getChildCount() {
		return getAllowsChildren() ? 1 : 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return partnerLinkBinding.getDependencyProject();
	}

	@Override
	public boolean isLeaf() {
		return !getAllowsChildren();
	}

	public final BpelProject getBpelProcess() {
		return (BpelProject) partnerLinkBinding.getDependencyProject();
	}

	public final ActivtyType getActivtyType() {
		return activtyType;
	}

	public final String toString() {
		return activity + ":" + name + ":" + operation;
	}

}
