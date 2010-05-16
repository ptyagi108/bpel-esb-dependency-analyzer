package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.esb.EsbProject;

/**
 * 
 * show esb project services
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectEsbServiceTree extends BasicTree {

	private static final long serialVersionUID = -6785404093212997928L;

	/**
	 * Constructor
	 */
	public ProjectEsbServiceTree() {
		super();
		setCellRenderer(new EsbServiceTreeRenederer());

	}

	/**
	 * add new {@link EsbProject}
	 * 
	 * @param esbProject
	 */
	public final void addEsbProject(EsbProject esbProject) {
		EsbServiceNode esbServiceNode = new EsbServiceNode(esbProject);
		treeModel.setRoot(esbServiceNode);
		expandPath(new TreePath(esbServiceNode));
	}

	/**
	 * custom renderen for esb services tree
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class EsbServiceTreeRenederer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof EsbServiceNode) {
				rnd.setIcon(IconFactory.ESB);
			}

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			}

			if (!(value instanceof IconNode)) {
				toString();
			}
			return rnd;
		}
	}

	@Override
	public void showPopupMenu(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
