package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;

/**
 * Mutiple workspace contains all workspace in project
 * 
 * @author Tomas Frastia
 */
public final class MultiWorkspace implements TreeNode {

	private final List<Workspace> workspaces;

	private File file;

	/**
	 * Constructor - wokspace folder
	 * 
	 * @param workspaceFolder
	 */
	public MultiWorkspace(File workspaceFolder) {
		workspaces = new ArrayList<Workspace>();
		file = workspaceFolder;
	}

	public final void addWorkspace(Workspace workspace) {
		workspace.setMultiWorkspace(this);
		workspaces.add(workspace);
	}

	public void addException(File jwsFile, ServiceParserException e) {

	}

	public final List<Workspace> getWorkspaces() {
		return workspaces;
	}

	public final File getFile() {
		return file;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return !workspaces.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return workspaces.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return workspaces.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return workspaces.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return workspaces.isEmpty();
	}

	public final void findUsageBpel(FindUsageProjectResult usage) {
		for (Workspace workspace : workspaces) {
			workspace.findUsageBpel(usage);
		}
	}
	public final void findUsageEsb(FindUsageProjectResult usage) {
		for (Workspace workspace : workspaces) {
			workspace.findUsageEsb(usage);
		}
	}
//	public final void findUsage2(FindUsageProjectResult usage) {
//		for (Workspace workspace : workspaces) {
//			workspace.findUsage2(usage);
//		}
//	}

}
