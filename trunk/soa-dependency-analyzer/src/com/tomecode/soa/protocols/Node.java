package com.tomecode.soa.protocols;

import java.util.List;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * node - for better analyze dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public interface Node<T> {

	/**
	 * parent object for this node
	 * 
	 * @return
	 */
	Object getParent();

	/**
	 * list of child's
	 * 
	 * @return
	 */
	List<?> getChilds();

	/**
	 * 
	 * @return
	 */
	T getObj();
}
