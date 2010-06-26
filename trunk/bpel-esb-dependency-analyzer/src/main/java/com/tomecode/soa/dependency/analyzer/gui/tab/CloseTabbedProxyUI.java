package com.tomecode.soa.dependency.analyzer.gui.tab;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.accessibility.Accessible;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;

import com.sun.java.swing.plaf.windows.WindowsIconFactory;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class CloseTabbedProxyUI extends CloseTabbedPaneUI {

	private final TabbedPaneUI delegateTabbedPanelUI;
	private boolean closeEnabled = true;

	/**
	 * image for button close
	 */
	private final BufferedImage imgButtonClose;
	/**
	 * close button
	 */
	private final JButton buttonClose;

	protected static final int BUTTONSIZE = 15;

	/**
	 * Construcotr
	 * 
	 * @param d
	 */
	public CloseTabbedProxyUI(TabbedPaneUI ui) {
		super();
		delegateTabbedPanelUI = ui;
		imgButtonClose = new BufferedImage(BUTTONSIZE, BUTTONSIZE, BufferedImage.TYPE_4BYTE_ABGR);
		buttonClose = new JButton();
		buttonClose.setSize(BUTTONSIZE, BUTTONSIZE);
		WindowsIconFactory.createFrameCloseIcon().paintIcon(buttonClose, imgButtonClose.createGraphics(), 0, 0);
	}

	public final boolean contains(JComponent c, int x, int y) {
		return delegateTabbedPanelUI.contains(c, x, y);
	}

	public final Accessible getAccessibleChild(JComponent c, int i) {
		return delegateTabbedPanelUI.getAccessibleChild(c, i);
	}

	public final int getAccessibleChildrenCount(JComponent c) {
		return delegateTabbedPanelUI.getAccessibleChildrenCount(c);
	}

	public final Dimension getMaximumSize(JComponent c) {
		return delegateTabbedPanelUI.getMaximumSize(c);
	}

	public final Dimension getMinimumSize(JComponent c) {
		return delegateTabbedPanelUI.getMinimumSize(c);
	}

	public final Dimension getPreferredSize(JComponent c) {
		return delegateTabbedPanelUI.getPreferredSize(c);
	}

	public final Rectangle getTabBounds(JTabbedPane pane, int index) {
		Rectangle r = new Rectangle(delegateTabbedPanelUI.getTabBounds(pane, index));
		r.width += 150;// BUTTONSIZE;
		// System.out.println(r.width);
		return r;
	}

	public final int getTabRunCount(JTabbedPane pane) {
		return delegateTabbedPanelUI.getTabRunCount(pane);
	}

	public final void update(Graphics g, JComponent c) {
		// System.out.println("update");
		delegateTabbedPanelUI.update(g, c);
	}

	public final boolean isCloseEnabled() {
		return closeEnabled;
	}

	public final void setCloseEnabled(boolean closeEnabled) {
		this.closeEnabled = closeEnabled;
	}

	public final void setCloseIcon(boolean b) {
		setCloseEnabled(b);
	}

	public final void installUI(JComponent c) {
		// System.out.println("installUI");
		delegateTabbedPanelUI.installUI(c);
	}

	public final void paint(Graphics g, JComponent c) {
		// System.out.println("Paint");
		delegateTabbedPanelUI.paint(g, c);
	}

	public final int tabForCoordinate(JTabbedPane pane, int x, int y) {
		// System.out.println("tabForCoordinate");
		return delegateTabbedPanelUI.tabForCoordinate(pane, x, y);
	}

	public final void uninstallUI(JComponent c) {
		delegateTabbedPanelUI.uninstallUI(c);
	}

}
