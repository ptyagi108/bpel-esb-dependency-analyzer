package com.tomecode.soa.ora.suite10g.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.DependencyNode;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * list of activities (invoke, receive, ...) which contains operations
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BpelOperations implements DependencyNode, ImageFace {

	private static final long serialVersionUID = 8366150968471755335L;

	/**
	 * list of operations in BPEL process
	 */
	private final List<Operation> operations;

	private Ora10gBpelProject bpelProject;

	/**
	 * Constructor
	 */
	public BpelOperations() {
		this.operations = new ArrayList<Operation>();
	}

	/**
	 * Constructor
	 * 
	 * @param process
	 */
	public BpelOperations(Ora10gBpelProject process) {
		this();
		this.bpelProject = process;

	}

	public final void addOperation(Operation operation) {
		operations.add(operation);
	}

	public final Ora10gBpelProject getBpelProcess() {
		return bpelProject;
	}

	public final String toString() {
		return bpelProject.toString();
	}

	public final List<Operation> getOperations() {
		return operations;
	}

	@Override
	public Project getProject() {
		return bpelProject;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_BPEL_PROCESS;
	}

	@Override
	public String getToolTip() {
		return null;
	}
}
