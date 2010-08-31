package com.tomecode.soa.wsdl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * WSDL for BPEL project or ESB service
 * 
 * @author Tomas Frastia
 * 
 */
public final class Wsdl implements Serializable {

	private static final long serialVersionUID = 2802713997788570698L;

	private File file;

	private String name;

	private List<PortType> portTypes;

	private final List<PartnerLinkType> partnerLinkTypes;

	/**
	 * Constructor
	 * 
	 * @param file
	 * @param name
	 */
	public Wsdl(File file, String name) {
		this.file = file;
		this.name = name;
		this.partnerLinkTypes = new ArrayList<PartnerLinkType>();
		this.portTypes = new ArrayList<PortType>();
	}

	public final String getName() {
		return name;
	}

	public final File getFile() {
		return file;
	}

	/**
	 * @return the portTypes
	 */
	public final List<PortType> getPortTypes() {
		return portTypes;
	}

	public final void addPortType(PortType portType) {
		this.portTypes.add(portType);
	}

	public final boolean existWsldOperation(String wsdlOperation) {
		for (PortType portType : portTypes) {
			return portType.existWsldOperation(wsdlOperation);
		}

		return false;
	}

	/**
	 * @return the partnerLinkTypes
	 */
	public final List<PartnerLinkType> getPartnerLinkTypes() {
		return partnerLinkTypes;
	}

	public final void addPartnerLinkType(PartnerLinkType type) {
		this.partnerLinkTypes.add(type);
	}

	/**
	 * find {@link PortType} by name
	 * 
	 * @param portTypeName
	 * @return
	 */
	public final PortType findPortType(String portTypeName) {
		if (portTypeName != null) {
			int index = portTypeName.indexOf(":");
			if (index == -1) {
				return findPortTypeByName(portTypeName);
			} else {
				return findPortType(portTypeName.substring(index + 1));
			}
		}
		return null;
	}

	/**
	 * find {@link PortType} by name in list of {@link PortType}
	 * 
	 * @param name
	 * @return null if not found
	 */
	private final PortType findPortTypeByName(String name) {
		for (PortType portType : portTypes) {
			if (name.equals(portType.getName())) {
				return portType;
			}
		}
		return null;
	}

	public final boolean containsPartnerLinkType(String partnerLinkTypeName) {
		for (PartnerLinkType partnerLinkType : partnerLinkTypes) {
			int index = partnerLinkTypeName.indexOf(":");
			if (index != -1) {
				if (partnerLinkTypeName.substring(index + 1).equals(partnerLinkType.getName())) {
					return true;
				}
			}

			if (partnerLinkTypeName.equals(partnerLinkType.getName())) {
				return true;
			}
		}
		return false;
	}
}
