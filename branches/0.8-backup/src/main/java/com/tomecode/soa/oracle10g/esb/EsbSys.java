package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;

/**
 * service system for Oracle 10g
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbSys extends BasicNode<BasicEsbNode> implements BasicEsbNode, IconNode {

	private static final long serialVersionUID = -5469410851019923440L;
	private EsbProject ownerEsbProject;
	private File esbSysFile;
	private String name;
	private String qName;

	/**
	 * basic contructor
	 */
	public EsbSys() {
	}

	/**
	 * Constructor
	 * 
	 * @param esbSysFile
	 * @param name
	 * @param qname
	 */
	public EsbSys(File esbSysFile, String name, String qname) {
		this();
		this.esbSysFile = esbSysFile;
		this.name = name;
		this.qName = qname;
	}

	/**
	 * Constructor
	 * 
	 * @param qname
	 */
	public EsbSys(String qname) {
		this();
		this.name = qname;
		this.qName = qname;
	}

	public void addBasicEsbNode(BasicEsbNode basicEsbNode) {
		childs.add(basicEsbNode);
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

	/**
	 * find {@link EsbGrp} by qname
	 * 
	 * @param qname
	 * @return
	 */
	public final EsbGrp findEsbGrpByQname(String qname) {
		for (BasicEsbNode basicEsbNode : childs) {
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
		for (BasicEsbNode basicEsbNode : childs) {
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

	public final List<BasicEsbNode> getBasicEsbNodes() {
		return childs;
	}

	/**
	 * find all {@link EsbSvc} in {@link EsbProject}
	 * 
	 * @param esbSvcs
	 */
	protected void findAllEsbSvc(List<EsbSvc> esbSvcs) {
		for (BasicEsbNode basicEsbNode : childs) {
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

	@Override
	public final ImageIcon getIcon() {
		return IconFactory.SYSTEM;
	}

}
