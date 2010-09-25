package com.tomecode.soa.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.project.Project;

/**
 * 
 * BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface BpelProcess {

	/**
	 * The project, which contains a BPEL process
	 * 
	 * @return
	 */
	Project getProject();

	/**
	 * image for a BPEL process
	 * 
	 * @return
	 */
	Image getImage();

	/**
	 * name of BPEL process
	 * 
	 * @return
	 */
	String getName();

	/**
	 * *.bpel.xml
	 * 
	 * @return
	 */
	File getFile();

}
