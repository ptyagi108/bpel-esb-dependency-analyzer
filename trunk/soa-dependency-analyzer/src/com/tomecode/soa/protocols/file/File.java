package com.tomecode.soa.protocols.file;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * FILE protocol
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "File Adapter")
public final class File implements ImageFace, Node<File> {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();
	/**
	 * parent service
	 */
	private Object parentService;

	@Override
	public final Object getParent() {
		return parentService;
	}

	public final void setParentService(Object service) {
		this.parentService = service;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final File getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.FILE_SERVER_SMALL;
		}
		return ImageFactory.FILE_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "File";
	}
}
