package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.ora.osb10g.services.dependnecies.OsbActivityDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.project.Project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Service - basic interface for all services in OSB 10g project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class Service implements ImageFace {

	/**
	 * PROXY service file
	 */
	protected File file;

	/**
	 * service name
	 */
	protected String name;

	protected String orginalName;

	/**
	 * parent project
	 */
	protected Project project;

	/**
	 * current folder
	 */
	private OraSB10gFolder folder;

	/**
	 * list of {@link ServiceDependencies}
	 */
	private final ServiceDependencies serviceDependencies;

	private final OsbActivityDependency activityDependency;

	/**
	 * type of dependency
	 */
	protected ServiceDependencyType type;

	/**
	 * Constructor
	 * 
	 * @param file
	 * @param type
	 */
	public Service(File file, ServiceDependencyType type) {
		this.file = file;
		this.type = type;
		this.serviceDependencies = new ServiceDependencies(this);
		this.activityDependency = new OsbActivityDependency(this);
	}

	public final String getName() {
		return name;
	}

	public final Project getProject() {
		return project;
	}

	public final void setProject(Project project) {
		this.project = project;
	}

	public abstract Image getImage();

	public final OraSB10gFolder getFolder() {
		return folder;
	}

	public final void setFolder(OraSB10gFolder folder) {
		this.folder = folder;
	}

	public final File getFile() {
		return file;
	}

	public String toString() {
		return name;
	}

	public final ServiceDependencies getServiceDependencies() {
		return serviceDependencies;
	}

	/**
	 * @return the activityDependency
	 */
	public final OsbActivityDependency getActivityDependency() {
		return activityDependency;
	}

	public final ServiceDependencyType getType() {
		return type;
	}

	public final String getOrginalName() {
		return orginalName;
	}

	@Override
	public String getToolTip() {
		return "Type: " + type.toString() + "\nName" + name + "\nFile: " + file;
	}
}
