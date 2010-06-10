package com.tomecode.soa.oracle10g.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * 
 * This object contais basic info about bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProject extends Project {

	private static final long serialVersionUID = 267257551834214909L;

	private String id;

	private String src;
	/**
	 * *.bpel file
	 */
	private File bpelXmlFile;

	/**
	 * {@link PartnerLinkBinding}
	 */
	private final List<PartnerLinkBinding> partnerLinkBindings;

	/**
	 * list of depedendecy {@link Project}
	 */
	private final List<Project> dependencyProjects;

	private final BpelOperations bpelOperations;

	private final BpelProcessStrukture bpelProcessStrukture;

//	/**
//	 * list of {@link EsbSvc} from which are dependency on me
//	 */
//	private final List<EsbSvc> dependenceEsbSvc;

	/**
	 * bpel process wsdl
	 */
	private Wsdl wsdl;

	/**
	 * Constructor
	 */
	public BpelProject() {
		super(ProjectType.ORACLE10G_BPEL);
		this.partnerLinkBindings = new ArrayList<PartnerLinkBinding>();
		this.dependencyProjects = new ArrayList<Project>();
		this.bpelOperations = new BpelOperations(this);
		this.bpelProcessStrukture = new BpelProcessStrukture(this);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param src
	 * @param bpelXmlFile
	 */
	public BpelProject(String id, String src, File bpelXmlFile) {
		this();
		this.id = id;
		this.src = src;
		this.bpelXmlFile = bpelXmlFile;
	}

	public final void addPartnerLinkBinding(PartnerLinkBinding partnerLink) {
		partnerLink.setParent(this);
		partnerLinkBindings.add(partnerLink);
	}

	public final List<PartnerLinkBinding> getPartnerLinkBindings() {
		return partnerLinkBindings;
	}

	public final String getId() {
		return id;
	}

	public final String getSrc() {
		return src;
	}

	public final File getBpelXmlFile() {
		return bpelXmlFile;
	}

	public final Wsdl getWsdl() {
		return wsdl;
	}

	public final void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return dependencyProjects.isEmpty();// partnerLinkBindings.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// PartnerLinkBinding partnerLinkBinding =
		// partnerLinkBindings.get(childIndex);
		//
		// if (partnerLinkBinding.getDependencyBpelProject() != null) {
		// return partnerLinkBinding.getDependencyBpelProject();
		// } else if (partnerLinkBinding.getDependencyEsbProject() != null) {
		// return partnerLinkBinding.getDependencyEsbProject();
		// }
		//
		// return new ErrorNode("not found service - " +
		// partnerLinkBinding.getName() + "",
		// partnerLinkBinding.getWsdlLocation(),
		// partnerLinkBinding.getParseErrror());
		return dependencyProjects.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return dependencyProjects.size();// partnerLinkBindings.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return dependencyProjects.indexOf(node);// partnerLinkBindings.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return partnerLinkBindings.isEmpty();
	}

	public final String toString() {
		return id == null ? src : id;
	}

	public final PartnerLinkBinding findPartnerLinkBinding(String partnerLinkName) {
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			if (partnerLinkBinding.getName().equals(partnerLinkName)) {
				return partnerLinkBinding;
			}
		}
		return null;
	}

	public final BpelOperations getBpelOperations() {
		return bpelOperations;
	}

	public final BpelProcessStrukture getBpelProcessStrukture() {
		return bpelProcessStrukture;
	}

	public final boolean compareByBpelXml(BpelProject bpelProject) {
		return (bpelXmlFile.equals(bpelProject.getBpelXmlFile()));
	}

	@Override
	public final ImageIcon getIcon() {
		return IconFactory.PROCESS;
	}

	/**
	 * analysis of {@link Project} dependnecies
	 */
	public final void analysisProjectDependencies() {
		dependencyProjects.clear();
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				if (!dependencyProjects.contains(partnerLinkBinding.getDependencyBpelProject())) {
					dependencyProjects.add(partnerLinkBinding.getDependencyBpelProject());
				}
			} else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				if (!dependencyProjects.contains(partnerLinkBinding.getDependencyEsbProject())) {
					dependencyProjects.add(partnerLinkBinding.getDependencyEsbProject());
				}
			} else {
				dependencyProjects.add(new UnknownProject(partnerLinkBinding));
			}
		}

	}
	
	
}
