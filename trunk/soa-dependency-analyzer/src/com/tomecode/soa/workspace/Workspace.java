package com.tomecode.soa.workspace;

import java.io.File;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.project.Project;

/**
 * 
 * Basic interface for all workspaces
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public interface Workspace extends ImageFace {

	/**
	 * workspace name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * workspace folder
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * workspace type
	 * 
	 * @return
	 */
	WorkspaceType getType();

	/**
	 * list of {@link Project}
	 * 
	 * @return
	 */
	List<Project> getProjects();

	/**
	 * parent {@link MultiWorkspace}
	 * 
	 * @return
	 */
	MultiWorkspace getMultiWorkspace();

	/**
	 * Supported workspace type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
