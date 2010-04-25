package com.tomecode.soa.oracle10g.esb;

import javax.swing.tree.TreeNode;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public interface BasicEsbNode extends TreeNode {

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
