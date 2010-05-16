package com.tomecode.soa.oracle10g;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * This object contains all {@link Project}
 * 
 * @author Tomas Frastia
 * 
 */
public final class Workspace implements TreeNode {

	/**
	 * owner
	 */
	private MultiWorkspace multiWorkspace;

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

	public final void addProject(Project project) {
		project.setWorkspace(this);
		this.projects.add(project);
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

	public final String toString() {
		return name;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final MultiWorkspace getMultiWorkspace() {
		return multiWorkspace;
	}

	public final void setMultiWorkspace(MultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	/**
	 * where bpel process is used...
	 * 
	 * @param usage
	 */
	public final void findUsageBpel(FindUsageProjectResult usage) {
		for (Project project : projects) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpel = (BpelProject) project;

				if (usage.getProject().getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject usageBpelProject = (BpelProject) usage.getProject();

					if (!bpel.compareByBpelXml(usageBpelProject)) {
						for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
							if (partnerLinkBinding.getDependencyProject() != null) {
								if (partnerLinkBinding.getDependencyProject() instanceof BpelProject) {
									if (((BpelProject) partnerLinkBinding.getDependencyProject()).compareByBpelXml(usageBpelProject)) {
										usage.addUsage(partnerLinkBinding.getParent());
									}
								}

							}
						}
					}
				}
			}
		}
	}

	public final void findUsageEsb(FindUsageProjectResult usage) {
		for (Project project : projects) {
			if (project.getType() == ProjectType.ORACLE10G_ESB) {
				EsbProject esbProject = (EsbProject) usage.getProject();
				List<EsbSvc> esbSvcs = esbProject.getAllEsbSvc();
				// EsbSvc esbSvc= (EsbSvc) esbSvcs.get(0).get());
				// esbSvc.getEsbOperations().get(0).get
			}
		}
	}

}
