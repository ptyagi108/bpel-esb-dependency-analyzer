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
	 * workspace type: IF <b>M</b> then is multiworkspace if <b>W</b> is simple
	 * workspace
	 */
	private String type;
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
	public RecentFile(String name, String type, File file) {
		this.name = name;
		this.type = type;
		this.file = file;
	}

	public final String getName() {
		return name;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	/**
	 * workpsace file
	 * 
	 * @return
	 */
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
