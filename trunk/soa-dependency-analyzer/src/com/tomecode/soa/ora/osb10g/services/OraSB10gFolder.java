package com.tomecode.soa.ora.osb10g.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Folder in OSB 10g
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class OraSB10gFolder implements ImageFace {

	/**
	 * list of folders in this folder
	 */
	protected final List<OraSB10gFolder> folders;

	/**
	 * list of services in this folder
	 */
	protected final List<Service> services;

	/**
	 * file system path
	 */
	private File fileSystemPath;

	/**
	 * path in OSB
	 */
	private String path;

	/**
	 * folder name
	 */
	private String name;

	/**
	 * parent folder
	 */
	private OraSB10gFolder parent;
	/**
	 * project
	 */
	private OraSB10gProject project;

	/**
	 * Constructor
	 * 
	 * @param project
	 * @param fileSystemPath
	 *            real path in file system
	 * @param path
	 *            path in OSB
	 * @param name
	 *            folder name
	 */
	public OraSB10gFolder(OraSB10gProject project, File fileSystemPath, String path, String name) {
		this.project = project;
		this.services = new ArrayList<Service>();
		this.folders = new ArrayList<OraSB10gFolder>();
		this.fileSystemPath = fileSystemPath;
		this.path = path;
		this.name = name;

	}

	/**
	 * get parent folder
	 * 
	 * @return
	 */
	public final OraSB10gFolder getParent() {
		return parent;
	}

	/**
	 * set parent for this folder
	 * 
	 * @param parent
	 */
	public final void setParent(OraSB10gFolder parent) {
		this.parent = parent;
	}

	/**
	 * add new folder
	 * 
	 * @param folder
	 */
	public final void addFolder(OraSB10gFolder folder) {
		folder.setParent(this);
		this.folders.add(folder);
	}

	/**
	 * list of folders in this folder
	 * 
	 * @return
	 */
	public final List<OraSB10gFolder> getFolders() {
		return folders;
	}

	/**
	 * add new service
	 * 
	 * @param service
	 */
	public final void addService(Service service) {
		this.services.add(service);
		project.addService(service);
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

	/**
	 * find a path if not exists path then will created a new path
	 * 
	 * @param project
	 * @param index
	 * @param paths
	 * @param jarPath
	 * @return
	 */
	protected final OraSB10gFolder findFolderAndCreate(OraSB10gProject project, int index, String[] paths, File jarPath) {

		String targetFolderName = paths[index];
		if (folders.isEmpty()) {
			String realPath = makePath(index, paths);
			addFolder(new OraSB10gFolder(project, new File(jarPath + "/$/" + realPath), realPath, targetFolderName));
		}

		for (OraSB10gFolder folder : folders) {
			if (folder.getName().equals(targetFolderName)) {
				if (index >= paths.length - 1) {
					return folder;
				}
				index++;
				return folder.findFolderAndCreate(project, index, paths, jarPath);
			}
		}

		String realPath = makePath(index, paths);
		OraSB10gFolder newFolder = new OraSB10gFolder(project, new File(jarPath + "/$/" + realPath), realPath, targetFolderName);
		addFolder(newFolder);

		index++;
		if (index <= paths.length - 1) {
			return newFolder.findFolderAndCreate(project, index, paths, jarPath);
		}

		return newFolder;
	}

	private static final String makePath(int index, String[] paths) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= index; i++) {
			sb.append(paths[i]);
			if (i++ < index) {
				sb.append("/");
				i--;
			}
		}
		return sb.toString();
	}

	public final Object[] getAll() {
		Object[] target = new Object[this.folders.size() + this.services.size()];

		System.arraycopy(this.folders.toArray(), 0, target, 0, this.folders.size());
		System.arraycopy(this.services.toArray(), 0, target, this.folders.size(), this.services.size());

		return target;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_OSB_FOLDER;
	}

	@Override
	public final String getToolTip() {
		return "Folder: " + name;
	}
}
