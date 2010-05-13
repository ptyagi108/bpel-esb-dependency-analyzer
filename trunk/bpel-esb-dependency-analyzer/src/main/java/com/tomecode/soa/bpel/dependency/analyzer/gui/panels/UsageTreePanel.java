package com.tomecode.soa.bpel.dependency.analyzer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.BasicTree;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsage;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class UsageTreePanel extends JPanel {

	private static final long serialVersionUID = 2752156559028598059L;

	private BasicTree basicTree;

	public UsageTreePanel(FindUsage result) {
		setLayout(new BorderLayout());
		basicTree = new BasicTree();
		add(new JScrollPane(basicTree), BorderLayout.CENTER);
		basicTree.addData(result);
	}

	public final void addData(FindUsage result) {
		basicTree.addData(result);
	}
}
