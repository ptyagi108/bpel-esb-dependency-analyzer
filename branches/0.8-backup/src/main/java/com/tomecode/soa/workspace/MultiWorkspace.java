package com.tomecode.soa.workspace;

import java.io.File;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;

/**
 * Abstract class for all workspaces
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public interface MultiWorkspace extends TreeNode, IconNode {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the folder
	 */
	File getFolder();

	/**
	 * list of {@link Workspace}
	 * 
	 * @return
	 */
//	List<Workspace> getWorkspaces();
}
