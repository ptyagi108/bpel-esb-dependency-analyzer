package com.tomecode.soa.dependency.analyzer.gui.tree;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreePath;

import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory;
import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory.MenuItems;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.dependency.analyzer.gui.utils.panels.UtilsPanel;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;

/**
 * 
 * Display operation from selected proces in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelOperationTree extends BasicTree {

	private static final long serialVersionUID = -3750125284965106516L;

	private final UtilsPanel workspaceUtilsPanel;

	/**
	 * 
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	public BpelOperationTree(UtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		popupMenu.add(MenuFactory.createFindUsageBpel(this));
		popupMenu.add(MenuFactory.createFindUsageEsb(this));
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

	@Override
	public final void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();

		if (treePath != null) {
			enableMenuItem(null, false);

			if (treePath.getLastPathComponent() instanceof BpelOperations) {
				enableMenuItem(MenuItems.FIND_USAGE_BPEL.getActionCmd(), true);
			} else if (treePath.getLastPathComponent() instanceof EsbServiceNode) {
				enableMenuItem(MenuItems.FIND_USAGE_ESB.getActionCmd(), true);
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
