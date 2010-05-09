package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.esb.BasicEsbNode.EsbNodeType;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * Contains esbsvc,esbgrp, esb files in project
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbProject extends Project {

	/**
	 * project name
	 */
	private String name;

	private File projectFolder;

	private final Vector<BasicEsbNode> basicEsbNodes;
	/**
	 * list of esbsvc
	 */
	private final Vector<Project> esbProjectsDependecies;

	/**
	 * Constructor
	 */
	public EsbProject() {
		super(ProjectType.ORACLE10G_ESB);
		basicEsbNodes = new Vector<BasicEsbNode>();
		esbProjectsDependecies = new Vector<Project>();
	}

	/**
	 * Constructor
	 * 
	 * @param projectFolder
	 * @param name
	 */
	public EsbProject(File projectFolder, String name) {
		this();
		this.projectFolder = projectFolder;
		this.name = name;
	}

	public final Vector<BasicEsbNode> getBasicEsbNodes() {
		return basicEsbNodes;
	}

	public final void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		basicEsbNodes.add(basicEsbNode);
	}

	public final String getName() {
		return name;
	}

	public final File getProjectFolder() {
		return projectFolder;
	}

	@Override
	public Enumeration<?> children() {
		return esbProjectsDependecies.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !esbProjectsDependecies.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return esbProjectsDependecies.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return esbProjectsDependecies.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return esbProjectsDependecies.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return esbProjectsDependecies.isEmpty();// esbs.isEmpty();
	}

	public final String toString() {
		return (projectFolder != null) ? projectFolder.getName() : name;
	}

	/**
	 * find {@link EsbProject} by qName and service url
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

	public final void addDependency(Project esbProject) {
		if (!esbProjectsDependecies.contains(esbProject)) {
			esbProjectsDependecies.add(esbProject);
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
}
