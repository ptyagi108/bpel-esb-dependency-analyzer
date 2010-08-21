package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.tomecode.soa.oracle10g.esb.BasicEsbNode.EsbNodeType;
import com.tomecode.soa.oracle10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * Contains esbsvc,esbgrp, esb files in project
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbProject implements Project {

	private static final long serialVersionUID = 1517251079778988223L;

	private String name;

	private File projectFolder;
	/**
	 * if true then project is in *.jws
	 */
	private boolean isInJws;

	private final List<BasicEsbNode> basicEsbNodes;
	/**
	 * list of esbsvc
	 */
	private final List<Project> projectDependecies;

	private Ora10gWorkspace workspace;

	/**
	 * Constructor
	 * 
	 * @param projectFolder
	 * @param name
	 */
	public EsbProject(String name, File projectFolder) {
		this.name = name;
		this.projectFolder = projectFolder;
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
		return (projectFolder != null) ? projectFolder.getName() : name;
	}

	public final List<Project> getProjectDependecies() {
		return projectDependecies;
	}

	/**
	 * find {@link EsbProject} by qName and service URL
	 * 
	 * @param qName
	 * @param serviceURL
	 * @return if return null then not found
	 */
	public final EsbProject findEsbProjectByQname(String qName, URL serviceURL) {
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
		return projectFolder;
	}

	@Override
	public final ProjectType getType() {
		return ProjectType.OPEN_ESB_BPEL;
	}

	public final void setWorkspace(Ora10gWorkspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final Workspace getWorkspace() {
		return workspace;
	}

}
