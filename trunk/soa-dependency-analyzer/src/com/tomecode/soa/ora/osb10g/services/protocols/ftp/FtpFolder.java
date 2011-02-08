package com.tomecode.soa.ora.osb10g.services.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * FTP folder
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "FTP Folder", parentMethod = "getParent")
public final class FtpFolder implements ImageFace, Node<FtpFolder> {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * path
	 */
	@PropertyViewData(title = "Name:")
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

	public final String getPath() {
		return name;
	}

	public final FtpServer getFtpServer() {
		return ftpServer;
	}

	public final String toString() {
		return name;
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

	@Override
	public final FtpServer getParent() {
		return ftpServer;
	}

	@Override
	public final List<Node<?>> getChilds() {
		return nodes;
	}

	@Override
	public final FtpFolder getObj() {
		return this;
	}

}
