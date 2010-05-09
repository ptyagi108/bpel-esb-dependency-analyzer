package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * service system for Oracle 10g
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbSys implements BasicEsbNode {

	private final Vector<BasicEsbNode> basicEsbNodes;
	private EsbProject ownerEsbProject;
	private File esbSysFile;
	private String name;
	private String qName;

	public EsbSys() {
		basicEsbNodes = new Vector<BasicEsbNode>();
	}

	public EsbSys(File esbSysFile, String name, String qname) {
		this();
		this.esbSysFile = esbSysFile;
		this.name = name;
		this.qName = qname;
	}

	public EsbSys(String qname) {
		this();
		this.name = qname;
		this.qName = qname;
	}

	public void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		basicEsbNodes.add(basicEsbNode);
	}

	public final EsbProject getOwnerEsbProject() {
		return ownerEsbProject;
	}

	public final File getEsbSysFile() {
		return esbSysFile;
	}

	public final String getName() {
		return name;
	}

	public final String getQname() {
		return qName;
	}

	public final void setOwnerEsbProject(EsbProject esbProject) {
		this.ownerEsbProject = esbProject;
	}

	@Override
	public final Enumeration<?> children() {
		return basicEsbNodes.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return basicEsbNodes.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return basicEsbNodes.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return basicEsbNodes.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return basicEsbNodes.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return basicEsbNodes.isEmpty();
	}

	@Override
	public Object get() {
		return this;
	}

	@Override
	public EsbNodeType getType() {
		return EsbNodeType.ESBSYS;
	}

	public final String toString() {
		return name;
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

	/**
	 * find {@link EsbProject} by qName and serviceURL
	 * 
	 * @param qName
	 * @param sericeURL
	 * @return
	 */
	public final EsbProject findEsbProjectByQname(String qName, URL sericeURL) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSys) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					EsbProject esbProject = ((EsbSys) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbGrp) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					EsbProject esbProject = ((EsbGrp) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				EsbProject esbProject = ((EsbSvc) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
				if (esbProject != null) {
					return esbProject;
				}
			}
		}
		return null;
	}

	public final Vector<BasicEsbNode> getBasicEsbNodes() {
		return basicEsbNodes;
	}

	protected void findAllEsbSvc(List<EsbSvc> esbSvcs) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				esbSys.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
				esbGrp.findAllEsbSvc(esbSvcs);
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				esbSvcs.add((EsbSvc) basicEsbNode.get());
			}
		}
	}

}
