package com.tomecode.soa.project;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Unknown project or service in SOA Suite 10g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "Unknown Project...", name = "Oracle SOA Suite 10g", parentMethod = "getWorkpsace")
public final class UnknownProject implements Project {

	private static final long serialVersionUID = 6364329347778611989L;

	private PartnerLinkBinding partnerLinkBinding;

	@PropertyViewData(title = "Name")
	private String name;

	private Workspace workspace;

	private UnknownProjectType type;

	/**
	 * Constructor
	 */
	private UnknownProject(String name) {
		this.type = UnknownProjectType.UNKNOWN;
		this.name = name;
	}

	/**
	 * 
	 * @param partnerLinkBinding
	 */
	public UnknownProject(PartnerLinkBinding partnerLinkBinding) {
		this(partnerLinkBinding.getName());
		this.type = UnknownProjectType.UNKNOWN;
		this.partnerLinkBinding = partnerLinkBinding;
	}

	/**
	 * Constructor
	 * 
	 * @param partnerLinkBinding
	 * @param type
	 *            {@link UnknownProjectType}
	 */
	public UnknownProject(PartnerLinkBinding partnerLinkBinding, UnknownProjectType type) {
		this(partnerLinkBinding.getName());
		this.type = type;
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
		return partnerLinkBinding.getName();
	}

	public final PartnerLinkBinding getPartnerLinkBinding() {
		return partnerLinkBinding;
	}

	@Override
	public final File getFile() {
		return null;
	}

	@Override
	public final ProjectType getType() {
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
	public final Workspace getWorkpsace() {
		return workspace;
	}

	@Override
	public final Image getImage(boolean small) {
		if (type == UnknownProjectType.FILE) {
			return ImageFactory.ORACLE_10G_ESB_FILE_ADAPTER;
		} else if (type == UnknownProjectType.DB) {
			return ImageFactory.ORACLE_10G_ESB_DB_ADAPTER;
		} else if (type == UnknownProjectType.JMS) {
			return ImageFactory.ORACLE_10G_ESB_JMS_ADAPTER;
		} else if (type == UnknownProjectType.FTP) {
			return ImageFactory.ORACLE_10G_ESB_FTP_ADAPTER;
		} else if (type == UnknownProjectType.AQ) {
			return ImageFactory.ORACLE_10G_ESB_AQ_ADAPTER;
		} else if (type == UnknownProjectType.MQ) {
			return ImageFactory.ORACLE_10G_ESB_MQ_ADAPTER;
		}
		return ImageFactory.UNKNOWN;
	}

	@Override
	public final String getToolTip() {
		String tooltip = "Unknown Project: " + name;
		if (type != UnknownProjectType.UNKNOWN) {
			tooltip += "\nType: " + getTypeText();
		}
		tooltip += "\nWSDL: " + partnerLinkBinding.getWsdlLocation();
		return tooltip;
	}

	/**
	 * 
	 * @return according type of {@link UnknownProject} return text
	 */
	public final String getTypeText() {
		if (type == UnknownProjectType.FILE) {
			return "File Adapter Service";
		} else if (type == UnknownProjectType.JMS) {
			return "JMS Adapter Service";
		} else if (type == UnknownProjectType.DB) {
			return "Database Adapter Service";
		} else if (type == UnknownProjectType.FTP) {
			return "FTP Adapter Service";
		} else if (type == UnknownProjectType.AQ) {
			return "AQ Adapter Service";
		} else if (type == UnknownProjectType.MQ) {
			return "MQ Adapter Service";
		}
		return "Unknown Service";
	}

	@Override
	public final boolean isFolder() {
		return false;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Unknown project type, for example: ftp, db, etc.
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum UnknownProjectType {
		JMS, DB, UNKNOWN, FTP, FILE, AQ, MQ;
	}

}
