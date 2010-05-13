package com.tomecode.soa.bpel.dependency.analyzer.gui.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.process.Project;

/**
 * Display all BPEL/ESB projects in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceTree extends BasicTree {

	private static final long serialVersionUID = -14952772269846358L;

	/**
	 * Constructor
	 */
	public WorkspaceTree() {
		super();
		setRootVisible(false);
		setCellRenderer(new WorkspaceTreeRenderer());
	}

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspaceTree(MultiWorkspace multiWorkspace) {
		this();
		treeModel.setRoot(multiWorkspace);
	}

	/**
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	private final class WorkspaceTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof BpelProject) {
				rnd.setIcon(IconFactory.PROCESS);
			} else if (value instanceof EsbProject) {
				rnd.setIcon(IconFactory.ESB);
			}
			if (value instanceof Project) {
				if (!((Project) value).isInJws()) {
					rnd.setForeground(Color.BLACK);
					rnd.setFont(rnd.getFont().deriveFont(Font.BOLD));

				}
			}

			return rnd;
		}
	}
}
