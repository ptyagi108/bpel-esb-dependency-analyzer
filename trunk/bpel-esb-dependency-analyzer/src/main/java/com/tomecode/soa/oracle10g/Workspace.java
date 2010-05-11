package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * This object contains all {@link Project}
 * 
 * @author Tomas Frastia
 * 
 */
public final class Workspace implements TreeNode {

	private String name;

	private File file;

	private final Vector<Project> projects;

	/**
	 * Constructor
	 */
	public Workspace() {
		this.projects = new Vector<Project>();
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 */
	public Workspace(File jwsFile) {
		this();
		this.file = jwsFile;
		this.name = jwsFile.getName().replace(".jws", "");
	}

	public final Vector<Project> getProjects() {
		return projects;
	}

	public final void addProject(Project service) {
		this.projects.add(service);
	}

	public final File getFile() {
		return file;
	}

	@Override
	public Enumeration<?> children() {
		return projects.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !projects.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return projects.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return projects.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return projects.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return projects.isEmpty();
	}

	/**
	 * find usage for bpel process (only bpel )
	 * 
	 * @param usage
	 * @return
	 */
	public final List<BpelProject> findBpelUsages(BpelProject usage) {
		List<BpelProject> list = new ArrayList<BpelProject>();

		for (Project project : projects) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpel = (BpelProject) project;
				if (!bpel.equals(usage)) {
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyProject() != null) {
							if (partnerLinkBinding.getDependencyProject().equals(usage)) {
								list.add(partnerLinkBinding.getParent());
							}
						}
					}
				}
			}
		}

		return list;
		// return new ArrayList<Bpel>();
	}

	public final String toString() {
		return name;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}
}
