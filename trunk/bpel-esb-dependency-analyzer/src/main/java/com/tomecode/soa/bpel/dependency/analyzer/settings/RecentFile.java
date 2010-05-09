package com.tomecode.soa.bpel.dependency.analyzer.settings;

import java.io.File;

import com.tomecode.soa.oracle10g.Workspace;

/**
 * Recent file
 * 
 * @author Tomas Frastia
 * 
 */
public final class RecentFile {

	/**
	 * workspace name
	 */
	private String name;
	/**
	 * workspace folder
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            {@link Workspace} name
	 * @param file
	 *            {@link Workspace} folder
	 */
	public RecentFile(String name, File file) {
		this.name = name;
		this.file = file;
	}

	public final String getName() {
		return name;
	}

	public final File getFile() {
		return file;
	}

	public boolean equals(String name, File file) {
		return this.name.equals(name) && this.file.equals(file);
	}

	public final String toString() {
		return name + "::" + file.getPath();
	}
}
