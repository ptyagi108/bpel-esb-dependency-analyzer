package com.tomecode.soa.bpel.dependency.analyzer.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.process.Project;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class FindUsageBpelProjectResult implements FindUsage {

	private BpelProject bpelProject;

	private List<Usage> activities;

	/**
	 * Constructor
	 * 
	 * @param bpelProject
	 */
	public FindUsageBpelProjectResult(BpelProject bpelProject) {
		this.bpelProject = bpelProject;
		this.activities = new ArrayList<Usage>();
	}

	public final BpelProject getBpelProject() {
		return bpelProject;
	}

	public void addUsage(Project project) {
		activities.add(new Usage(project));
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return !activities.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return activities.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return activities.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return activities.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return activities.isEmpty();
	}

	public final String toString() {
		return bpelProject.toString();
	}

	@Override
	public final ImageIcon getIcon() {
		return IconFactory.PROCESS;
	}
}
