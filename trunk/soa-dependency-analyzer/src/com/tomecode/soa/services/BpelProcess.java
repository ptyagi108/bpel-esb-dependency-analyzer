package com.tomecode.soa.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.project.Project;

/**
 * 
 * BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public interface BpelProcess {

	Project getProject();

	Image getImage();

	String getName();

	File getFile();

}
