package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.esb.EsbGrp;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.oracle10g.esb.EsbSys;

/**
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectEsbServiceTree extends BasicTree {

	private static final long serialVersionUID = -6785404093212997928L;

	public ProjectEsbServiceTree() {
		super();
		setCellRenderer(new EsbServiceTreeRenederer());

	}

	public final void addEsbProject(EsbProject esbProject) {
		EsbServiceNode esbServiceNode = new EsbServiceNode(esbProject);
		treeModel.setRoot(esbServiceNode);
		expandPath(new TreePath(esbServiceNode));
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class EsbServiceTreeRenederer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof EsbServiceNode) {
				rnd.setIcon(IconFactory.ESB);
			} else if (value instanceof EsbSvc) {
				rnd.setIcon(IconFactory.SERVICE);
			} else if (value instanceof EsbSys) {
				rnd.setIcon(IconFactory.SYSTEM);
			} else if (value instanceof EsbGrp) {
				rnd.setIcon(IconFactory.SERVICE_GROUPE);
			}

			return rnd;
		}
	}
}
