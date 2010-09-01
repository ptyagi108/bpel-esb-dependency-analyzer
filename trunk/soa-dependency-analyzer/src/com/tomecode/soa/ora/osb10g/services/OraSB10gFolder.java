package com.tomecode.soa.ora.osb10g.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public class OraSB10gFolder {

	protected final List<OraSB10gFolder> folders;

	protected final List<Service> services;

	private File fileSystemPath;

	private String path;

	private String name;

	private OraSB10gFolder parent;

	public OraSB10gFolder(File fileSystemPath, String path, String name) {
		this.services = new ArrayList<Service>();
		this.folders = new ArrayList<OraSB10gFolder>();
		this.fileSystemPath = fileSystemPath;
		this.path = path;
		this.name = name;
	}

	public final OraSB10gFolder getParent() {
		return parent;
	}

	public final void setParent(OraSB10gFolder parent) {
		this.parent = parent;
	}

	public final void addFolder(OraSB10gFolder folder) {
		folder.setParent(this);
		this.folders.add(folder);
	}

	public final List<OraSB10gFolder> getFolders() {
		return folders;
	}

	public final void addService(Service service) {
		this.services.add(service);
	}

	/**
	 * @return the services
	 */
	public final List<Service> getServices() {
		return services;
	}

	/**
	 * @return the fileSystemPath
	 */
	public final File getFileSystemPath() {
		return fileSystemPath;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}
}
