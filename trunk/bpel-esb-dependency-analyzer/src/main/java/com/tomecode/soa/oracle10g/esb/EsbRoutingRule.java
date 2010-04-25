package com.tomecode.soa.oracle10g.esb;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbRoutingRule implements TreeNode {

	private String qname;
	private String serviceQName;

	public EsbRoutingRule(String qname, String serviceQName) {
		this.qname = qname;
		this.serviceQName = serviceQName;
	}

	public final String getQname() {
		return qname;
	}

	public final String getServiceQName() {
		return serviceQName;
	}

	public final String toString() {
		return serviceQName;
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

}
