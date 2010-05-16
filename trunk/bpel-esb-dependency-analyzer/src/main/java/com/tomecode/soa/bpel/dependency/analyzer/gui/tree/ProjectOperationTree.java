package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.panels.WorkspaceUtilsPanel;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;

/**
 * 
 * Display operation from selected proces in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectOperationTree extends BasicTree {

	private static final long serialVersionUID = -3750125284965106516L;

	private final WorkspaceUtilsPanel workspaceUtilsPanel;

	/**
	 * 
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	public ProjectOperationTree(WorkspaceUtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		setCellRenderer(new OperationTreeRenderer());
		createMenuItem("Find Usage for BPEL project", "findUsageBpelProject");
		createMenuItem("Find Usage for ESB project", "findUsageESBproject");
	}

	/**
	 * display selected bpel proces in {@link WorkspaceTree}
	 * 
	 * @param bpelProcess
	 */
	public final void addBpelProcessOperations(BpelOperations bpelOperations) {
		treeModel.setRoot(bpelOperations);
		expandPath(new TreePath(bpelOperations));
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class OperationTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			}

			return rnd;
		}
	}

	@Override
	public final void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof BpelOperations) {
				enableMenuItem("findUsageBpelProject", true);
			} else if (treePath.getLastPathComponent() instanceof EsbServiceNode) {
				enableMenuItem("findUsageESBproject", true);
			} else {
				enableMenuItem(null, false);
			}
		}
		popupMenu.show(this, x, y);
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		FindUsageProjectResult usage = null;

		if (e.getActionCommand().equals("findUsageBpelProject")) {
			BpelOperations bpelOperations = (BpelOperations) getSelectionPath().getLastPathComponent();
			usage = new FindUsageProjectResult(bpelOperations.getBpelProcess());
			if (bpelOperations.getBpelProcess().getWorkspace().getMultiWorkspace() != null) {
				bpelOperations.getBpelProcess().getWorkspace().getMultiWorkspace().findUsageBpel(usage);
			} else {
				bpelOperations.getBpelProcess().getWorkspace().findUsageBpel(usage);
			}

			workspaceUtilsPanel.showFindUsageBpelProject(usage);

		} else if (e.getActionCommand().equals("findUsageESBproject")) {
			EsbServiceNode esbServiceNode = (EsbServiceNode) getSelectionPath().getLastPathComponent();

			usage = new FindUsageProjectResult(esbServiceNode.getProject());
			if (esbServiceNode.getProject().getWorkspace().getMultiWorkspace() != null) {
				esbServiceNode.getProject().getWorkspace().getMultiWorkspace().findUsageBpel(usage);
			} else {
				esbServiceNode.getProject().getWorkspace().findUsageEsb(usage);
			}
			workspaceUtilsPanel.showFindUsageEsbProject(usage);
		}
	}
}
