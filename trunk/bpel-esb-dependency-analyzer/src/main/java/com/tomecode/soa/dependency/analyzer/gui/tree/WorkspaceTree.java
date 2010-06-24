package com.tomecode.soa.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tomecode.soa.dependency.analyzer.gui.FrmProjectInfo;
import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory;
import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory.MenuItems;
import com.tomecode.soa.dependency.analyzer.gui.panels.UtilsPanel;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
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

	private UtilsPanel workspaceUtilsPanel;

	/**
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	private WorkspaceTree(UtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		setRootVisible(false);
		setCellRenderer(new WorkspaceTreeRenderer());
		popupMenu.add(MenuFactory.createFindUsageBpel(this));
		popupMenu.add(MenuFactory.createFindUsageEsb(this));
		popupMenu.addSeparator();
		popupMenu.add(MenuFactory.createProjectProperties(this));
	}

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspaceTree(MultiWorkspace multiWorkspace, UtilsPanel workspaceUtilsPanel) {
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
		Project project = (Project) getSelectionPath().getLastPathComponent();
		if (e.getActionCommand().equals(MenuItems.FIND_USAGE_BPEL.getActionCmd())) {
			workspaceUtilsPanel.showFindUsageBpelProject(FindUsageProjectResult.createUsageForBpelProject(project));
		} else if (e.getActionCommand().equals(MenuItems.FIND_USAGE_ESB.getActionCmd())) {
			workspaceUtilsPanel.showFindUsageBpelProject(FindUsageProjectResult.createUsageForEsbProject(project));
		} else if (e.getActionCommand().equals(MenuItems.PROJECT_PROPERTIES.getActionCmd())) {
			FrmProjectInfo.showMe(project);
		}

	}

	@Override
	public final void showPopupMenu(int x, int y) {
		enableMenuItem(null, false);
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof BpelProject) {
				enableMenuItem(MenuItems.FIND_USAGE_BPEL.getActionCmd(), true);
				enableMenuItem(MenuItems.PROJECT_PROPERTIES.getActionCmd(), true);
			} else if (treePath.getLastPathComponent() instanceof EsbProject) {
				enableMenuItem(MenuItems.FIND_USAGE_ESB.getActionCmd(), true);
				enableMenuItem(MenuItems.PROJECT_PROPERTIES.getActionCmd(), true);
			}
		}
		popupMenu.show(this, x, y);
	}
}
