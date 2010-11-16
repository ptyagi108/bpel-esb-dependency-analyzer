package com.tomecode.soa.services;

import java.io.File;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
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
public interface BpelProcess extends ImageFace {

	/**
	 * The project, which contains a BPEL process
	 * 
	 * @return
	 */
	Project getProject();

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
