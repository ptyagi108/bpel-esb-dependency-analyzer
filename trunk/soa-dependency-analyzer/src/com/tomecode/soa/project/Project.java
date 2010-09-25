package com.tomecode.soa.project;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Basic interface for all BPEL/ESB projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */

public interface Project {

	/**
	 * project name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * project folder
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * project type
	 * 
	 * @return
	 */
	ProjectType getType();

	/**
	 * workspace which contains a project
	 * 
	 * @return
	 */
	Workspace getWorkpsace();

	/**
	 * image for GUI
	 * 
	 * @return
	 */
	Image getImage();

}
