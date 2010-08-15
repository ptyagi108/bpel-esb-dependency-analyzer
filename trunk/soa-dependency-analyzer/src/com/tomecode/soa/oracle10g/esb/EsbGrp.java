package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Oracle 10g - service group
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbGrp implements BasicEsbNode {

	private static final long serialVersionUID = -5057011739449470393L;

	private final List<BasicEsbNode> childs;

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

	/**
	 * Constructor
	 */
	public EsbGrp() {
		this.childs = new ArrayList<BasicEsbNode>();
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
		childs.add(basicEsbNode);
	}

	public Object get() {
		return this;
	}

	public EsbNodeType getType() {
		return EsbNodeType.ESBGRP;
	}

	public final String toString() {
		return name;
	}

	public final EsbGrp findEsbGrpByQname(String qname) {
		for (BasicEsbNode basicEsbNode : childs) {
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
		for (BasicEsbNode basicEsbNode : childs) {
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

	public final List<BasicEsbNode> getBasicEsbNodes() {
		return childs;
	}

	protected final void findAllEsbSvc(List<EsbSvc> esbSvcs) {
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

//	public ImageIcon getIcon() {
//		return IconFactory.SERVICE_GROUPE;
//	}

}
