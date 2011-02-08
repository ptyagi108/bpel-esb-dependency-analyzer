package com.tomecode.soa.ora.suite10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * service system for Oracle 10g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "System", name = "Oracle SOA Suite 10g", parentMethod = "getProject")
public final class EsbSys implements BasicEsbNode {

	private static final long serialVersionUID = -5469410851019923440L;
	private Ora10gEsbProject ownerEsbProject;

	@PropertyViewData(title = "Name")
	private String name;

	@PropertyViewData(title = "Path")
	private File file;

	private String qName;

	private final List<BasicEsbNode> childs;

	/**
	 * basic constructor
	 */
	public EsbSys() {
		this.childs = new ArrayList<BasicEsbNode>();
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
		this.file = esbSysFile;
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

	public final Ora10gEsbProject getProject() {
		return ownerEsbProject;
	}

	public final File getEsbSysFile() {
		return file;
	}

	public final String getName() {
		return name;
	}

	public final String getQname() {
		return qName;
	}

	public final void setOwnerEsbProject(Ora10gEsbProject esbProject) {
		this.ownerEsbProject = esbProject;
	}

	public Object get() {
		return this;
	}

	public EsbNodeType getType() {
		return EsbNodeType.ESBSYS;
	}

	public final String toString() {
		return name;
	}

	/**
	 * find {@link EsbGrp} by qName
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
	 * find {@link Ora10gEsbProject} by qName and serviceURL
	 * 
	 * @param qName
	 * @param sericeURL
	 * @return
	 */
	public final Ora10gEsbProject findEsbProjectByQname(String qName, URL sericeURL) {
		for (BasicEsbNode basicEsbNode : childs) {
			if (basicEsbNode.getType() == EsbNodeType.ESBSYS) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbSys) basicEsbNode.get()).getProject();
				} else {
					Ora10gEsbProject esbProject = ((EsbSys) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBGRP) {
				if (basicEsbNode.getQname().equals(qName)) {
					return ((EsbGrp) basicEsbNode.get()).getProject();
				} else {
					Ora10gEsbProject esbProject = ((EsbGrp) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
					if (esbProject != null) {
						return esbProject;
					}
				}
			} else if (basicEsbNode.getType() == EsbNodeType.ESBSVC) {
				Ora10gEsbProject esbProject = ((EsbSvc) basicEsbNode.get()).findEsbProjectByQname(qName, sericeURL);
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
	 * find all {@link EsbSvc} in {@link Ora10gEsbProject}
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
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_SYSTEM;
	}

	@Override
	public final String getToolTip() {
		return "Type: System\nName: " + getName() + "\nFile: " + (file != null ? file.getPath() : "");
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
