package com.tomecode.soa.bpel.dependency.analyzer.gui.panels;

import javax.swing.JTabbedPane;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsage;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageBpelProjectResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * 
 * Simple {@link JTabbedPane} for display utilities
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceUtilsPanel extends JTabbedPane {

	private static final long serialVersionUID = -8171342255621764543L;

	/**
	 * Constructor
	 */
	public WorkspaceUtilsPanel() {

	}

	/**
	 * find index by tab title
	 * 
	 * @param title
	 * @return
	 */
	private final int findTabIndexByTitle(String title) {
		for (int i = 0; i <= getTabCount() - 1; i++) {
			if (getTitleAt(i).equals(title)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * show tab - "Variable is used" - and add usage data
	 * 
	 * @param usage
	 */
	public void showFindUsageVariable(FindUsageVariableResult usage) {
		addData(usage, "Variable is used");
	}

	/**
	 * show tab - "PartnerLink is used" - and add usage data
	 * 
	 * @param usage
	 */
	public final void showFindUsagePartnerLink(FindUsagePartnerLinkResult usage) {
		addData(usage, "PartnerLink is used");
	}

	/**
	 * show tab - "BPEL project is used" - and add usage data
	 * 
	 * @param usage
	 */
	public final void showFindUsageBpelProject(FindUsageBpelProjectResult usage) {
		addData(usage, "BPEL project is used");
	}

	/**
	 * find table by title and add new usage
	 * 
	 * @param usage
	 * @param title
	 */
	private final void addData(FindUsage usage, String title) {
		UsageTreePanel panel = findUsageTreePanel(title);
		if (panel != null) {
			panel.addData(usage);
		}
		updateUI();
	}

	/**
	 * find {@link UsageTreePanel} in tabs by title
	 * 
	 * @param title
	 * @return
	 */
	private final UsageTreePanel findUsageTreePanel(String title) {
		int index = findTabIndexByTitle(title);
		UsageTreePanel panel = null;
		if (index == -1) {
			panel = new UsageTreePanel();
			addTab(title, panel);
			setSelectedIndex(getTabCount() - 1);
		} else {
			panel = (UsageTreePanel) getComponent(index);
			setSelectedIndex(index);
		}

		return panel;
	}
}
