package com.tomecode.soa.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

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
public class FtpServer implements ImageFace {

	/**
	 * ftp server host
	 */
	private String server;
	/**
	 * ftp server port
	 */
	private int port;
	/**
	 * list of {@link FtpFolder}
	 */
	private final List<FtpFolder> ftpFolders;

	/**
	 * Constructor
	 * 
	 * @param server
	 * @param port
	 */
	public FtpServer(String server, int port) {
		this.ftpFolders = new ArrayList<FtpFolder>();
		this.server = server;
		this.port = port;
	}

	public final String getServer() {
		return server;
	}

	public final int getPort() {
		return port;
	}

	public final List<FtpFolder> getFtpFolders() {
		return ftpFolders;
	}

	@Override
	public Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FTP_SERVER_SMALL;
		}
		return ImageFactory.FTP_SERVER;
	}

	@Override
	public final String toString() {
		if (port != -1) {
			return server + ":" + port;
		}
		return server;
	}

	public final void addUrl(String path) {
		if (!exists(path)) {
			ftpFolders.add(new FtpFolder(path, this));
		}
	}

	private final boolean exists(String url) {
		for (FtpFolder folder : ftpFolders) {
			if (folder.equals(url)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getToolTip() {
		return null;
	}

}
