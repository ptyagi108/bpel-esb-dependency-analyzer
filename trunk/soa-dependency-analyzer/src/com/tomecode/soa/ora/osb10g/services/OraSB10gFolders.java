package com.tomecode.soa.ora.osb10g.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;

/**
 * 
 * Root OSB 10g folder
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gFolders extends OraSB10gFolder {

	/**
	 * list of all services in project -
	 */
	private final List<Service> allServices;

	/**
	 * Constructor
	 * 
	 * @param project
	 *            project reference
	 * @param fileSystemPath
	 *            real path in file system
	 * @param path
	 *            in OSB
	 * @param name
	 *            folder name
	 */
	public OraSB10gFolders(OraSB10gProject project, File fileSystemPath, String path, String name) {
		super(project, fileSystemPath, path, name);
		this.allServices = new ArrayList<Service>();
	}

	/**
	 * find a path if not exists path then will created a new path
	 * 
	 * @param path
	 *            path in jar file
	 * @param jarPath
	 *            real path for jar file
	 * @return
	 */
	public final OraSB10gFolder findAndCreate(OraSB10gProject project, String path, File jarPath) {
		String[] paths = path.split("/");
		return findFolderAndCreate(project, 0, paths, jarPath);
	}

	public final void addToAllServices(Service service) {
		allServices.add(service);
	}

	/**
	 * @return the allServices
	 */
	public final List<Service> getAllServices() {
		return allServices;
	}

}
