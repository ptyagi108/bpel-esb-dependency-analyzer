package com.tomecode.soa.bpel.dependency.analyzer.gui.panels;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.UsageTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsage;
import com.tomecode.soa.bpel.dependency.analyzer.usages.Usage;

/**
 * 
 * panel show usage for variables, partnerLinks, projects ...etc
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class UsageTreePanel extends JPanel {

	private static final long serialVersionUID = 2752156559028598059L;

	private final UsageTree basicTree;

	private final FindUsageTreeRenderer rendererTree;

	/**
	 * Constructor
	 * 
	 * @param result
	 */
	public UsageTreePanel() {
		setLayout(new BorderLayout());
		basicTree = new UsageTree();
		rendererTree = new FindUsageTreeRenderer();
		basicTree.setCellRenderer(rendererTree);
		add(new JScrollPane(basicTree), BorderLayout.CENTER);
	}

	/**
	 * Set new data
	 * 
	 * @param result
	 */
	public final void addData(FindUsage result) {
		basicTree.addData(result);
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class FindUsageTreeRenderer implements TreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			} else if (value instanceof Usage) {
				Usage usage = (Usage) value;
				ImageIcon icon = null;
				if (usage.getActivity() != null) {
					icon = usage.getActivity().getActivtyType().getImageIcon();
				} else if (usage.getProject() != null) {
					icon = usage.getProject().getIcon();
				}
				if (icon != null) {
					rnd.setIcon(icon);
				}
			}

			return rnd;
		}
	}

}
