package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.panels.WorkspaceUtilsPanel;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.Project;

/**
 * Display all BPEL/ESB projects in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceTree extends BasicTree {

	private static final long serialVersionUID = -14952772269846358L;

	private WorkspaceUtilsPanel workspaceUtilsPanel;

	/**
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	private WorkspaceTree(WorkspaceUtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		setRootVisible(false);
		setCellRenderer(new WorkspaceTreeRenderer());
		createMenuItem("Find Usage for BPEL project", "findUsageBpelProject");
		createMenuItem("Find Usage for ESB project", "findUsageESBproject");
	}

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspaceTree(MultiWorkspace multiWorkspace, WorkspaceUtilsPanel workspaceUtilsPanel) {
		this(workspaceUtilsPanel);
		treeModel.setRoot(multiWorkspace);
		expandProjectNodes(new TreePath(multiWorkspace));
	}

	/**
	 * expand all nodes
	 * 
	 * @param parent
	 */
	protected final void expandProjectNodes(TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (int i = 0; i <= node.getChildCount() - 1; i++) {
				TreePath path = parent.pathByAddingChild(node.getChildAt(i));
				if (path.getLastPathComponent() instanceof Project) {
					expandPath(parent);
				} else {
					expandProjectNodes(path);
				}
			}
		}
		expandPath(parent);
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class WorkspaceTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			}
			
			if (value instanceof Project) {
				if (!((Project) value).isInJws()) {
					rnd.setForeground(Color.BLACK);
					rnd.setFont(rnd.getFont().deriveFont(Font.BOLD));
				}
			}

			return rnd;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FindUsageProjectResult usage = null;

		if (e.getActionCommand().equals("findUsageBpelProject")) {
			Project project = (Project) getSelectionPath().getLastPathComponent();
			usage = new FindUsageProjectResult(project);
			if (project.getWorkspace().getMultiWorkspace() != null) {
				project.getWorkspace().getMultiWorkspace().findUsageBpel(usage);
			} else {
				project.getWorkspace().findUsageBpel(usage);
			}

			workspaceUtilsPanel.showFindUsageBpelProject(usage);

		} else if (e.getActionCommand().equals("findUsageESBproject")) {
			Project project = (Project) getSelectionPath().getLastPathComponent();

			usage = new FindUsageProjectResult(project);
			if (project.getWorkspace().getMultiWorkspace() != null) {
				project.getWorkspace().getMultiWorkspace().findUsageBpel(usage);
			} else {
				project.getWorkspace().findUsageEsb(usage);
			}
			workspaceUtilsPanel.showFindUsageEsbProject(usage);
		}

	}

	@Override
	public void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof BpelProject) {
				enableMenuItem("findUsageBpelProject", true);
			} else if (treePath.getLastPathComponent() instanceof EsbProject) {
				enableMenuItem("findUsageESBproject", true);
			} else {
				enableMenuItem(null, false);
			}
		}
		popupMenu.show(this, x, y);
	}
}
