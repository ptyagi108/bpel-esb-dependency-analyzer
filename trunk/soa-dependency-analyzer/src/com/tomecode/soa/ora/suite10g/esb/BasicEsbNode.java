package com.tomecode.soa.ora.suite10g.esb;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
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
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * 
	 * type of service in esb - Oracle 10g SOA Suite
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public enum EsbNodeType {
		ESBSRC, ESBSYS, ESBGRP, ESBOPERATION, ESBSVC, ROUTING;
	}
}
