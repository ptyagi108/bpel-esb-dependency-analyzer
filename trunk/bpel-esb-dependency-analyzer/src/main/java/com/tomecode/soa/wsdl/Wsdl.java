package com.tomecode.soa.wsdl;

import java.io.File;
import java.io.Serializable;

/**
 * 
 * 
 * wsdl for bpel project or esb service
 * 
 * @author Tomas Frastia
 * 
 */
public final class Wsdl implements Serializable {

	private static final long serialVersionUID = 2802713997788570698L;

	private File file;

	private String name;

	private PortType portType;

	public Wsdl(File file, String name) {
		this.file = file;
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final File getFile() {
		return file;
	}

	public final PortType getPortType() {
		return portType;
	}

	public final void setPortType(PortType portType) {
		this.portType = portType;
	}

	public final boolean existWsldOperation(String wsdlOperation) {
		if (portType != null) {
			return portType.existWsldOperation(wsdlOperation);
		}

		return false;
	}

}
