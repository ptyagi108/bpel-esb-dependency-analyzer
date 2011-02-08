package com.tomecode.soa.ora.osb10g.services.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * FTP server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "SFTP Server")
public final class SFtpServer implements ImageFace, Node<SFtpServer> {

	/**
	 * ftp server host
	 */
	@PropertyViewData(title = "Server:")
	protected String server;
	/**
	 * ftp server port
	 */
	@PropertyViewData(title = "Port:")
	protected int port;
	/**
	 * list of {@link FtpFolder}
	 */
	protected final List<SFtpFolder> ftpFolders = new ArrayList<SFtpFolder>();

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
	public SFtpServer(String server, int port) {
		this.server = server;
		this.port = port;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.SFTP_SERVER_SMALL;
		}
		return ImageFactory.SFTP_SERVER;
	}

	@Override
	public String getToolTip() {
		return "Type: FTP Server\nName: " + server + (port != -1 ? ("\nPort: " + String.valueOf(port)) : "");
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
			ftpFolders.add(new SFtpFolder(path, this));
		}
	}

	private final boolean exists(String url) {
		for (SFtpFolder folder : ftpFolders) {
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
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<SFtpFolder> getChilds() {
		return ftpFolders;
	}

	@Override
	public final SFtpServer getObj() {
		return this;
	}

	public final String getServer() {
		return server;
	}

	public final int getPort() {
		return port;
	}
}
