package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * Unknown service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class UnknownService extends Service {

	public UnknownService(Project project, String name) {
		super(null, ServiceDependencyType.UNKNOWN);
		this.project = project;
		this.name = name;
	}

	@Override
	public Image getImage() {
		return ImageFactory.UNKNOWN;
	}

}
