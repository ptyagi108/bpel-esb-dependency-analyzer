package com.tomecode.soa.oracle10g.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.process.Service;
import com.tomecode.soa.process.ServiceType;

/**
 * This object contains all {@link Service}
 * 
 * @author Tomas Frastia
 * 
 */
public final class Workspace implements TreeNode {

	private File file;

	private final Vector<Service> services;

	/**
	 * Constructor
	 */
	public Workspace() {
		this.services = new Vector<Service>();
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 */
	public Workspace(File file) {
		this();
		this.file = file;
	}

	public final Vector<Service> getServices() {
		return services;
	}

	public final void addService(Service service) {
		this.services.add(service);
	}

	public final File getFile() {
		return file;
	}

	@Override
	public Enumeration<?> children() {
		return services.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !services.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return services.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return services.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return services.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return services.isEmpty();
	}

	/**
	 * find usage for bpel process (only bpel )
	 * 
	 * @param usage
	 * @return
	 */
	public final List<Bpel> findBpelUsages(Bpel usage) {
		List<Bpel> list = new ArrayList<Bpel>();

		for (Service service : services) {
			if (service.getType() == ServiceType.ORACLE10G_BPEL) {
				Bpel bpel = (Bpel) service;
				if (!bpel.equals(usage)) {
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getBpelProcess() != null) {
							if (partnerLinkBinding.getBpelProcess().equals(usage)) {
								list.add(partnerLinkBinding.getParent());
							}
						}
					}
				}
			}
		}

		return list;
		// return new ArrayList<Bpel>();
	}
}
