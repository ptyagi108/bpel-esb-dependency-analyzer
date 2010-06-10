package com.tomecode.soa.oracle10g.esb;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.DependencyNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
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

	private static final long serialVersionUID = 7634028314194386738L;

	private String qname;

	private String wsdlOperation;

	private EsbSvc esbSvc;
	private final List<EsbRoutingRule> esbRoutingRules;

	/**
	 * Constructor
	 */
	public EsbOperation() {
		esbRoutingRules = new ArrayList<EsbRoutingRule>();
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

	public final List<DependencyNode> getDependencyNodes() {
		return childs;
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

	public final EsbSvc getEsbSvc() {
		return esbSvc;
	}

	public final void setEsbSvc(EsbSvc esbSvc) {
		this.esbSvc = esbSvc;
	}

	public final void findUsage(FindUsageProjectResult usage) {
		for (DependencyNode dependencyNode : childs) {
			if (dependencyNode.getProject().equals(usage.getProject())) {
				usage.addUsage(esbSvc.getOwnerEsbProject());
			}
		}
	}

}
