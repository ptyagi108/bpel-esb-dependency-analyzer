package com.tomecode.soa.ora.suite10g.esb;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface BasicEsbNode {

	EsbNodeType getType();

	String getQname();

	Object get();

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public enum EsbNodeType {
		ESBSRC, ESBSYS, ESBGRP, ESBOPERATION, ESBSVC;
	}
}
