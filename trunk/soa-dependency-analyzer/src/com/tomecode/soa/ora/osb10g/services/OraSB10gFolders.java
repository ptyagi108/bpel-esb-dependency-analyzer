package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;

/**
 * 
 * Root OSB 10g folder
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gFolders extends OraSB10gFolder {

	public OraSB10gFolders(OraSB10gProject project, File fileSystemPath, String path, String name) {
		super(project, fileSystemPath, path, name);
	}

}
