package com.tomecode.soa.ora.suite10g.esb.protocols;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * ESH adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public class EsbAdapter {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * parent service
	 */
	private Object parentService;

	private String locations;

	/**
	 * 
	 * @param locations
	 *            JNDI name
	 */
	public EsbAdapter(String locations) {
		this.locations = locations;
	}

	public final Object getParent() {
		return parentService;
	}

	public final List<?> getChilds() {
		return nodes;
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
