package com.tomecode.soa.protocols.ftp;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * FTP folder
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class FtpFolder implements ImageFace {

	/**
	 * path
	 */
	private String name;

	/**
	 * parent server
	 */
	private FtpServer ftpServer;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param ftpServer
	 */
	public FtpFolder(String name, FtpServer ftpServer) {
		this.name = name;
		this.ftpServer = ftpServer;
	}

	public final String getName() {
		return name;
	}

	public final FtpServer getFtpServer() {
		return ftpServer;
	}

	@Override
	public String getToolTip() {
		return "Type: Folder\nName: " + name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FTP_FOLDER_SMALL;
		}
		return ImageFactory.FTP_FOLDER;
	}

}
