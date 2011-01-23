package com.tomecode.soa.ora.osb10g.services.config;

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
public final class EndpointFTP extends EndpointConfig<FtpServer> {

	public EndpointFTP() {
		super(ProviderProtocol.FTP);
	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseFtpServerUris(uris, nodes);
	}

	public final Image getImage() {
		return ImageFactory.SFTP_SERVER;
	}
}
