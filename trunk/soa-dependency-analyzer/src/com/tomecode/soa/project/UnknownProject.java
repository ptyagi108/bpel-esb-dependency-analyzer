package com.tomecode.soa.project;

import java.io.File;

import com.tomecode.soa.oracle10g.project.PartnerLinkBinding;
import com.tomecode.soa.workspace.Workspace;

/**
 * Unknown project or service
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownProject implements Project {

	private static final long serialVersionUID = 6364329347778611989L;

	private PartnerLinkBinding partnerLinkBinding;

	private String name;

	private Workspace workspace;

	/**
	 * Constructor
	 */
	public UnknownProject(String name) {

		this.name = name;
		// super(name, null, ProjectType.UNKNOWN);
	}

	public UnknownProject(PartnerLinkBinding partnerLinkBinding) {
		this(partnerLinkBinding.getName());
		// super(partnerLinkBinding.getName(), null, ProjectType.UNKNOWN);
		this.partnerLinkBinding = partnerLinkBinding;
	}

	// @Override
	// public Enumeration<?> children() {
	// return null;
	// }
	//
	// @Override
	// public boolean getAllowsChildren() {
	// return false;
	// }
	//
	// @Override
	// public TreeNode getChildAt(int childIndex) {
	// return null;
	// }
	//
	// @Override
	// public int getChildCount() {
	// return 0;
	// }
	//
	// @Override
	// public int getIndex(TreeNode node) {
	// return 0;
	// }
	//
	// @Override
	// public TreeNode getParent() {
	// return null;
	// }
	//
	// @Override
	// public boolean isLeaf() {
	// return true;
	// }

	// public ImageIcon getIcon() {
	// return IconFactory.UNKNOWN;
	// }

	public final String toString() {
		return partnerLinkBinding.getWsdlLocation();
	}

	public final PartnerLinkBinding getPartnerLinkBinding() {
		return partnerLinkBinding;
	}

	@Override
	public File getFile() {
		return null;
	}

	@Override
	public ProjectType getType() {
		return ProjectType.UNKNOWN;
	}

	public final void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public Workspace getWorkspace() {
		return workspace;
	}

}
