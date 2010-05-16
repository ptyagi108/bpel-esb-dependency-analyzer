package com.tomecode.soa.bpel.dependency.analyzer.usages;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.project.Project;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class FindUsageProjectResult implements FindUsage {

	private final Project project;

	private List<Usage> activities;

	/**
	 * Constructor
	 * 
	 * @param bpelProject
	 */
	public FindUsageProjectResult(Project project) {
		this.project = project;
		this.activities = new ArrayList<Usage>();
	}

	public final Project getProject() {
		return project;
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
		return project.toString();
	}

	@Override
	public final ImageIcon getIcon() {
		return project.getIcon();
	}
}
