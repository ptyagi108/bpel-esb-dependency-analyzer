package com.tomecode.soa.bpel.dependency.analyzer.gui.panels;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.BasicTree;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsage;
import com.tomecode.soa.bpel.dependency.analyzer.utils.Usage;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class UsageTreePanel extends JPanel {

	private static final long serialVersionUID = 2752156559028598059L;

	private final BasicTree basicTree;

	private final FindUsageTreeRenderer rendererTree;

	/**
	 * Constructor
	 * 
	 * @param result
	 */
	public UsageTreePanel(FindUsage result) {
		setLayout(new BorderLayout());
		basicTree = new BasicTree();
		rendererTree = new FindUsageTreeRenderer();
		basicTree.setCellRenderer(rendererTree);
		add(new JScrollPane(basicTree), BorderLayout.CENTER);
		basicTree.addData(result);
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

			if (value instanceof Usage) {
				Usage usage = (Usage) value;
				ImageIcon icon = usage.getActivity().getActivtyType().getImageIcon();
				if (icon != null) {
					rnd.setIcon(icon);
				}
			}

			return rnd;
		}
	}

}
