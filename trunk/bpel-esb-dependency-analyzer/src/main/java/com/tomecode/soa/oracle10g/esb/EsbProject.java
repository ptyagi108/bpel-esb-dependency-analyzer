package com.tomecode.soa.oracle10g.esb;

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * Contains esbsvc files in project
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbProject extends Project {

	/**
	 * project name
	 */
	private String name;

	private File projectFolder;
	/**
	 * list of esbsvc
	 */
	private final Vector<Esbsvc> esbs;

	/**
	 * Constructor
	 */
	public EsbProject() {
		super(ProjectType.ORACLE10G_ESB);
		esbs = new Vector<Esbsvc>();
	}

	/**
	 * Constructor
	 * 
	 * @param projectFolder
	 * @param name
	 */
	public EsbProject(File projectFolder, String name) {
		this();
		this.projectFolder = projectFolder;
		this.name = name;
	}

	public final void addEsb(Esbsvc esb) {
		esb.setOwnerEsbProject(this);
		esbs.add(esb);
	}

	public final Vector<Esbsvc> getEsbs() {
		return esbs;
	}

	public final String getName() {
		return name;
	}

	public final File getProjectFolder() {
		return projectFolder;
	}

	@Override
	public Enumeration<Esbsvc> children() {
		return esbs.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !esbs.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		esbs.get(childIndex).getOwnerEsbProject();
		
		return esbs.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return 0;//esbs.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return esbs.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return esbs.isEmpty();
	}

	public final String toString() {
		return name;
	}

}
