package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;

/**
 * Mutiple workspace contains all workspace in project
 * 
 * @author Tomas Frastia
 */
public final class MultiWorkspace implements TreeNode {

	private final List<Workspace> workspaces;

	private File file;

	public MultiWorkspace(File workspaceFolder) {
		workspaces = new ArrayList<Workspace>();
		file = workspaceFolder;
	}

	public final void addWorkspace(Workspace w) {
		workspaces.add(w);
	}

	public void addException(File jwsFile, ServiceParserException e) {

	}

	public final List<Workspace> getWorkspaces() {
		return workspaces;
	}

	/**
	 * find usage for bpel process
	 * 
	 * @param bpelProcess
	 * @return
	 */
	public final List<BpelProject> findBpelUsages(BpelProject bpelProcess) {
		List<BpelProject> list = new ArrayList<BpelProject>();

		for (Workspace workspace : workspaces) {
			list.addAll(workspace.findBpelUsages(bpelProcess));
		}

		return list;
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

}
