package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownFile implements Service {

	private File fileSystemPath;

	public UnknownFile(File fileSystemPath) {
		this.fileSystemPath = fileSystemPath;
	}

	/**
	 * @return the fileSystemPath
	 */
	public final File getFileSystemPath() {
		return fileSystemPath;
	}

	public final String getName() {
		return fileSystemPath.getName();
	}
}
