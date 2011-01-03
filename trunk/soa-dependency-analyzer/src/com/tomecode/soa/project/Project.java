package com.tomecode.soa.project;

import java.io.File;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic interface for all BPEL/ESB projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */

public interface Project extends ImageFace {

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

	boolean isFolder();
}
