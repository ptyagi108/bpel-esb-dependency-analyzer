package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.process.Service;
import com.tomecode.soa.process.ServiceType;

/**
 * Esb service
 * 
 * @author Frastia Tomas
 * 
 */
public final class Esb extends Service {

	private File file;
	private String name;
	private String qName;

	private String wsdlURL;

	private String concreteWSDLURL;

	private String soapEndpointURI;

	private Vector<EsbOperation> esbOperations;

	public Esb() {
		super(ServiceType.ORACLE10G_ESB);
		esbOperations = new Vector<EsbOperation>();
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 * @param qName
	 */
	public Esb(File file, String name, String qName) {
		this();
		this.file = file;
		this.name = name;
		this.qName = qName;
	}

	public final File getFile() {
		return file;
	}

	public final String getName() {
		return name;
	}

	public final String getQName() {
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

	public final void addOperation(EsbOperation esbOperation) {
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
		if (name == null) {
			if (qName != null) {
				return qName;
			}
		}
		return file.getName();
	}
}
