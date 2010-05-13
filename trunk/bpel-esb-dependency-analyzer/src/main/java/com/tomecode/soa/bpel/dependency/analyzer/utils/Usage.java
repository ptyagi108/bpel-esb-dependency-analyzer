package com.tomecode.soa.bpel.dependency.analyzer.utils;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.bpel.activity.Activity;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class Usage implements TreeNode {

	private Activity activity;

	/**
	 * Constructor
	 * 
	 * @param activity
	 */
	public Usage(Activity activity) {
		this.activity = activity;
	}

	public final Activity getActivity() {
		return activity;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	public final String toString() {
		return activity.toString();
	}
}
