package com.tomecode.soa.wsdl;

/**
 * Role for {@link PartnerLinkType} in WSDL
 * 
 * @author Tomas Frastia
 * 
 */
public final class Role {

	private final PartnerLinkType partnerLinkType;

	private final String name;

	private final PortType portType;

	/**
	 * 
	 * @param name
	 * @param partnerLinkType
	 * @param portType
	 */
	public Role(String name, PartnerLinkType partnerLinkType, PortType portType) {
		this.partnerLinkType = partnerLinkType;
		this.name = name;
		this.portType = portType;
	}

	/**
	 * @return the partnerLinkType
	 */
	public final PartnerLinkType getPartnerLinkType() {
		return partnerLinkType;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the portType
	 */
	public final PortType getPortType() {
		return portType;
	}

}
