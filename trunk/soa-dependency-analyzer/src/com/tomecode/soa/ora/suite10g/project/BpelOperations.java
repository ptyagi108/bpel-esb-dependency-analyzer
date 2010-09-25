package com.tomecode.soa.ora.suite10g.project;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.tree.node.DependencyNode;
import com.tomecode.soa.project.Project;

/**
 * 
 * list of activities (invoke, receive, ...) which contains operations
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BpelOperations implements DependencyNode {

	private static final long serialVersionUID = 8366150968471755335L;

	/**
	 * list of operations in BPEL process
	 */
	private final List<Operation> operations;

	private BpelProject bpelProject;

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
	public BpelOperations(BpelProject process) {
		this();
		this.bpelProject = process;

	}

	public final void addOperation(Operation operation) {
		operations.add(operation);
	}

	public final BpelProject getBpelProcess() {
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

	// public ImageIcon getIcon() {
	// return IconFactory.PROCESS;
	// }
}
