package com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.Project;

/**
 * 
 * Simple helper tree node for esb project - wraper treeNode for
 * {@link EsbProject} wich does not show project dependencies
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbServiceNode implements TreeNode, DependencyNode {

	private static final long serialVersionUID = -2232894399903425396L;

	private EsbProject esbProject;

	/**
	 * Constructor
	 * 
	 * @param esbProject
	 */
	public EsbServiceNode(EsbProject esbProject) {
		this.esbProject = esbProject;
	}

	public int getChildCount() {
		return esbProject.getBasicEsbNodes().size();
	}

	@Override
	public Enumeration<?> children() {
		return esbProject.getBasicEsbNodes().elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !esbProject.getBasicEsbNodes().isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return esbProject.getBasicEsbNodes().get(childIndex);
	}

	@Override
	public int getIndex(TreeNode node) {
		return esbProject.getBasicEsbNodes().indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return esbProject;
	}

	@Override
	public boolean isLeaf() {
		return esbProject.getBasicEsbNodes().isEmpty();
	}

	public final String toString() {
		return esbProject.toString();
	}

	@Override
	public Project getProject() {
		return esbProject;
	}
}
