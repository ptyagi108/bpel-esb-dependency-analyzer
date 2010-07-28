package com.tomecode.soa.dependency.analyzer.settings;

import java.io.File;

import com.tomecode.soa.oracle10g.Ora10gWorkspace;

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
	private RecentFileType type;
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
	 *            {@link Ora10gWorkspace} name
	 * @param file
	 *            {@link Ora10gWorkspace} folder
	 */
	public RecentFile(String name, String type, File file) {
		this(name, RecentFileType.parseType(type), file);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param type
	 * @param file
	 */
	public RecentFile(String name, RecentFileType type, File file) {
		this.name = name;
		this.type = type;
		this.file = file;
	}

	public final String getName() {
		return name;
	}

	public final RecentFileType getType() {
		return type;
	}

	public final void setType(RecentFileType type) {
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
		return name + ":" + (type == null ? "" : type) + ":" + file.getPath();
	}

	/**
	 * 
	 * Recent file type
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static enum RecentFileType {
		ORACLE10G_MULTIPLE_WORKSPACE("oracle10gMutliWorkspace"), ORACLE10G_WORKSPACE("oracle10gWorkspace"), UNKNOWN("");

		private final String value;

		/**
		 * Constructor
		 * 
		 * @param type
		 *            value in xml
		 */
		private RecentFileType(String type) {
			this.value = type;
		}

		/**
		 * parse {@link RecentFileType} by xmlValue
		 * 
		 * @param type
		 * @return
		 */
		public final static RecentFileType parseType(String type) {
			for (RecentFileType recentFileType : values()) {
				if (recentFileType.value.equalsIgnoreCase(type)) {
					return recentFileType;
				}
			}
			return UNKNOWN;
		}

		public final String getXmlValue() {
			return value;
		}
	}
}
