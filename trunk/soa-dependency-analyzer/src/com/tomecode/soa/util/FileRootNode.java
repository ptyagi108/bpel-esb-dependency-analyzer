package com.tomecode.soa.util;

import java.io.File;

import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;

/**
 * Helper node for {@link ProjectStructureNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
public final class FileRootNode {

	private final File[] files;

	public FileRootNode() {
		files = new File[1];
	}

	public final void setFile(File file) {
		files[0] = file;
	}

	public final File[] getFiles() {
		return files;
	}

	public final boolean hasFiles() {
		return files[0] != null;
	}
}
