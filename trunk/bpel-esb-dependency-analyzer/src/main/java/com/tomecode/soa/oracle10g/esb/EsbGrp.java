package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * Oracle 10g - service group
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbGrp implements BasicEsbNode {

	private EsbProject ownerEsbProject;
	/**
	 * service group file
	 */
	private File esbGrpFile;
	/**
	 * service group name
	 */
	private String name;
	/**
	 * service group qName
	 */
	private String qname;

	private final Vector<BasicEsbNode> basicEsbNodes;

	/**
	 * Constructor
	 */
	public EsbGrp() {
		basicEsbNodes = new Vector<BasicEsbNode>();
	}

	/**
	 * Constructor
	 * 
	 * @param esbGrpFile
	 * @param name
	 * @param qname
	 */
	public EsbGrp(File esbGrpFile, String name, String qname) {
		this();
		this.esbGrpFile = esbGrpFile;
		this.name = name;
		this.qname = qname;
	}

	public final File getEsbGrpFile() {
		return esbGrpFile;
	}

	public final String getName() {
		return name;
	}

	public final String getQname() {
		return qname;
	}

	public final void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		basicEsbNodes.add(basicEsbNode);
	}

	@Override
	public Enumeration<?> children() {
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
		return EsbNodeType.ESBGRP;
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
					esbGrp.findEsbGrpByQname(qname);
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				esbSys.findEsbGrpByQname(qname);
			}
		}

		return null;
	}

	public final EsbProject getOwnerEsbProject() {
		return ownerEsbProject;
	}

	public final void setOwnerEsbProject(EsbProject ownerEsbProject) {
		this.ownerEsbProject = ownerEsbProject;
	}

	public final EsbProject findEsbProjectByQname(String qName, URL serviceURL) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSys) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					EsbProject esbProject = ((EsbSys) basicEsbNode.get()).findEsbProjectByQname(qName, serviceURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbGrp) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					EsbProject esbProject = ((EsbGrp) basicEsbNode.get()).findEsbProjectByQname(qName, serviceURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSvc) basicEsbNode.get()).getOwnerEsbProject();
				}
			}
		}
		return null;
	}

	public final Vector<BasicEsbNode> getBasicEsbNodes() {
		return basicEsbNodes;
	}

}
