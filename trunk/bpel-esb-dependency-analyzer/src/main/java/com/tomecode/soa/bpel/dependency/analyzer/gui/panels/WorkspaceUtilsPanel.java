package com.tomecode.soa.bpel.dependency.analyzer.gui.panels;

import javax.swing.JTabbedPane;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
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

	public void showFindUsageVariable(FindUsageVariableResult result) {
		int index = findTabIndexByTitle("Variable is used");
		UsageTreePanel panel = null;
		if (index == -1) {
			panel = new UsageTreePanel(result);
			addTab("Variable is used", panel);
			setSelectedIndex(getTabCount() - 1);
		} else {
			panel = (UsageTreePanel) getComponent(index);
			panel.addData(result);
			setSelectedIndex(index);
		}
		updateUI();
	}

	public void addServiceUsedIn() {
		// TODO Auto-generated method stub

	}

	public final void showFindUsagePartnerLink(FindUsagePartnerLinkResult findUsagePartnerLinkResult) {
		int index = findTabIndexByTitle("PartnerLink is used");
		UsageTreePanel panel = null;
		if (index == -1) {
			panel = new UsageTreePanel(findUsagePartnerLinkResult);
			addTab("PartnerLink is used", panel);
			setSelectedIndex(getTabCount() - 1);
		} else {
			panel = (UsageTreePanel) getComponent(index);
			panel.addData(findUsagePartnerLinkResult);
			setSelectedIndex(index);
		}
		updateUI();
	}
}
