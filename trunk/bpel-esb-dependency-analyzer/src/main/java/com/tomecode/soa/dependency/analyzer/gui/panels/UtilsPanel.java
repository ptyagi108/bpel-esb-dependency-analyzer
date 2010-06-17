package com.tomecode.soa.dependency.analyzer.gui.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

import com.tomecode.soa.dependency.analyzer.gui.components.WorkspacePanel;
import com.tomecode.soa.dependency.analyzer.usages.FindUsage;
import com.tomecode.soa.dependency.analyzer.usages.FindUsagePartnerLinkResult;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageProjectResult;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageVariableResult;

/**
 * 
 * Simple {@link JTabbedPane} for display utilities
 * 
 * @author Tomas Frastia
 * 
 */
public final class UtilsPanel extends JTabbedPane {

	private static final long serialVersionUID = -8171342255621764543L;

	private UtilsPanelListener utilsPanelListener;

	/**
	 * Constructor
	 */
	public UtilsPanel() {
		addMouseListener(new MouseAdapter() {
			public final void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
					if (getSelectedIndex() != -1) {
						removeTabAt(getSelectedIndex());
						if (getTabCount() == 0) {
							utilsPanelListener.hide();
						}
					}
				}
			}
		});
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
	public final void showFindUsageBpelProject(FindUsageProjectResult usage) {
		addData(usage, "BPEL project is used");
	}

	/**
	 * show tab - "ESB project is used" - and add usage data
	 * 
	 * @param usage
	 */
	public final void showFindUsageEsbProject(FindUsageProjectResult usage) {
		addData(usage, "ESB project is used");
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
			utilsPanelListener.show();
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

	public final void setListener(UtilsPanelListener utilsPanelListener) {
		this.utilsPanelListener = utilsPanelListener;
	}

	/**
	 * 
	 * simple interface for notifi {@link WorkspacePanel} for display/hide
	 * {@link UtilsPanel}
	 * 
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static interface UtilsPanelListener {

		/**
		 * dispaly {@link UtilsPanel} in {@link WorkspacePanel}
		 */
		void show();

		/**
		 * hide {@link UtilsPanel} in {@link WorkspacePanel}
		 */
		void hide();
	}
}
