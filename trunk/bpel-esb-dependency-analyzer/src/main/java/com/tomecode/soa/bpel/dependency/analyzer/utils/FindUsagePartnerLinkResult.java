package com.tomecode.soa.bpel.dependency.analyzer.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.bpel.activity.Activity;
import com.tomecode.soa.oracle10g.bpel.activity.PartnerLink;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class FindUsagePartnerLinkResult implements FindUsage {

	private PartnerLink partnerLink;

	private List<Usage> activities;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public FindUsagePartnerLinkResult(PartnerLink partnerLink) {
		this.partnerLink = partnerLink;
		this.activities = new ArrayList<Usage>();
	}

	public final PartnerLink getPartnerLink() {
		return partnerLink;
	}

	public void addUsage(Activity activity) {
		activities.add(new Usage(activity));
	}

	@Override
	public Enumeration<?> children() {
		return null;
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
		return null;
	}

	@Override
	public boolean isLeaf() {
		return activities.isEmpty();
	}

	public final String toString() {
		return partnerLink.toString();
	}
}
