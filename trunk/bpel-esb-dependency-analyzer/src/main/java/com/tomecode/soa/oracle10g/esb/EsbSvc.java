package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;

/**
 * Esb service
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbSvc extends BasicNode<EsbOperation> implements BasicEsbNode, IconNode {

	/**
	 * esb svc file
	 */
	private File file;
	private String name;
	private String qName;

	private String wsdlURL;

	private String concreteWSDLURL;

	private String soapEndpointURI;

	/**
	 * owner project
	 */
	private EsbProject ownerEsbProject;

	/**
	 * Constructor
	 */
	public EsbSvc() {
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 *            svcc file
	 * @param name
	 * @param qName
	 */
	public EsbSvc(File file, String name, String qName) {
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
		childs.add(esbOperation);
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

	/**
	 * find {@link EsbProject} by qname
	 * 
	 * @param qName
	 * @param sericeURL
	 * @return
	 */
	public final EsbProject findEsbProjectByQname(String qName, URL sericeURL) {
		if (this.qName.equalsIgnoreCase(qName)) {
			if (this.concreteWSDLURL != null) {
				if (this.concreteWSDLURL.equalsIgnoreCase(sericeURL.getFile())) {
					return ownerEsbProject;
				}
			}
		}

		return null;
	}

	public final List<EsbOperation> getEsbOperations() {
		return childs;
	}

	@Override
	public ImageIcon getIcon() {
		return IconFactory.SERVICE;
	}

}
