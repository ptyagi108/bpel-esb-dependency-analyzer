package com.tomecode.soa.dependency.analyzer.tree.node;

import com.tomecode.soa.ora.suite10g.esb.EsbProject;
import com.tomecode.soa.project.Project;

/**
 * 
 * Simple helper tree node for ESB project - wrapper treeNode for
 * {@link EsbProject} which does not show project dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EsbServiceNode implements DependencyNode {

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

	public final boolean hasChildren() {
		return !esbProject.getBasicEsbNodes().isEmpty();
	}

	public final Object[] getChildren() {
		return esbProject.getBasicEsbNodes().toArray();
	}

	public final String toString() {
		return esbProject.toString();
	}

	@Override
	public Project getProject() {
		return esbProject;
	}

}
