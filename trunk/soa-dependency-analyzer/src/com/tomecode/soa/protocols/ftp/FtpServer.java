package com.tomecode.soa.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * FTP server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class FtpServer implements ImageFace, Node<FtpServer> {

	/**
	 * ftp server host
	 */
	protected String server;
	/**
	 * ftp server port
	 */
	protected int port;
	/**
	 * list of {@link FtpFolder}
	 */
	protected final List<FtpFolder> ftpFolders = new ArrayList<FtpFolder>();

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * Constructor
	 * 
	 * @param server
	 * @param port
	 */
	public FtpServer(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public final String getServer() {
		return server;
	}

	public final int getPort() {
		return port;
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
			if (folder.toString().equals(url)) {
				return true;
			}
		}
		return false;
	}

	public final void setParentService(Service parentService) {
		this.parentService = parentService;
	}

	@Override
	public String getToolTip() {
		return "Type: FTP Server\nName: " + server + (port != -1 ? ("\nPort: " + String.valueOf(port)) : "");
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<FtpFolder> getChilds() {
		return ftpFolders;
	}

	@Override
	public FtpServer getObj() {
		return this;
	}
}
