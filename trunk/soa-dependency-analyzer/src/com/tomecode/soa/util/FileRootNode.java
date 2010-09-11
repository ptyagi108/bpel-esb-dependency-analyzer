package com.tomecode.soa.util;

import java.io.File;

import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.project.Project;

/**
 * Helper node for {@link ProjectStructureNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
public final class FileRootNode {

	private final File[] files;

	private Project project;

	public FileRootNode() {
		files = new File[1];
	}

	public final File[] getFiles() {
		return files;
	}

	public final boolean hasFiles() {
		return files[0] != null;
	}

	/**
	 * @return the project
	 */
	public final Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public final void setProject(Project project) {
		this.project = project;
		if (project != null) {
			files[0] = project.getFile();
		} else {
			files[0] = null;
		}
	}

}
