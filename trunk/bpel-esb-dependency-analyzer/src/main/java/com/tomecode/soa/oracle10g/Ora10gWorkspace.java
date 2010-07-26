package com.tomecode.soa.oracle10g;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * This object contains all {@link Project}
 * 
 * @author Tomas Frastia
 * 
 */
public final class Ora10gWorkspace extends BasicNode<Project> implements IconNode, Serializable {

	private static final long serialVersionUID = -3328038478792839666L;

	/**
	 * owner
	 */
	private Ora10gMultiWorkspace multiWorkspace;

	/**
	 * workspace name
	 */
	private String name;
	/**
	 * jws file
	 */
	private File file;

	/**
	 * Constructor
	 */
	public Ora10gWorkspace() {
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 */
	public Ora10gWorkspace(File jwsFile) {
		this();
		this.file = jwsFile;
		this.name = jwsFile.getName().replace(".jws", "");
	}

	public final List<Project> getProjects() {
		return childs;
	}

	/**
	 * add new {@link Project} and set parent
	 * 
	 * @param project
	 */
	public final void addProject(Project project) {
		project.setWorkspace(this);
		childs.add(project);
	}

	public final File getFile() {
		return file;
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

	public final Ora10gMultiWorkspace getMultiWorkspace() {
		return multiWorkspace;
	}

	public final void setMultiWorkspace(Ora10gMultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	/**
	 * where bpel process is used...
	 * 
	 * @param usage
	 */
	public final void findUsageBpel(FindUsageProjectResult usage) {
		for (Project project : childs) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpel = (BpelProject) project;

				BpelProject usageBpelProject = (BpelProject) usage.getProject();

				if (!bpel.compareByBpelXml(usageBpelProject)) {
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyBpelProject() != null) {

							if (partnerLinkBinding.getDependencyBpelProject().compareByBpelXml(usageBpelProject)) {
								usage.addUsage(partnerLinkBinding.getParent());
							}

						}
					}

				}
			} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
				EsbProject esbProject = (EsbProject) project;
				if (!esbProject.equals(usage.getProject())) {
					List<EsbSvc> esbSvcs = esbProject.getAllEsbSvc();
					for (EsbSvc esbSvc : esbSvcs) {
						esbSvc.findUsage(usage);
					}
				}
			}
		}
	}

	/**
	 * find usage for esb project
	 * 
	 * @param usage
	 */
	public final void findUsageEsb(FindUsageProjectResult usage) {
		for (Project project : childs) {
			if (project.getType() == ProjectType.ORACLE10G_ESB) {
				EsbProject esbProject = (EsbProject) project;
				if (!esbProject.equals(usage.getProject())) {
					List<EsbSvc> esbSvcs = esbProject.getAllEsbSvc();
					for (EsbSvc esbSvc : esbSvcs) {
						esbSvc.findUsage(usage);
					}
				}
			} else if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpelProject = (BpelProject) project;
				List<PartnerLinkBinding> partnerLinkBindings = bpelProject.getPartnerLinkBindings();
				for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
					if (partnerLinkBinding.getDependencyEsbProject() != null) {
						if (partnerLinkBinding.getDependencyEsbProject().equals(usage.getProject())) {
							usage.addUsage(partnerLinkBinding.getParent());
						}
					}
				}

			}
		}
	}

	@Override
	public ImageIcon getIcon() {
		return IconFactory.WORKSPACE;
	}

}
