package com.tomecode.soa.dependency.analyzer.gui.tree.node;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.project.Project;

/**
 * 
 * Empty node - display only project name
 * 
 * @author Tomas Frastia
 * 
 */
public final class EmptyNode implements TreeNode {

	private Project project;

	public EmptyNode(Project project) {
		this.project = project;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	public final String toString() {
		return project.toString();
	}
}
