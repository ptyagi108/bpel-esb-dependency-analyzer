package com.tomecode.soa.ora.osb10g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.services.OraSB10gFolders;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;

/**
 * Oracle Service Bus 10g project
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gProject implements Project {

	private File file;
	private OraSB10gWorkspace workspace;

	private OraSB10gFolders oraSB10gFolders;

	private final List<Service> services;

	public OraSB10gProject(File file) {
		this.file = file;
		this.services = new ArrayList<Service>();
		this.oraSB10gFolders = new OraSB10gFolders(this, file, null, null);
	}

	public final void addService(Service service) {
		service.setProject(this);
		this.services.add(service);
	}

	public final List<Service> getServices() {
		return services;
	}

	/**
	 * @return the oraSB10gFolders
	 */
	public final OraSB10gFolders getOraSB10gFolders() {
		return oraSB10gFolders;
	}

	public final void setWorkspace(OraSB10gWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		return file.getName();
	}

	public String getName() {
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

	public final List<Proxy> getProxyServices() {
		List<Proxy> list = new ArrayList<Proxy>();
		for (Service service : services) {
			if (service instanceof Proxy) {
				list.add((Proxy) service);
			}
		}
		return list;
	}
}
