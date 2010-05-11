package com.tomecode.soa.oracle10g.bpel;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.bpel.activity.Activity;

/**
 * 
 * View the BPEL process tree
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProcessStrukture implements TreeNode {

	private final Vector<Activity> activities;

	private BpelProject bpelProcess;

	public BpelProcessStrukture() {
		this.activities = new Vector<Activity>();
	}

	public BpelProcessStrukture(BpelProject bpelProcess) {
		this();
		this.bpelProcess = bpelProcess;
	}

	public final void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	@Override
	public Enumeration<?> children() {
		return activities.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !activities.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return activities.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return activities.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return activities.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return bpelProcess;
	}

	@Override
	public boolean isLeaf() {
		return activities.isEmpty();
	}

	public final String toString() {
		return bpelProcess.toString();
	}
}
