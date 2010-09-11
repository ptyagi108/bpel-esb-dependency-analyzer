package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.project.Project;

/**
 * Service - basic interface for all services in OSB 10g project
 * 
 * @author Tomas Frastia
 * 
 */
public interface Service {

	String getName();

	Project getProject();

	void setProject(Project project);

	Image getImage();
}
