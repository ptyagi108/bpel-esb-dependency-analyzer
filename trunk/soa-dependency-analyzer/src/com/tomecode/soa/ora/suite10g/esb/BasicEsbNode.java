package com.tomecode.soa.ora.suite10g.esb;

/**
 * 
 * @author Tomas Frastia
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
