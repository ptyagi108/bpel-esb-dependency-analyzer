package com.tomecode.soa.ora.suite10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 10g - service group
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbGrp implements BasicEsbNode {

	private static final long serialVersionUID = -5057011739449470393L;

	private final List<BasicEsbNode> childs;

	private Ora10gEsbProject ownerEsbProject;
	/**
	 * service group file
	 */
	private File file;
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
		this.file = esbGrpFile;
		this.name = name;
		this.qname = qname;
	}

	public final File getEsbGrpFile() {
		return file;
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

	public final Ora10gEsbProject getOwnerEsbProject() {
		return ownerEsbProject;
	}

	public final void setOwnerEsbProject(Ora10gEsbProject ownerEsbProject) {
		this.ownerEsbProject = ownerEsbProject;
	}

	public final Ora10gEsbProject findEsbProjectByQname(String qName, URL serviceURL) {
		for (BasicEsbNode basicEsbNode : childs) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSys) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					Ora10gEsbProject esbProject = ((EsbSys) basicEsbNode.get()).findEsbProjectByQname(qName, serviceURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbGrp) basicEsbNode.get()).getOwnerEsbProject();
				} else {
					Ora10gEsbProject esbProject = ((EsbGrp) basicEsbNode.get()).findEsbProjectByQname(qName, serviceURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSvc) basicEsbNode.get()).getProject();
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

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SERVICE_GROUPE;
	}

	@Override
	public final String getToolTip() {
		return "Type: Group\nName: " + name + "\nFile: " + (file != null ? file.getPath() : "");
	}

	public final EsbSvc findEsbSvcByQname(String qname) {
		for (BasicEsbNode basicEsbNode : childs) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				EsbSvc esbSvc = (EsbSvc) basicEsbNode.get();
				if (esbSvc.getQname().equals(qname)) {
					return esbSvc;
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				EsbSys esbSys = (EsbSys) basicEsbNode;
				EsbSvc esbSvc = esbSys.findEsbSvcByQname(qname);
				if (esbSvc != null) {
					return null;
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
}
