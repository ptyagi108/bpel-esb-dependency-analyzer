package com.tomecode.soa.bpel.model;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

/**
 * This object contains all {@link BpelProcess}
 * 
 * @author Tomas Frastia
 * 
 */
public final class Workspace implements TreeNode {

	private File file;

	private final Vector<BpelProcess> bpelProcesses;

	public Workspace() {
		this.bpelProcesses = new Vector<BpelProcess>();
	}

	public Workspace(File file) {
		this();
		this.file = file;
	}

	public final void addBpelProcess(BpelProcess bpelProcess) {
		this.bpelProcesses.add(bpelProcess);
	}

	public final File getFile() {
		return file;
	}

	@Override
	public Enumeration<?> children() {
		return bpelProcesses.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !bpelProcesses.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return bpelProcesses.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return bpelProcesses.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return bpelProcesses.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return bpelProcesses.isEmpty();
	}

}
