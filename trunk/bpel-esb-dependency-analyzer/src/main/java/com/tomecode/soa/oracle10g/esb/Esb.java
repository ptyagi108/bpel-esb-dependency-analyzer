package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.util.Vector;

/**
 * Esb service
 * 
 * @author Frastia Tomas
 * 
 */
public final class Esb {

	private File file;
	private String name;
	private String qName;

	private String wsdlURL;

	private String concreteWSDLURL;

	private String soapEndpointURI;

	private Vector<EsbOperation> esbOperations;

	public Esb() {
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

}
