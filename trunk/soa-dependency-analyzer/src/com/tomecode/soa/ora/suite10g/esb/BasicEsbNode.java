package com.tomecode.soa.ora.suite10g.esb;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface BasicEsbNode extends ImageFace {

	EsbNodeType getType();

	String getQname();

	Object get();

	/**
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public enum EsbNodeType {
		ESBSRC, ESBSYS, ESBGRP, ESBOPERATION, ESBSVC;
	}
}
