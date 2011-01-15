package com.tomecode.soa.dependency.analyzer.icons;

import org.eclipse.swt.graphics.Image;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface ImageFace {

	/**
	 * get image
	 * 
	 * @param small
	 *            - true then return small image
	 * @return
	 */
	Image getImage(boolean small);

	/**
	 * get tool tip for object
	 * 
	 * @return
	 */
	String getToolTip();

}
