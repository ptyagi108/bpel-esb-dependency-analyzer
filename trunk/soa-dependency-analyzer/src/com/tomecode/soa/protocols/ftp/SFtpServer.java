package com.tomecode.soa.protocols.ftp;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * FTP server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class SFtpServer extends FtpServer implements ImageFace {

	/**
	 * Constructor
	 * 
	 * @param server
	 * @param port
	 */
	public SFtpServer(String server, int port) {
		super(server, port);

	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {

		}
		return ImageFactory.SFTP_SERVER;
	}

}
