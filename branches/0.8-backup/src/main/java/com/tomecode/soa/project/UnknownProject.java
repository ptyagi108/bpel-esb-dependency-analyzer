package com.tomecode.soa.project;

import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;

/**
 * Unknown project or service
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownProject extends Project {

	private static final long serialVersionUID = 6364329347778611989L;

	private PartnerLinkBinding partnerLinkBinding;

	/**
	 * Constructor
	 */
	public UnknownProject(String name) {
		super(name, null, ProjectType.UNKNOWN);
	}

	public UnknownProject(PartnerLinkBinding partnerLinkBinding) {
		super(partnerLinkBinding.getName(), null, ProjectType.UNKNOWN);
		this.partnerLinkBinding = partnerLinkBinding;
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

	@Override
	public ImageIcon getIcon() {
		return IconFactory.UNKNOWN;
	}

	public final String toString() {
		return partnerLinkBinding.getWsdlLocation();
	}

	public final PartnerLinkBinding getPartnerLinkBinding() {
		return partnerLinkBinding;
	}

}
