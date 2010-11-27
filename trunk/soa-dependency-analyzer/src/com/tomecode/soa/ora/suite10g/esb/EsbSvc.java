package com.tomecode.soa.ora.suite10g.esb;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Oracle 10g ESB SVC service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbSvc implements BasicEsbNode {

	private static final long serialVersionUID = -1114897174017288770L;

	private final List<EsbOperation> childs;
	/**
	 * ESB SVC file
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
	private Ora10gEsbProject ownerEsbProject;

	/**
	 * Constructor
	 */
	public EsbSvc() {
		childs = new ArrayList<EsbOperation>();
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 *            SVC file
	 * @param name
	 * @param qName
	 */
	public EsbSvc(File file, String name, String qName) {
		this();
		this.file = file;
		this.name = name;
		this.qName = qName;
	}

	public final Ora10gEsbProject getOwnerEsbProject() {
		return ownerEsbProject;
	}

	public final void setOwnerEsbProject(Ora10gEsbProject esbProject) {
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

	/**
	 * add new {@link EsbOperation} and set parent {@link EsbSvc}
	 * 
	 * @param esbOperation
	 */
	public final void addEsbOperation(EsbOperation esbOperation) {
		esbOperation.setEsbSvc(this);
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
	 * find {@link Ora10gEsbProject} by qname
	 * 
	 * @param qName
	 * @param sericeURL
	 * @return
	 */
	public final Ora10gEsbProject findEsbProjectByQname(String qName, URL sericeURL) {
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
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SERVICE;
	}

	// public ImageIcon getIcon() {
	// return IconFactory.SERVICE;
	// }
	//
	// public final void findUsage(FindUsageProjectResult usage) {
	// for (EsbOperation esbOperation : childs) {
	// esbOperation.findUsage(usage);
	// }
	// }

}
