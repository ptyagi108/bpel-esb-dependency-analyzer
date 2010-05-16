package com.tomecode.soa.oracle10g.esb;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.DependencyNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.project.Project;

/**
 * 
 * esb operation
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbOperation extends BasicNode<DependencyNode> implements BasicEsbNode, IconNode {

	private String qname;

	private String wsdlOperation;

	private final List<EsbRoutingRule> esbRoutingRules;

	/**
	 * list of depedencies for project
	 */
	// private final Vector<Project> dependencyProjects;
//	private final List<DependencyNode> dependencyProjects;

	/**
	 * Constructor
	 */
	public EsbOperation() {
		esbRoutingRules = new ArrayList<EsbRoutingRule>();
	//	dependencyProjects = new ArrayList<DependencyNode>();
	}

	/**
	 * Constructor
	 * 
	 * @param qname
	 * @param wsdlOperation
	 */
	public EsbOperation(String qname, String wsdlOperation) {
		this();
		this.qname = qname;
		this.wsdlOperation = wsdlOperation;
	}

	public final String getQname() {
		return qname;
	}

	public final String getWsdlOperation() {
		return wsdlOperation;
	}

	public final void addOperation(EsbOperation esbOperation) {
		esbOperation.addOperation(esbOperation);
	}

//	@Override
//	public Enumeration<?> children() {
//		return null;
//	}
//
//	@Override
//	public final boolean getAllowsChildren() {
//		return dependencyProjects.isEmpty();// esbRoutingRules.isEmpty();
//	}
//
//	@Override
//	public TreeNode getChildAt(int childIndex) {
//		return dependencyProjects.get(childIndex);// esbRoutingRules.get(childIndex);
//	}
//
//	@Override
//	public int getChildCount() {
//		return dependencyProjects.size();// esbRoutingRules.size();
//	}
//
//	@Override
//	public int getIndex(TreeNode node) {
//		return dependencyProjects.indexOf(node);// esbRoutingRules.indexOf(node);
//	}
//
//	@Override
//	public TreeNode getParent() {
//		return this;
//	}
//
//	@Override
//	public boolean isLeaf() {
//		return dependencyProjects.isEmpty();// esbRoutingRules.isEmpty();
//	}

	public final void addRoutingRule(EsbRoutingRule esbRoutingRule) {
		esbRoutingRules.add(esbRoutingRule);
	}

	public final String toString() {
		return wsdlOperation;
	}

	@Override
	public Object get() {
		return this;
	}

	@Override
	public EsbNodeType getType() {
		return EsbNodeType.ESBOPERATION;
	}

	public final void addDepdendencyProject(EsbProject project) {
		if (!containsDependencyProject(project)) {
			childs.add(new EsbServiceNode(project));
		}
	}

	public final void addDepdendencyProject(BpelProject project) {
		if (!containsDependencyProject(project)) {
			childs.add(project.getBpelOperations());
		}
	}

	private final boolean containsDependencyProject(Project project) {
		for (DependencyNode dependencyNode : childs) {
			if (dependencyNode.getProject().equals(project)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ImageIcon getIcon() {
		return IconFactory.OPERATION;
	}

}
