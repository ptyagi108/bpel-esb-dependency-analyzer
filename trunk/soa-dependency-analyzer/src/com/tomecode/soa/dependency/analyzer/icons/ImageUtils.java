package com.tomecode.soa.dependency.analyzer.icons;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * Utilities for images
 * 
 * @author Tomas Frastia
 * 
 */
public final class ImageUtils {

	private ImageUtils() {

	}

	public static final Image getProjectImage(Project project) {
		if (project.getType() == ProjectType.OPEN_ESB_BPEL) {
			return ImageFactory.OPEN_ESB_BPEL_PROCESS;
		} else if (project.getType() == ProjectType.ORACLE10G_BPEL) {
			return ImageFactory.ORACLE_10G_BPEL_PROCESS;
		} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
			return ImageFactory.ORACLE_10G_ESB;
		}
		return null;
	}

	public static final Image getMultiWorkspaceImage(Object object) {
		return ImageFactory.MULTI_WORKSPACE;
	}
}
