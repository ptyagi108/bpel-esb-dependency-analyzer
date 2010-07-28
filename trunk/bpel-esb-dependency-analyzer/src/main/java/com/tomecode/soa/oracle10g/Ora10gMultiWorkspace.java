package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * Multiple workspace contains all workspace in project
 * 
 * @author Tomas Frastia
 */
public final class Ora10gMultiWorkspace extends BasicNode<Ora10gWorkspace> implements MultiWorkspace {

	private static final long serialVersionUID = 5467653582353248725L;
	/**
	 * workspace folder
	 */
	private File folder;

	/**
	 * Constructor - workspace folder
	 * 
	 * @param workspaceFolder
	 */
	public Ora10gMultiWorkspace(File workspaceFolder) {
		this.folder = workspaceFolder;
	}

	/**
	 * add new {@link Ora10gWorkspace} and set parent
	 * 
	 * @param workspace
	 */
	public final void addWorkspace(Ora10gWorkspace workspace) {
		workspace.setMultiWorkspace(this);
		childs.add(workspace);
	}

	public void addException(File jwsFile, ServiceParserException e) {

	}

	public final List<Ora10gWorkspace> getWorkspaces() {
		return childs;
	}

	/**
	 * find usage for BPEL project
	 */
	public final void findUsageBpel(FindUsageProjectResult usage) {
		for (Ora10gWorkspace workspace : childs) {
			workspace.findUsageBpel(usage);
		}
	}

	/**
	 * find usage for ESB project
	 * 
	 * @param usage
	 */
	public final void findUsageEsb(FindUsageProjectResult usage) {
		for (Ora10gWorkspace workspace : childs) {
			workspace.findUsageEsb(usage);
		}
	}

	@Override
	public ImageIcon getIcon() {
		return IconFactory.WORKSPACE;
	}

	@Override
	public final File getFolder() {
		return folder;
	}

	@Override
	public String getName() {
		return null;
	}

}
