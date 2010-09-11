package com.tomecode.soa.project;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Basic interface for all BPEL/ESB projects
 * 
 * @author Tomas Frastia
 * 
 */
public interface Project {

	String getName();

	File getFile();

	/**
	 * project type
	 * 
	 * @return
	 */
	ProjectType getType();

	Workspace getWorkpsace();

	Image getImage();

}
