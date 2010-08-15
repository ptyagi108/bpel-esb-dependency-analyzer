package com.tomecode.soa.dependency.analyzer.tree.node;

import com.tomecode.soa.project.Project;

/**
 * 
 * helper tree node for dependency projects
 * 
 * @author Tomas Frastia
 * 
 */
public interface DependencyNode {
	/**
	 * {@link Project}
	 * 
	 * @return
	 */
	Project getProject();

}
