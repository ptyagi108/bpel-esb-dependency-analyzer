package com.tomecode.soa.ora.osb10g.services.config;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Endpoint protocol - FILE
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointFile extends EndpointConfig {

	public EndpointFile() {
		super(ProviderProtocol.FILE);
	}

	public final Image getImage() {
		return ImageFactory.FILE_SERVER;
	}
}
