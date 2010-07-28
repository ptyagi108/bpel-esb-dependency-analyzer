package com.tomecode.soa.bpel.structure;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;

/**
 * Default class all BPEL process structure
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class AbstractProcessStructure extends BasicNode<Activity> {

	private static final long serialVersionUID = 3689765531696663106L;

	public AbstractProcessStructure() {
		super();
	}

	public final void addActivity(Activity activity) {
		childs.add(activity);
	}

}
