package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.protocols.ftp.FtpServer;

/**
 * Endpoint protocol - FTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointFTP extends EndpointConfig {

	private final List<FtpServer> ftpServers;

	public EndpointFTP() {
		super(ProviderProtocol.FTP);
		this.ftpServers = new ArrayList<FtpServer>();
	}

	public final List<FtpServer> getFtpServers() {
		return ftpServers;
	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseFtpServerUris(uris, ftpServers);
	}

	public final Image getImage() {
		return ImageFactory.SFTP_SERVER;
	}
}
