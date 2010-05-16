package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.List;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;

/**
 * Mutiple workspace contains all workspace in project
 * 
 * @author Tomas Frastia
 */
public final class MultiWorkspace extends BasicNode<Workspace> {

	/**
	 * workspace folder
	 */
	private File file;

	/**
	 * Constructor - wokspace folder
	 * 
	 * @param workspaceFolder
	 */
	public MultiWorkspace(File workspaceFolder) {
		file = workspaceFolder;
	}

	/**
	 * add new {@link Workspace} and set parent
	 * 
	 * @param workspace
	 */
	public final void addWorkspace(Workspace workspace) {
		workspace.setMultiWorkspace(this);
		childs.add(workspace);
	}

	public void addException(File jwsFile, ServiceParserException e) {

	}

	public final List<Workspace> getWorkspaces() {
		return childs;
	}

	public final File getFile() {
		return file;
	}

	/**
	 * find usage for bpel project
	 */
	public final void findUsageBpel(FindUsageProjectResult usage) {
		for (Workspace workspace : childs) {
			workspace.findUsageBpel(usage);
		}
	}

	/**
	 * find usage for esb project
	 * 
	 * @param usage
	 */
	public final void findUsageEsb(FindUsageProjectResult usage) {
		for (Workspace workspace : childs) {
			workspace.findUsageEsb(usage);
		}
	}

}
