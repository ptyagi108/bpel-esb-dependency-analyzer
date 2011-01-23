package com.tomecode.soa.protocols.db;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * DB protocol
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class Database implements ImageFace, Node<Database> {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * parent service
	 */
	private Object parentService;

	private String locations;

	public Database(String locations) {
		this.locations = locations;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final Database getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.DB_SERVER_SMALL;
		}
		return ImageFactory.DB_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "DB Server";
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return locations;
	}

	public final String toString() {
		return locations;
	}

	/**
	 * @param parentService
	 *            the parentService to set
	 */
	public final void setParentService(Object parentService) {
		this.parentService = parentService;
	}

}
