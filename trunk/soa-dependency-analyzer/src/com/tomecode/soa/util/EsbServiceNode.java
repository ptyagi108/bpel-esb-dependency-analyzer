package com.tomecode.soa.util;

import com.tomecode.soa.dependency.analyzer.tree.node.DependencyNode;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.project.Project;

/**
 * 
 * Simple helper tree node for esb project - wraper treeNode for
 * {@link Ora10gEsbProject} wich does not show project dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbServiceNode implements DependencyNode {

	private static final long serialVersionUID = -2232894399903425396L;

	private Ora10gEsbProject esbProject;

	/**
	 * Constructor
	 * 
	 * @param esbProject
	 */
	public EsbServiceNode(Ora10gEsbProject esbProject) {
		this.esbProject = esbProject;
	}

	public int getChildCount() {
		return esbProject.getBasicEsbNodes().size();
	}

	//
	// @Override
	// public Enumeration<?> children() {
	// return null;
	// }
	//
	// @Override
	// public boolean getAllowsChildren() {
	// return !esbProject.getBasicEsbNodes().isEmpty();
	// }
	//
	// @Override
	// public TreeNode getChildAt(int childIndex) {
	// return esbProject.getBasicEsbNodes().get(childIndex);
	// }
	//
	// @Override
	// public int getIndex(TreeNode node) {
	// return esbProject.getBasicEsbNodes().indexOf(node);
	// }
	//
	// @Override
	// public TreeNode getParent() {
	// return esbProject;
	// }
	//
	// @Override
	// public boolean isLeaf() {
	// return esbProject.getBasicEsbNodes().isEmpty();
	// }

	public final String toString() {
		return esbProject.toString();
	}

	@Override
	public Project getProject() {
		return esbProject;
	}

	// public ImageIcon getIcon() {
	// return IconFactory.ESB;
	// }
}
