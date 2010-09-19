package com.tomecode.soa.ora.osb10g.services.dependnecies;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownService;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * Main object. Contains all dependencies from {@link #parent} project
 * 
 * @author Tomas Frastia
 * 
 */
public final class ServiceDependencies {

	private final Service parent;

	/**
	 * list of dependencies
	 */
	private final List<ServiceDependency> dependnecies;

	/**
	 * Constructor
	 * 
	 * @param parent
	 */
	public ServiceDependencies(Service parent) {
		this.parent = parent;
		this.dependnecies = new ArrayList<ServiceDependency>();
	}

	/**
	 * @return the parent
	 */
	public final Service getParent() {
		return parent;
	}

	/**
	 * @return the dependnecies
	 */
	public final List<ServiceDependency> getDependnecies() {
		return dependnecies;
	}

	public void addDependency(ServiceDependency dependency) {
		this.dependnecies.add(dependency);
	}

	public final UnknownService findUnknownService(Project depProject, String serviceName) {
		for (ServiceDependency serviceDependency : dependnecies) {
			for (Service service : serviceDependency.getServices()) {
				if (service.getType() == ServiceDependencyType.UNKNOWN) {
					UnknownService unknownService = (UnknownService) service;
					if (unknownService.getProject().equals(depProject) && unknownService.getName().equals(serviceName)) {
						return unknownService;
					}
				}
			}
		}

		return null;
	}
}
