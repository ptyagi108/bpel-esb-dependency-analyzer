package com.tomecode.soa.ora.suite10g.esb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.DependencyNode;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.util.EsbServiceNode;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved. ESB operation
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbOperation implements BasicEsbNode {

	private static final long serialVersionUID = 7634028314194386738L;

	private final List<Object> childs;

	private String qname;

	private String wsdlOperation;

	private EsbSvc esbSvc;

	private Mep mep;

	/**
	 * Constructor
	 */
	public EsbOperation() {
		this.childs = new ArrayList<Object>();
		this.mep = Mep.REQUEST;
	}

	/**
	 * Constructor
	 * 
	 * @param qname
	 * @param wsdlOperation
	 */
	public EsbOperation(String qname, String wsdlOperation, String mep) {
		this();
		this.qname = qname;
		this.wsdlOperation = wsdlOperation;

		if (Mep.REQUEST_RESPONSE.name.equals(mep)) {
			this.mep = Mep.REQUEST_RESPONSE;
		} else {
			this.mep = Mep.REQUEST;
		}
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
		childs.add(esbRoutingRule);
	}

	public final String toString() {
		return wsdlOperation;
	}

	@Override
	public Object get() {
		return this;
	}

	public final List<Object> getDependencies() {
		return childs;
	}

	public final List<Object> makeListWithoutRountingRule() {
		List<Object> list = new ArrayList<Object>();
		for (Object o : childs) {
			if (o instanceof EsbRoutingRule) {
				List<EsbSvc> esbSvcs = ((EsbRoutingRule) o).getEsbSvcs();
				for (EsbSvc esbSvc : esbSvcs) {
					if (!list.contains(esbSvc)) {
						list.add(esbSvc);
					}
				}
			} else {
				list.add(o);
			}

		}
		return list;
	}

	@Override
	public EsbNodeType getType() {
		return EsbNodeType.ESBOPERATION;
	}

	public final void addDepdendencyProject(Ora10gEsbProject project) {
		if (!containsDependencyProject(project)) {
			childs.add(new EsbServiceNode(project));
		}
	}

	public final void addDepdendencyProject(Ora10gBpelProject project) {
		if (!containsDependencyProject(project)) {
			childs.add(project.getBpelOperations());
		}
	}

	public final void addDependency(BasicEsbNode dependencyNode) {
		if (!childs.contains(dependencyNode)) {
			childs.add(dependencyNode);
		}
	}

	private final boolean containsDependencyProject(Project project) {
		for (Object o : childs) {
			if (o instanceof DependencyNode) {
				DependencyNode dependencyNode = (DependencyNode) o;
				if (dependencyNode.getProject().equals(project)) {
					return true;
				}
			}

		}
		return false;
	}

	// public ImageIcon getIcon() {
	// return IconFactory.OPERATION;
	// }

	public final EsbSvc getEsbSvc() {
		return esbSvc;
	}

	public final void setEsbSvc(EsbSvc esbSvc) {
		this.esbSvc = esbSvc;
	}

	@Override
	public final Image getImage() {
		if (mep == Mep.REQUEST) {
			return ImageFactory.ORACLE_10G_OPERATION_REQUEST;
		}
		return ImageFactory.ORACLE_10G_OPERATION_REQUEST_RESPONSE;
	}

	@Override
	public final String getToolTip() {
		return "Operation: " + wsdlOperation;
	}

	// public final List<EsbRoutingRule> getEsbRoutingRules() {
	// return esbRoutingRules;
	// }

	// public final void findUsage(FindUsageProjectResult usage) {
	// for (DependencyNode dependencyNode : childs) {
	// if (dependencyNode.getProject().equals(usage.getProject())) {
	// usage.addUsage(esbSvc.getOwnerEsbProject());
	// }
	// }
	// }

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved. ESB operation
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	private enum Mep {
		REQUEST("Request"), REQUEST_RESPONSE("RequestResponse");

		private final String name;

		Mep(String name) {
			this.name = name;
		}
	}

}
