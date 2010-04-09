package com.tomecode.soa.oracle10g.esb;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbOperation implements TreeNode {

	private String qname;

	private String wsdlOperation;

	private final Vector<EsbRoutingRule> esbRoutingRules;

	public EsbOperation() {
		esbRoutingRules = new Vector<EsbRoutingRule>();
	}

	public EsbOperation(String qname, String wsdlOperation) {
		this();
		this.qname = qname;
		this.wsdlOperation = wsdlOperation;
	}

	public final String getQname() {
		return qname;
	}

	public final String getWsdlOperation() {
		return wsdlOperation;
	}

	public final void addOperation(EsbOperation esbOperation) {
		esbOperation.addOperation(esbOperation);
	}

	@Override
	public Enumeration<?> children() {
		return esbRoutingRules.elements();
	}

	@Override
	public final boolean getAllowsChildren() {
		return esbRoutingRules.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;// return esbRoutingRules.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return esbRoutingRules.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return esbRoutingRules.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return this;
	}

	@Override
	public boolean isLeaf() {
		return esbRoutingRules.isEmpty();
	}

	public final void addRoutingRule(EsbRoutingRule esbRoutingRule) {
		esbRoutingRules.add(esbRoutingRule);
	}

}
