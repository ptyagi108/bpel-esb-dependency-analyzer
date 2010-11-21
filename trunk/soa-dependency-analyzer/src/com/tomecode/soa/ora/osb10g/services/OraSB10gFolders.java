package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

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

	public OraSB10gFolders(OraSB10gProject project, File fileSystemPath, String path, String name) {
		super(project, fileSystemPath, path, name);
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

}
