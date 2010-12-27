package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Unknown service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class UnknownService extends Service {

	/**
	 * Constructor
	 * 
	 * @param project
	 * @param name
	 */
	public UnknownService(Project project, String name) {
		super(null, ServiceDependencyType.UNKNOWN);
		this.project = project;
		this.name = name;
		this.orginalName = name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.UNKNOWN;
	}

}
