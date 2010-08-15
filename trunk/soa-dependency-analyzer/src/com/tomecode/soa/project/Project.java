package com.tomecode.soa.project;

import java.io.File;

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

}
