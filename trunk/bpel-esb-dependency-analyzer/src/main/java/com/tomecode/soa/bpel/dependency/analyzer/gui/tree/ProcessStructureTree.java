package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.panels.WorkspaceUtilsPanel;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;
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

	/**
	 * simple popup menu
	 */
	private final JPopupMenu popupMenu;

	private final List<Activity> activities;

	private WorkspaceUtilsPanel workspaceUtilsPanel;

	/**
	 * Constructor
	 */
	public ProcessStructureTree(WorkspaceUtilsPanel workspaceUtilsPanel) {
		super();
		this.workspaceUtilsPanel = workspaceUtilsPanel;
		activities = new ArrayList<Activity>();
		treePathRenderer = new SelectTreePathRenderer();
		popupMenu = new JPopupMenu();
		popupMenu.add(createMenuItem("Find Usage for Variable", "findUsageVariable"));
		popupMenu.add(createMenuItem("Find Usage for PartnerLink", "findUsagePartnerLink"));
		setCellRenderer(treePathRenderer);

		addMouseListener(new MouseAdapter() {
			public final void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					showPopupMenu(e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * show {@link #popupMenu}
	 * 
	 * @param x
	 * @param y
	 */
	private final void showPopupMenu(int x, int y) {
		TreePath treePath = this.getSelectionPath();
		if (treePath != null) {
			if (treePath.getLastPathComponent() instanceof Variable) {
				enableMenuItem("findUsageVariable", true);
			} else if (treePath.getLastPathComponent() instanceof PartnerLink) {
				enableMenuItem("findUsagePartnerLink", true);
			} else {
				enableMenuItem(null, false);
			}
			popupMenu.show(this, x, y);
		}
	}

	/**
	 * enable/disable menu item in {@link #popupMenu}
	 * 
	 * @param actionCmd
	 * @param enable
	 */
	private final void enableMenuItem(String actionCmd, boolean enable) {
		for (int i = 0; i <= popupMenu.getComponentCount() - 1; i++) {
			if (popupMenu.getComponent(i) instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(i);
				if (actionCmd == null) {
					menuItem.setEnabled(enable);
				} else {
					if (actionCmd.equals(menuItem.getActionCommand())) {
						menuItem.setEnabled(enable);
					} else {
						menuItem.setEnabled(false);
					}
				}
			}
		}
	}

	private final JMenuItem createMenuItem(String title, String actionCmd) {
		JMenuItem item = new JMenuItem(title);
		item.setActionCommand(actionCmd);
		item.addActionListener(this);
		return item;
	}

	/**
	 * display select process in {@link WorkspaceTree}
	 * 
	 * @param bpelProcessStrukture
	 */
	public final void addBpelProcessStrukture(BpelProcessStrukture bpelProcessStrukture) {
		treeModel.setRoot(bpelProcessStrukture);
		expandAllNodes(new TreePath(bpelProcessStrukture));
	}

	public final void addSelectedActivities(List<Activity> activities) {
		this.activities.clear();
		this.activities.addAll(activities);
		updateUI();
	}

	public final void clearSelectedOpertiaons() {
		activities.clear();
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
				if (!activities.isEmpty()) {

					if (activities.get(0).toString().equals(activity.toString())) {
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
			for (Activity activity : activities) {
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

				activityInTree = activityInTree.getParentActivity();
			}

			return true;
		}
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if ("findUsageVariable".equals(e.getActionCommand())) {
			Variable variable = (Variable) getSelectionPath().getLastPathComponent();
			FindUsageVariableResult result = new FindUsageVariableResult(variable);

			if (variable.getParentActivity() != null && variable.getParentActivity().getParentActivity() != null) {
				variable.getParentActivity().getParentActivity().findUsage(result);
			}
			workspaceUtilsPanel.showFindUsageVariable(result);
		} else if ("findUsagePartnerLink".equals(e.getActionCommand())) {
			PartnerLink partnerLink = (PartnerLink) getSelectionPath().getLastPathComponent();

			FindUsagePartnerLinkResult result = new FindUsagePartnerLinkResult(partnerLink);

			if (partnerLink.getParentActivity() != null && partnerLink.getParentActivity().getParentActivity() != null) {
				partnerLink.getParentActivity().getParentActivity().findUsage(result);
			}

			workspaceUtilsPanel.showFindUsagePartnerLink(result);
		}
	}
}
