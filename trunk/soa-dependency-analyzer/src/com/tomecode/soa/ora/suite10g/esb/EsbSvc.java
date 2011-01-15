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

	private ServiceSubType serviceSubType;

	private ServiceType serviceType;

	private String typeDescription;
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

	private String targetServiceQName;
	private EsbSvc targetService;

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

	/**
	 * parent project
	 * 
	 * @return
	 */
	public final Ora10gEsbProject getProject() {
		return ownerEsbProject;
	}

	/**
	 * set parent project
	 * 
	 * @param esbProject
	 */
	public final void setProject(Ora10gEsbProject esbProject) {
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

	public final ServiceSubType getServiceSubType() {
		return serviceSubType;
	}

	public final void setServiceSubType(ServiceSubType serviceSubType) {
		this.serviceSubType = serviceSubType;
	}

	public final ServiceType getServiceType() {
		return serviceType;
	}

	public final void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public final String getTypeDescription() {
		return typeDescription;
	}

	public final void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
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
	public final EsbNodeType getType() {
		return EsbNodeType.ESBSVC;
	}

	/**
	 * find {@link Ora10gEsbProject} by qName
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
	public final Image getImage(boolean small) {
		if (ServiceSubType.JMS == serviceSubType) {
			return ImageFactory.ORACLE_10G_ESB_JMS_ADAPTER;
		} else if (ServiceSubType.DB == serviceSubType) {
			return ImageFactory.ORACLE_10G_ESB_DB_ADAPTER;
		} else if (ServiceType.ExternalService == serviceType) {
			return ImageFactory.ORACLE_10G_ESB_SOAP_SERVICE;
		} else if (ServiceSubType.File == serviceSubType) {
			return ImageFactory.ORACLE_10G_ESB_FILE_ADAPTER;
		} else if (ServiceType.RoutingService == serviceType) {
			return ImageFactory.ORACLE_10G_ESB_ROUTING_SERVICE;
		} else if (ServiceSubType.FTP == serviceSubType) {
			return ImageFactory.ORACLE_10G_ESB_FTP_ADAPTER;
		}

		return ImageFactory.ORACLE_10G_SERVICE;
	}

	/**
	 * 
	 * type of sub ESB service
	 * 
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Oracle 10g ESB SVC service
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum ServiceSubType {
		DB, RoutingService, JMS, File, FTP, DEFAULT;

		public static final ServiceSubType parse(String name) {
			for (ServiceSubType type : values()) {
				if (type.toString().equals(name)) {
					return type;
				}
			}
			return DEFAULT;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * type of ESB service
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	public enum ServiceType {
		InboundAdapterService, RoutingService, OutboundAdapterService, ExternalService, DEFAULT;

		public static final ServiceType parse(String name) {
			for (ServiceType type : values()) {
				if (type.toString().equals(name)) {
					return type;
				}
			}
			return DEFAULT;
		}

	}

	@Override
	public final String getToolTip() {
		return "Type: " + (typeDescription != null ? typeDescription + ": " : "") + "\nName: " + (name != null ? name : "") + "\nFile: " + (file != null ? file.getPath() : "");
	}

	public final String getTargetServiceQName() {
		return targetServiceQName;
	}

	public final void setTargetServiceQName(String targetServiceQName) {
		this.targetServiceQName = targetServiceQName;
	}

	public final EsbSvc getTargetService() {
		return targetService;
	}

	public final void setTargetService(EsbSvc targetService) {
		this.targetService = targetService;
	}

	public final EsbOperation getOperation(String wsdlOperation) {
		for (EsbOperation esbOperation : childs) {
			if (esbOperation.getWsdlOperation().equals(wsdlOperation)) {
				return esbOperation;
			}
		}
		return null;
	}

}
