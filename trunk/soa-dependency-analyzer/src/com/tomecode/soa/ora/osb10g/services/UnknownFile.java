package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownFile implements Service {

	private File fileSystemPath;

	private Project project;

	public UnknownFile(File fileSystemPath) {
		this.fileSystemPath = fileSystemPath;
	}

	/**
	 * @return the fileSystemPath
	 */
	public final File getFileSystemPath() {
		return fileSystemPath;
	}

	public final String getName() {
		return fileSystemPath.getName();
	}

	@Override
	public final Project getProject() {
		return project;
	}

	@Override
	public final void setProject(Project project) {
		this.project = project;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.UNKNOWN;
	}
}
