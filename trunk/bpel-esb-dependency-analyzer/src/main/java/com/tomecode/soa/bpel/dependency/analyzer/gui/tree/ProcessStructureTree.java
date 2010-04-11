package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.oracle10g.bpel.Activity;
import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;

/**
 * 
 * Display structure of selected process in {@link WorkspaceTree}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProcessStructureTree extends BasicTree {

	private static final long serialVersionUID = 1258506152291476233L;

	private final SelectTreePathRenderer treePathRenderer;

	private final List<Activity> activities;

	/**
	 * Constructor
	 */
	public ProcessStructureTree() {
		super();
		activities = new ArrayList<Activity>();
		treePathRenderer = new SelectTreePathRenderer();
		setCellRenderer(treePathRenderer);
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

			if (value instanceof Activity) {
				Activity activity = (Activity) value;

				for (Activity selectActivity : activities) {
					if (selectActivity.toString().equals(activity.toString())) {
						rnd.setForeground(Color.red);
					}
				}

				if (activity.getActivtyType() != null) {
					rnd.setIcon(activity.getActivtyType().getImageIcon());
				}

			}

			// if (value instanceof Operation) {
			//
			// BpelProcess bpelProcess = (BpelProcess) value;
			// if (bpelProcess.getParent() != null) {
			//
			// // if (rootBpelProcess.equals(bpelProcess)) {
			// rnd.setForeground(Color.red);
			// } else {
			// rnd.setForeground(Color.black);
			// }
			// rnd.setText(value.toString());
			//
			// }

			return rnd;
		}
	}

}
