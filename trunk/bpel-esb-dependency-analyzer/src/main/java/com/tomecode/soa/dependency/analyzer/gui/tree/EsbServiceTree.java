package com.tomecode.soa.dependency.analyzer.gui.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory;
import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory.MenuItems;
import com.tomecode.soa.dependency.analyzer.gui.panels.UtilsPanel;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.esb.EsbProject;

/**
 * 
 * show esb project services
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbServiceTree extends BasicTree {

	private static final long serialVersionUID = -6785404093212997928L;

	private final UtilsPanel workspaceUtilsPanel;

	/**
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	public EsbServiceTree(UtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		setCellRenderer(new EsbServiceTreeRenederer());
		popupMenu.add(MenuFactory.createFindUsageBpel(this));
		popupMenu.add(MenuFactory.createFindUsageEsb(this));
	}

	/**
	 * add new {@link EsbProject}
	 * 
	 * @param esbProject
	 */
	public final void addEsbProject(EsbProject esbProject) {
		EsbServiceNode esbServiceNode = new EsbServiceNode(esbProject);
		treeModel.setRoot(esbServiceNode);
		expandPath(new TreePath(esbServiceNode));
	}

	/**
	 * tree cell renderer for {@link EsbServiceTree}
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class EsbServiceTreeRenederer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof EsbServiceNode) {
				rnd.setIcon(IconFactory.ESB);
			}

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			}

			if (!(value instanceof IconNode)) {
				toString();
			}
			return rnd;
		}
	}

	@Override
	public final void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof BpelOperations) {
				enableMenuItem(MenuItems.FIND_USAGE_BPEL.getActionCmd(), true);
			} else if (treePath.getLastPathComponent() instanceof EsbServiceNode) {
				enableMenuItem(MenuItems.FIND_USAGE_ESB.getActionCmd(), true);
			} else {
				enableMenuItem(null, false);
			}
		}
		popupMenu.show(this, x, y);
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(MenuItems.FIND_USAGE_BPEL.getActionCmd())) {
			BpelOperations bpelOperations = (BpelOperations) getSelectionPath().getLastPathComponent();
			workspaceUtilsPanel.showFindUsageBpelProject(FindUsageProjectResult.createUsageForBpelProject(bpelOperations));
		} else if (e.getActionCommand().equals(MenuItems.FIND_USAGE_ESB.getActionCmd())) {
			EsbServiceNode esbServiceNode = (EsbServiceNode) getSelectionPath().getLastPathComponent();
			workspaceUtilsPanel.showFindUsageEsbProject(FindUsageProjectResult.createUsageForEsbProject(esbServiceNode));
		}
	}
}
