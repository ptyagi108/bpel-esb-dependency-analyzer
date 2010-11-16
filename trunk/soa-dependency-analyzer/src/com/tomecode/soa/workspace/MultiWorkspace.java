package com.tomecode.soa.workspace;

import java.io.File;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * Basic interface for all multi workspaces
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public interface MultiWorkspace extends ImageFace {

	String getName();

	File getFile();

	/**
	 * multi workspace type
	 */
	WorkspaceType getType();

	boolean containsWorkspace(Workspace workspace);

}
