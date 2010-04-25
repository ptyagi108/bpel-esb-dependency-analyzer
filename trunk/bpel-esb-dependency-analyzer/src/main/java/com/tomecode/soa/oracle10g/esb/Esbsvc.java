package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * Esb service
 * 
 * @author Frastia Tomas
 * 
 */
public final class Esbsvc implements BasicEsbNode {

	private File file;
	private String name;
	private String qName;

	private String wsdlURL;

	private String concreteWSDLURL;

	private String soapEndpointURI;

	private Vector<EsbOperation> esbOperations;

	private EsbProject ownerEsbProject;

	/**
	 * Constructor
	 */
	public Esbsvc() {
		esbOperations = new Vector<EsbOperation>();
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 * @param qName
	 */
	public Esbsvc(File file, String name, String qName) {
		this();
		this.file = file;
		this.name = name;
		this.qName = qName;
	}

	public final EsbProject getOwnerEsbProject() {
		return ownerEsbProject;
	}

	public final void setOwnerEsbProject(EsbProject esbProject) {
		this.ownerEsbProject = esbProject;
	}

	public final File getFile() {
		return file;
	}

	public final String getName() {
		return name;
	}

	public final String getQname() {
		return qName;
	}

	public final String getWsdlURL() {
		return wsdlURL;
	}

	public final void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	public final String getConcreteWSDLURL() {
		return concreteWSDLURL;
	}

	public final void setConcreteWSDLURL(String concreteWSDLURL) {
		this.concreteWSDLURL = concreteWSDLURL;
	}

	public final String getSoapEndpointURI() {
		return soapEndpointURI;
	}

	public final void setSoapEndpointURI(String soapEndpointURI) {
		this.soapEndpointURI = soapEndpointURI;
	}

	public final void addEsbOperation(EsbOperation esbOperation) {
		esbOperations.add(esbOperation);
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return !esbOperations.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return esbOperations.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return esbOperations.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return esbOperations.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return esbOperations.isEmpty();
	}

	public String toString() {
		if (name == null || name.trim().length() == 0) {
			if (qName == null || qName.trim().length() == 0) {
				return file.getName();
			} else {
				return qName;
			}
		}
		return name;
	}

	@Override
	public Object get() {
		return this;
	}

	@Override
	public EsbNodeType getType() {
		return EsbNodeType.ESBSVC;
	}
}
