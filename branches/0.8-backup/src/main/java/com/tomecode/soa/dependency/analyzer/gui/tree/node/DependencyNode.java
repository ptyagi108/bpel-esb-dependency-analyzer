package com.tomecode.soa.dependency.analyzer.gui.tree.node;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.project.Project;

/**
 * 
 * helper tree node for depedency projects
 * 
 * @author Tomas Frastia
 * 
 */
public interface DependencyNode extends TreeNode {
	/**
	 * {@link Project}
	 * 
	 * @return
	 */
	Project getProject();

}
