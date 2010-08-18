package com.tomecode.soa.workspace;

import java.io.File;

/**
 * 
 * Basic interface for all workspaces
 * 
 * @author Tomas Frastia
 * 
 */
public interface Workspace {

	/**
	 * workspace name
	 * 
	 * @return
	 */
	String getName();

	File getFile();

	/**
	 * workspace type
	 * 
	 * @return
	 */
	WorkspaceType getType();

	/**
	 * Supported workspace type
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public enum WorkspaceType {
		ORACLE_1OG("Oracle SOA SUITE 10g"), ORACLE_11G("Oracle SOA Fusion 11g"), OPEN_ESB("Open ESB"), ORACLE_SERVICE_BUS_10G("Oracle Service Bus 10g");

		private final String title;

		WorkspaceType(String titile) {
			this.title = titile;
		}

		public final String toString() {
			return title;
		}
	}
}
