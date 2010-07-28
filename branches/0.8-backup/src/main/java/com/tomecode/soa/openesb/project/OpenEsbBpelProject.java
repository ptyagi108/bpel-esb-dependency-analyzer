package com.tomecode.soa.openesb.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * Open ESB - BPEL project
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbBpelProject extends Project {

	private static final long serialVersionUID = 2921064536043532535L;

	private final List<OpenEsbBpelProcess> bpelProcesses;

	public OpenEsbBpelProject(String name, File file) {
		super(name, file, ProjectType.OPEN_ESB_BPEL);
		this.bpelProcesses = new ArrayList<OpenEsbBpelProcess>();
	}

	public final void addBpelProcess(OpenEsbBpelProcess bpelProcess) {
		this.bpelProcesses.add(bpelProcess);
	}

	@Override
	public Enumeration<?> children() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
