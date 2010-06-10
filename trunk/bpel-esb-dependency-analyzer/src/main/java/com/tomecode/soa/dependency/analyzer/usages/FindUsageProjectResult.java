package com.tomecode.soa.dependency.analyzer.usages;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.project.Project;

/**
 * 
 * impl class for project usage
 * 
 * @author Tomas Frastia
 * 
 */
public final class FindUsageProjectResult implements FindUsage {

	private final Project project;

	private List<Usage> usages;

	/**
	 * Constructor
	 * 
	 * @param bpelProject
	 */
	public FindUsageProjectResult(Project project) {
		this.project = project;
		this.usages = new ArrayList<Usage>();
	}

	public final Project getProject() {
		return project;
	}

	/**
	 * adding new project usage if not exists
	 * 
	 * @param project
	 */
	public final void addUsage(Project project) {
		if (!existUsage(project)) {
			usages.add(new Usage(project));
		}
	}

	/**
	 * check , whether usage exists
	 * 
	 * @param project
	 * @return
	 */
	private final boolean existUsage(Project project) {
		for (Usage usage : usages) {
			if (usage.getProject().equals(project)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return !usages.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return usages.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return usages.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return usages.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return usages.isEmpty();
	}

	public final String toString() {
		return project.toString();
	}

	@Override
	public final ImageIcon getIcon() {
		return project.getIcon();
	}
}
