package com.tomecode.soa.workspace;

import java.io.File;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;

/**
 * Workspace
 * 
 * @author Tomas Frastia
 * 
 */
public interface Workspace extends TreeNode, IconNode {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the folder
	 */
	File getFolder();

	/**
	 * list of {@link Project}
	 * 
	 * @return
	 */
//	List<Project> getProjects();

}
