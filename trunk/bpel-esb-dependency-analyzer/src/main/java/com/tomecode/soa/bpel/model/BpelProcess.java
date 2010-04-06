package com.tomecode.soa.bpel.model;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * 
 * This object contais basic info about bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProcess implements TreeNode, Cloneable {

	private String id;

	private String src;

	private File bpelXmlFile;

	/**
	 * {@link PartnerLinkBinding}
	 */
	private final Vector<PartnerLinkBinding> partnerLinkBindings;

	private final BpelOperations bpelOperations;

	private final BpelProcessStrukture bpelProcessStrukture;

	/**
	 * Constructor
	 */
	public BpelProcess() {
		this.partnerLinkBindings = new Vector<PartnerLinkBinding>();
		this.bpelOperations = new BpelOperations(this);
		this.bpelProcessStrukture = new BpelProcessStrukture(this);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param src
	 * @param bpelXmlFile
	 */
	public BpelProcess(String id, String src, File bpelXmlFile) {
		this();
		this.id = id;
		this.src = src;
		this.bpelXmlFile = bpelXmlFile;
	}

	public final void addPartnerLinkBinding(PartnerLinkBinding partnerLink) {
		partnerLink.setParent(this);
		partnerLinkBindings.add(partnerLink);
	}

	public final List<PartnerLinkBinding> getPartnerLinkBindings() {
		return partnerLinkBindings;
	}

	public final String getId() {
		return id;
	}

	public final String getSrc() {
		return src;
	}

	public final File getBpelXmlFile() {
		return bpelXmlFile;
	}

	@Override
	public Enumeration<?> children() {
		return partnerLinkBindings.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return partnerLinkBindings.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		BpelProcess bpelProcess = partnerLinkBindings.get(childIndex).getBpelProcess();
		if (bpelProcess == null) {
			return new DefaultMutableTreeNode("error");
		}
		return bpelProcess;
	}

	@Override
	public int getChildCount() {
		return partnerLinkBindings.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return partnerLinkBindings.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return partnerLinkBindings.isEmpty();
	}

	public final String toString() {
		return id == null ? src : id;
	}

	public final PartnerLinkBinding findPartnerLinkBinding(String partnerLinkName) {
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			if (partnerLinkBinding.getName().equals(partnerLinkName)) {
				return partnerLinkBinding;
			}
		}
		return null;
	}

	public final BpelOperations getBpelOperations() {
		return bpelOperations;
	}

	public final BpelProcessStrukture getBpelProcessStrukture() {
		return bpelProcessStrukture;
	}

}
