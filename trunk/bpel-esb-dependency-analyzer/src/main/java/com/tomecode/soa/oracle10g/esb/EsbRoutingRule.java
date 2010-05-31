package com.tomecode.soa.oracle10g.esb;

import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbRoutingRule implements TreeNode, Serializable {

	private static final long serialVersionUID = -342513167744595428L;
	private String qname;
	private String serviceQName;

	/**
	 * Constructor
	 * 
	 * @param qname
	 * @param serviceQName
	 */
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
