package com.tomecode.soa.dependency.analyzer.gui.menu;

import java.awt.MenuItem;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.tomecode.soa.dependency.analyzer.icons.IconFactory;

/**
 * 
 * Helper class for easy creating of menu items
 * 
 * @author Frastia Tomas
 * 
 */
public final class MenuFactory {

	/**
	 * create {@link JMenuItem} by {@link MenuItems#ARROW_BACK}
	 * 
	 * @param actionListener
	 * @return
	 */
	public final static JMenuItem createArrowBack(ActionListener actionListener) {
		return createJMenuItem(MenuItems.ARROW_BACK, actionListener);
	}

	/**
	 * create {@link JMenuItem} by {@link MenuItems#ARROW_FORWARD}
	 * 
	 * @param actionListener
	 * @return
	 */
	public final static JMenuItem createArrowForward(ActionListener actionListener) {
		return createJMenuItem(MenuItems.ARROW_FORWARD, actionListener);
	}

	/**
	 * create {@link JMenuItem} by {@link MenuItems#FIND_USAGE_BPEL}
	 * 
	 * @param actionListener
	 * @return
	 */
	public final static JMenuItem createFindUsageBpel(ActionListener actionListener) {
		return createJMenuItem(MenuItems.FIND_USAGE_BPEL.getTitle(), MenuItems.FIND_USAGE_BPEL.getActionCmd(), MenuItems.FIND_USAGE_BPEL.getImageIcon(), actionListener);
	}

	/**
	 * create {@link JMenuItem} by {@link MenuItems#FIND_USAGE_ESB}
	 * 
	 * @param actionListener
	 * @return
	 */
	public final static JMenuItem createFindUsageEsb(ActionListener actionListener) {
		return createJMenuItem(MenuItems.FIND_USAGE_ESB.getTitle(), MenuItems.FIND_USAGE_ESB.getActionCmd(), MenuItems.FIND_USAGE_ESB.getImageIcon(), actionListener);
	}

	/**
	 * create {@link JMenuItem} by {@link MenuItems#PROJECT_PROPERTIES}
	 * 
	 * @param actionListener
	 * @return
	 */
	public final static JMenuItem createProjectProperties(ActionListener actionListener) {
		return createJMenuItem(MenuItems.PROJECT_PROPERTIES.getTitle(), MenuItems.PROJECT_PROPERTIES.getActionCmd(), MenuItems.PROJECT_PROPERTIES.getImageIcon(), actionListener);
	}

	/**
	 * Create new {@link JMenuItem}
	 * 
	 * @param title
	 *            menu item title
	 * @param actionCmd
	 *            menu action command
	 * @param icon
	 *            icon for menu
	 * @param actionListener
	 *            reference for {@link ActionListener}
	 * @return new {@link JMenuItem}
	 */
	public final static JMenuItem createJMenuItem(String title, String actionCmd, Icon icon, ActionListener actionListener) {
		JMenuItem item = new JMenuItem(title);
		item.setActionCommand(actionCmd);
		if (icon != null) {
			item.setIcon(icon);
		}
		item.addActionListener(actionListener);
		return item;
	}

	/**
	 * create new {@link JMenuItem} by {@link MenuItems}
	 * 
	 * @param menuItems
	 *            selected {@link MenuItems}
	 * @param actionListener
	 *            reference for {@link ActionListener}
	 * @return new {@link JMenuItem}
	 */
	public final static JMenuItem createJMenuItem(MenuItems menuItems, ActionListener actionListener) {
		return createJMenuItem(menuItems.getTitle(), menuItems.getActionCmd(), menuItems.getImageIcon(), actionListener);
	}

	/**
	 * enable/disable {@link MenuItem} in {@link JPopupMenu}
	 * 
	 * @param popupMenu
	 *            target {@link JPopupMenu} which contains affected menuitem
	 * 
	 * @param actionCmd
	 *            identificator {@link MenuItem}
	 * @param enable
	 *            true/false
	 */
	public static final void enableMenuItem(JPopupMenu popupMenu, String actionCmd, boolean enable) {
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
	 * Enum contains tile and actionCommand for menu items. This is just a
	 * helper for better creating (@ link JMenuItem)
	 * 
	 * @author Frastia Tomas
	 * 
	 */
	public static enum MenuItems {

		ARROW_BACK("Back", "arrowBack", IconFactory.ARROW_BACK), ARROW_FORWARD("Forward", "arrowForward", IconFactory.ARROW_FORWARD), RELOAD_GRAPH("Reload", "reloadGraph", IconFactory.RELOAD_GRAPH), FIND_USAGE_BPEL("Find Usage for BPEL projec", "findUsageBpelProject",
				IconFactory.SEARCH), FIND_USAGE_ESB("Find Usage for ESB project", "findUsageESBproject", IconFactory.SEARCH), PROJECT_PROPERTIES("Properties...", "infoAboutProject", IconFactory.ABOUT), FIND_USAGE_VARIABLE("Find Usage for Variable", "findUsageVariable",
				IconFactory.SEARCH), FIND_USAGE_PARTNERLINK("Find Usage for PartnerLink", "findUsagePartnerLink", IconFactory.SEARCH);
		private final String title;
		private final String actionCmd;
		private final ImageIcon imageIcon;

		/**
		 * Constructor
		 * 
		 * @param title
		 *            menu title
		 * @param actionCmd
		 *            menu action command
		 * @param imageIcon
		 *            icon for menu
		 */
		private MenuItems(String title, String actionCmd, ImageIcon imageIcon) {
			this.title = title;
			this.actionCmd = actionCmd;
			this.imageIcon = imageIcon;
		}

		public final ImageIcon getImageIcon() {
			return imageIcon;
		}

		public final String getTitle() {
			return title;
		}

		public final String getActionCmd() {
			return actionCmd;
		}

		public final String toString() {
			return title;
		}
	}
}
