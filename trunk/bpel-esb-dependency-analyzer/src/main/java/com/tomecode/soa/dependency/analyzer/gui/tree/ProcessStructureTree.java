package com.tomecode.soa.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory;
import com.tomecode.soa.dependency.analyzer.gui.menu.MenuFactory.MenuItems;
import com.tomecode.soa.dependency.analyzer.gui.panels.UtilsPanel;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.usages.FindUsagePartnerLinkResult;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageVariableResult;
import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;
import com.tomecode.soa.oracle10g.bpel.activity.Activity;
import com.tomecode.soa.oracle10g.bpel.activity.PartnerLink;
import com.tomecode.soa.oracle10g.bpel.activity.Variable;

/**
 * 
 * Display structure of selected process in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProcessStructureTree extends BasicTree implements ActionListener {

	private static final long serialVersionUID = 1258506152291476233L;

	private final SelectTreePathRenderer treePathRenderer;

	private final List<Activity> selectedActivityWithOperationPath;

	private UtilsPanel workspaceUtilsPanel;

	/**
	 * 
	 * Constructor
	 * 
	 * @param workspaceUtilsPanel
	 */
	public ProcessStructureTree(UtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		selectedActivityWithOperationPath = new ArrayList<Activity>();
		treePathRenderer = new SelectTreePathRenderer();

		popupMenu.add(MenuFactory.createJMenuItem(MenuItems.FIND_USAGE_VARIABLE, this));
		popupMenu.add(MenuFactory.createJMenuItem(MenuItems.FIND_USAGE_PARTNERLINK, this));

		setCellRenderer(treePathRenderer);

	}

	/**
	 * show {@link #popupMenu}
	 * 
	 * @param x
	 * @param y
	 */
	public final void showPopupMenu(int x, int y) {
		enableMenuItem(null, false);
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof Variable) {
				enableMenuItem(MenuItems.FIND_USAGE_VARIABLE.getActionCmd(), true);
			} else if (treePath.getLastPathComponent() instanceof PartnerLink) {
				enableMenuItem(MenuItems.FIND_USAGE_PARTNERLINK.getActionCmd(), true);
			}
			popupMenu.show(this, x, y);
		}
	}

	/**
	 * display select process in {@link WorkspaceTree}
	 * 
	 * @param bpelProcessStrukture
	 */
	public final void addBpelProcessStrukture(BpelProcessStrukture bpelProcessStrukture) {
		selectedActivityWithOperationPath.clear();
		treeModel.setRoot(bpelProcessStrukture);
		expandAllNodes(new TreePath(bpelProcessStrukture));
	}

	public final void setSelectedActivities(List<Activity> activities) {
		this.selectedActivityWithOperationPath.clear();
		this.selectedActivityWithOperationPath.addAll(activities);
		updateUI();
	}

	public final void addSelectedActivities(List<Activity> activities) {
		this.selectedActivityWithOperationPath.addAll(activities);
		updateUI();
	}

	public final void clearSelectedOperationsTreePath() {
		selectedActivityWithOperationPath.clear();
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class SelectTreePathRenderer implements TreeCellRenderer {

		private static final long serialVersionUID = -6314674946997479687L;

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof BpelProcessStrukture) {
				rnd.setIcon(IconFactory.PROCESS);
			} else if (value instanceof Activity) {

				Activity activity = (Activity) value;
				if (!selectedActivityWithOperationPath.isEmpty()) {
					// compare selected last activty by name and operation
					if (selectedActivityWithOperationPath.get(0).compare(activity)) {
						if (compareByTreeActivities(activity)) {
							rnd.setForeground(Color.red);
							rnd.setFont(rnd.getFont().deriveFont(Font.BOLD));
						}
					}
				}

				if (activity.getActivtyType() != null) {
					rnd.setIcon(activity.getActivtyType().getImageIcon());
				}
			}
			rnd.setFocusable(true);
			return rnd;

		}

		/**
		 * compare by tree path
		 * 
		 * @param activityInTree
		 * @return
		 */
		private final boolean compareByTreeActivities(Activity activityInTree) {
			for (Activity activity : selectedActivityWithOperationPath) {
				if (activityInTree == null) {
					return false;
				}
				if (activity.getName() == null && activity.getActivtyType() == null) {
					if (!activity.toString().equals(activityInTree.toString())) {
						return false;
					}
				} else if (activity.getName() == null) {
					if (!activity.getActivtyType().equals(activityInTree.getActivtyType())) {
						return false;
					}
				} else {
					if (!activity.getName().equals(activityInTree.getName()) || !activity.getActivtyType().equals(activityInTree.getActivtyType())) {
						return false;
					}
				}

				activityInTree = activityInTree.getParent();
			}

			return true;
		}

	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (MenuItems.FIND_USAGE_VARIABLE.getActionCmd().equals(e.getActionCommand())) {
			Variable variable = (Variable) getSelectionPath().getLastPathComponent();
			FindUsageVariableResult result = new FindUsageVariableResult(variable);

			if (variable.getParent() != null && variable.getParent().getParent() != null) {
				variable.getParent().getParent().findUsage(result);
			}
			workspaceUtilsPanel.showFindUsageVariable(result);
		} else if (MenuItems.FIND_USAGE_PARTNERLINK.getActionCmd().equals(e.getActionCommand())) {
			PartnerLink partnerLink = (PartnerLink) getSelectionPath().getLastPathComponent();

			FindUsagePartnerLinkResult result = new FindUsagePartnerLinkResult(partnerLink);

			if (partnerLink.getParent() != null && partnerLink.getParent().getParent() != null) {
				partnerLink.getParent().getParent().findUsage(result);
			}

			workspaceUtilsPanel.showFindUsagePartnerLink(result);
		}
	}
}
