package com.tomecode.soa.ora.suite10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.esb.BasicEsbNode.EsbNodeType;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Contains esbsvc,esbgrp, esb files in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora10gEsbProject implements Project {

	private static final long serialVersionUID = 1517251079778988223L;

	private String name;

	private File file;
	/**
	 * if true then project is in *.jws
	 */
	private boolean isInJws;

	private final List<BasicEsbNode> basicEsbNodes;
	/**
	 * list of esbsvc
	 */
	private final List<Project> projectDependecies;

	/**
	 * parent workspace
	 */
	private Ora10gWorkspace workspace;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 *            project name
	 * @param projectFolder
	 *            project folder
	 */
	public Ora10gEsbProject(String name, File projectFolder) {
		this.name = name;
		this.file = projectFolder;
		// super(name, projectFolder, ProjectType.ORACLE10G_ESB);
		basicEsbNodes = new ArrayList<BasicEsbNode>();
		projectDependecies = new Vector<Project>();
	}

	public final List<BasicEsbNode> getBasicEsbNodes() {
		return basicEsbNodes;
	}

	public final void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		basicEsbNodes.add(basicEsbNode);
	}

	public final String toString() {
		return (file != null) ? file.getName() : name;
	}

	public final List<Project> getProjectDependecies() {
		return projectDependecies;
	}

	/**
	 * find {@link Ora10gEsbProject} by qName and service URL
	 * 
	 * @param qName
	 * @param serviceURL
	 * @return if return null then not found
	 */
	public final Ora10gEsbProject findEsbProjectByQname(String qName, URL serviceURL) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getQname().equals(qName)) {
				return this;
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				if (esbSys.getQname().equals(qName)) {
					return this;
				} else {
					if (esbSys.findEsbProjectByQname(qName, serviceURL) != null) {
						return this;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode;
				if (esbGrp.getQname().equals(qName)) {
					return this;
				} else {
					if (esbGrp.findEsbProjectByQname(qName, serviceURL) != null) {
						return this;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				if (((EsbSys) basicEsbNode).findEsbProjectByQname(qName, serviceURL) != null) {
					return this;
				}
			}
		}

		return null;
	}

	public final EsbSys findEsbSysByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				if (esbSys.getQname().equals(qname)) {
					return esbSys;
				}
			}
		}

		return null;
	}

	public final EsbSvc findEsbSvcByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				EsbSvc esbSvc = (EsbSvc) basicEsbNode.get();
				if (esbSvc.getQname().equals(qname)) {
					return esbSvc;
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode;
				EsbSvc esbSvc = esbSys.findEsbSvcByQname(qname);
				if (esbSvc != null) {
					return esbSvc;
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode;
				EsbSvc esbSvc = esbGrp.findEsbSvcByQname(qname);
				if (esbSvc != null) {
					return esbSvc;
				}
			}
		}
		return null;
	}

	public final EsbGrp findEsbGrpByQname(String qname) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qname)) {
					return (EsbGrp) basicEsbNode.get();
				} else {
					EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
					esbGrp = esbGrp.findEsbGrpByQname(qname);
					if (esbGrp != null) {
						return esbGrp;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				EsbGrp esbGrp = esbSys.findEsbGrpByQname(qname);
				if (esbGrp != null) {
					return esbGrp;
				}
			}
		}

		return null;
	}

	public final void addDependency(Project project) {
		if (!projectDependecies.contains(project)) {
			projectDependecies.add(project);
		}
	}

	public final List<EsbSvc> getAllEsbSvc() {
		List<EsbSvc> esbSvcs = new ArrayList<EsbSvc>();
		findAllEsbSvc(esbSvcs);
		return esbSvcs;
	}

	private final void findAllEsbSvc(List<EsbSvc> esbSvcs) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
				esbGrp.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				esbSys.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				esbSvcs.add((EsbSvc) basicEsbNode.get());
			}
		}

	}

	// public final ImageIcon getIcon() {
	// return IconFactory.ESB;
	// }

	public final boolean isInJws() {
		return isInJws;
	}

	public final void setInJws(boolean isInJws) {
		this.isInJws = isInJws;
	}

	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final ProjectType getType() {
		return ProjectType.ORACLE10G_ESB;
	}

	public final void setWorkspace(Ora10gWorkspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	@Override
	public Image getImage() {
		return ImageFactory.ORACLE_10G_ESB;
	}

	@Override
	public final String getToolTip() {
		return "Oracle SOA Suite 10g - ESB Project: " + getName() + "\nFile: " + (file != null ? file.getPath() : "");
	}
}
