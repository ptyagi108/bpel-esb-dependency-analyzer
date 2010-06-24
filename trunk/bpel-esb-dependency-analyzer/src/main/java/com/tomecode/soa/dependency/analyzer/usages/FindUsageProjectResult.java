package com.tomecode.soa.dependency.analyzer.usages;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.project.Project;

/**
 * 
 * impl class for project usage
 * 
 * @author Tomas Frastia
 * 
 */
public final class FindUsageProjectResult implements FindUsage {

	private final Project project;

	private List<Usage> usages;

	/**
	 * Constructor
	 * 
	 * @param bpelProject
	 */
	public FindUsageProjectResult(Project project) {
		this.project = project;
		this.usages = new ArrayList<Usage>();
	}

	public final Project getProject() {
		return project;
	}

	/**
	 * adding new project usage if not exists
	 * 
	 * @param project
	 */
	public final void addUsage(Project project) {
		if (!existUsage(project)) {
			usages.add(new Usage(project));
		}
	}

	/**
	 * check , whether usage exists
	 * 
	 * @param project
	 * @return
	 */
	private final boolean existUsage(Project project) {
		for (Usage usage : usages) {
			if (usage.getProject().equals(project)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return !usages.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return usages.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return usages.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return usages.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return usages.isEmpty();
	}

	public final String toString() {
		return project.toString();
	}

	@Override
	public final ImageIcon getIcon() {
		return project.getIcon();
	}

	/**
	 * create new {@link FindUsageProjectResult} for {@link BpelOperations}
	 * 
	 * @param bpelOperations
	 * @return
	 */
	public static final FindUsageProjectResult createUsageForBpelProject(BpelOperations bpelOperations) {
		FindUsageProjectResult usage = new FindUsageProjectResult(bpelOperations.getBpelProcess());
		if (bpelOperations.getBpelProcess().getWorkspace().getMultiWorkspace() != null) {
			bpelOperations.getBpelProcess().getWorkspace().getMultiWorkspace().findUsageBpel(usage);
		} else {
			bpelOperations.getBpelProcess().getWorkspace().findUsageBpel(usage);
		}
		return usage;
	}

	/**
	 * 
	 * create new {@link FindUsageProjectResult} for bpel project
	 * 
	 * @param project
	 * @return
	 */
	public static final FindUsageProjectResult createUsageForBpelProject(Project project) {
		FindUsageProjectResult usage = new FindUsageProjectResult(project);
		if (project.getWorkspace().getMultiWorkspace() != null) {
			project.getWorkspace().getMultiWorkspace().findUsageBpel(usage);
		} else {
			project.getWorkspace().findUsageBpel(usage);
		}
		return usage;
	}

	/**
	 * 
	 * create {@link FindUsageProjectResult} from {@link EsbServiceNode} for
	 * {@link Project}
	 * 
	 * @param esbServiceNode
	 * @return
	 */
	public static final FindUsageProjectResult createUsageForEsbProject(EsbServiceNode esbServiceNode) {
		FindUsageProjectResult usage = new FindUsageProjectResult(esbServiceNode.getProject());
		if (esbServiceNode.getProject().getWorkspace().getMultiWorkspace() != null) {
			esbServiceNode.getProject().getWorkspace().getMultiWorkspace().findUsageEsb(usage);
		} else {
			esbServiceNode.getProject().getWorkspace().findUsageEsb(usage);
		}
		return usage;
	}

	/**
	 * 
	 * create uage for esb project
	 * 
	 * @param project
	 * @return
	 */
	public static final FindUsageProjectResult createUsageForEsbProject(Project project) {
		FindUsageProjectResult usage = new FindUsageProjectResult(project);
		if (project.getWorkspace().getMultiWorkspace() != null) {
			project.getWorkspace().getMultiWorkspace().findUsageEsb(usage);
		} else {
			project.getWorkspace().findUsageEsb(usage);
		}
		return usage;
	}
}
