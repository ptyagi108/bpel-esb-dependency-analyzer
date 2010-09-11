package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;

/**
 * Business service
 * 
 * @author Tomas Frastia
 * 
 */
public final class BusinessService implements Service {

	private Project project;

	private String name;

	private File file;

	public BusinessService(File file) {
		this.file = file;
		int index = file.getName().indexOf(".biz");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			name = file.getName();
		}
	}

	public final File getFile() {
		return file;
	}

	@Override
	public final String getName() {
		return name;
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
		return ImageFactory.OSB_10G_SERVICE;
	}

}
