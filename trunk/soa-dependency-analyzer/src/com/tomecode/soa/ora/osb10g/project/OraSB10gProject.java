package com.tomecode.soa.ora.osb10g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolders;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle Service Bus 10g project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gProject implements Project {

	/**
	 * if is true then this project is JAR file
	 */
	private boolean folder;
	/**
	 * project file or folder
	 */
	private File file;
	/**
	 * parent {@link Workspace}
	 */
	private OraSB10gWorkspace workspace;

	private OraSB10gFolders folders;

	private final List<Service> services;

	private final List<Service> servicesWithFlow;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            project file or folder
	 * @param isFolder
	 */
	public OraSB10gProject(File file, boolean isFolder) {
		this.file = file;
		this.folder = isFolder;
		this.services = new ArrayList<Service>();
		this.servicesWithFlow = new ArrayList<Service>();
		this.folders = new OraSB10gFolders(this, file, null, null);
	}

	public final void addService(Service service) {
		service.setProject(this);
		if (service instanceof Proxy || service instanceof SplitJoin) {
			this.servicesWithFlow.add(service);
		}
		this.services.add(service);
	}

	/**
	 * 
	 * @return all services in project
	 */
	public final List<Service> getServices() {
		return services;
	}

	/**
	 * 
	 * 
	 * @return list of {@link Proxy} or {@link SplitJoin}
	 */
	public final List<Service> getServicesWithFlow() {
		return servicesWithFlow;
	}

	/**
	 * @return the oraSB10gFolders
	 */
	public final OraSB10gFolders getFolders() {
		return folders;
	}

	public final void setWorkspace(OraSB10gWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		return file.getName();
	}

	public final String getName() {
		return file.getName();
	}

	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final ProjectType getType() {
		return ProjectType.ORACLE_SERVICE_BUS_1OG;
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	public final Image getImage(boolean small) {
		return ImageFactory.OSB_10G_PROJECT;
	}

	public final boolean isFolder() {
		return folder;
	}

	@Override
	public final String getToolTip() {
		return "Oracle Service Bus 10g Project\nName: " + getName() + "\nFile: " + (file != null ? file.getPath() : "");
	}

}
