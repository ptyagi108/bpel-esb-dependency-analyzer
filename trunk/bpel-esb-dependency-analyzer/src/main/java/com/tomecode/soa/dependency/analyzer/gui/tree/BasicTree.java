package com.tomecode.soa.dependency.analyzer.gui.tree;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;

/**
 * Basic tree
 * 
 * @author Tomas Frastia
 * 
 */
public abstract class BasicTree extends JTree implements ActionListener {

	private static final long serialVersionUID = 2719679725572727785L;
	/**
	 * default tree model
	 */
	protected final DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("root"));

	protected JPopupMenu popupMenu;

	public BasicTree() {
		setModel(treeModel);
		treeModel.setRoot(null);
		setCellRenderer(new IconTreeRenderer());
		popupMenu = new JPopupMenu();
		addMouseListener(new MouseAdapter() {
			public final void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					showPopupMenu(e.getX(), e.getY());
				}
			}
		});
	}

	public abstract void showPopupMenu(int x, int y);

	/**
	 * 
	 * create {@link JMenuItem}
	 * 
	 * @param title
	 * @param actionCmd
	 * @return
	 */
	protected final void createMenuItem(String title, String actionCmd, Icon icon) {
		JMenuItem item = new JMenuItem(title);
		item.setActionCommand(actionCmd);
		if (icon != null) {
			item.setIcon(icon);
		}
		item.addActionListener(this);
		popupMenu.add(item);
	}

	/**
	 * expand all nodes
	 * 
	 * @param parent
	 */
	protected final void expandAllNodes(TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (int i = 0; i <= node.getChildCount() - 1; i++) {
				TreePath path = parent.pathByAddingChild(node.getChildAt(i));
				expandAllNodes(path);
			}
		}
		expandPath(parent);
	}

	/**
	 * reset content the tree
	 * 
	 */
	public final void clear() {
		treeModel.setRoot(null);

	}

	public final void addData(TreeNode node) {
		treeModel.setRoot(node);
		expandAllNodes(new TreePath(node));
	}

	/**
	 * enable/disable menu item in {@link #popupMenu}
	 * 
	 * @param actionCmd
	 * @param enable
	 */
	protected final void enableMenuItem(String actionCmd, boolean enable) {
		for (int i = 0; i <= popupMenu.getComponentCount() - 1; i++) {
			if (popupMenu.getComponent(i) instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem) popupMenu.getComponent(i);
				if (actionCmd == null) {
					menuItem.setEnabled(enable);
				} else {
					if (actionCmd.equals(menuItem.getActionCommand())) {
						menuItem.setEnabled(enable);
					}
				}
			}
		}
	}

	/**
	 * Icon renderer
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public final class IconTreeRenderer implements TreeCellRenderer {

		public final Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			DefaultTreeCellRenderer rnd = (DefaultTreeCellRenderer) new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof IconNode) {
				rnd.setIcon(((IconNode) value).getIcon());
			}

			return rnd;
		}
	}
}
