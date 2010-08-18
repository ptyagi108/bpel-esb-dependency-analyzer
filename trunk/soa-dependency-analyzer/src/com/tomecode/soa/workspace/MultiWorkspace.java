package com.tomecode.soa.workspace;

/**
 * 
 * Basic interface for all multi workspaces
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public interface MultiWorkspace extends Workspace {

	/**
	 * mutliti workspace type
	 */
	WorkspaceType getType();
}
