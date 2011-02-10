package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.protocols.ftp.SFtpServer;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * 
 * Endpoint protocol - SFTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointSFTP extends EndpointConfig<SFtpServer> {

	private final List<SFtpServer> ftpServers;

	public EndpointSFTP() {
		super(ProviderProtocol.SFTP);
		this.ftpServers = new ArrayList<SFtpServer>();

	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseSFtpServerUris(uris, ftpServers);
	}

	/**
	 * @return the ftpServers
	 */
	public final List<SFtpServer> getSFtpServers() {
		return ftpServers;
	}

	public final Image getImage() {
		return ImageFactory.FTP_SERVER;
	}
}
