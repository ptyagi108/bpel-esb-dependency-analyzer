package com.tomecode.soa.workspace;

import java.io.File;

import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * Basic interface for all multi workspaces
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public interface MultiWorkspace {

	String getName();

	File getFile();

	/**
	 * multi workspace type
	 */
	WorkspaceType getType();

}
