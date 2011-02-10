package com.tomecode.soa.ora.osb10g.services.config;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.file.File;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - FILE
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointFile extends EndpointConfig<File> {

	public EndpointFile() {
		super(ProviderProtocol.FILE);
	}

	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FILE_SERVER_SMALL;
		}
		return ImageFactory.FILE_SERVER;
	}

	public final String toString() {
		if (uris.isEmpty()) {
			return "";
		}
		return makeuris();
	}
}
