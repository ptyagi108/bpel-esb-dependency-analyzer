package com.tomecode.soa.oracle10g.bpel;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.DependencyNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.project.Project;

/**
 * 
 * list of activities (invoke, receive, ...) wich contains operations
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelOperations extends BasicNode<Operation> implements DependencyNode, IconNode {

	private static final long serialVersionUID = 8366150968471755335L;
	private BpelProject bpelProject;

	/**
	 * Constructor
	 */
	public BpelOperations() {
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
		childs.add(operation);
	}

	public final BpelProject getBpelProcess() {
		return bpelProject;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	public final String toString() {
		return bpelProject.toString();
	}

	@Override
	public Project getProject() {
		return bpelProject;
	}

	@Override
	public ImageIcon getIcon() {
		return IconFactory.PROCESS;
	}
}
