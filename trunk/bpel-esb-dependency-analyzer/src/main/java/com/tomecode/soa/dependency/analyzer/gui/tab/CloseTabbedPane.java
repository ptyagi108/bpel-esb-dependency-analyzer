package com.tomecode.soa.dependency.analyzer.gui.tab;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.EventListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.TabbedPaneUI;

import com.tomecode.soa.dependency.analyzer.gui.panels.WorkspaceChangeListener;
import com.tomecode.soa.dependency.analyzer.gui.panels.WorkspacePanel;
import com.tomecode.soa.dependency.analyzer.gui.tab.event.DoubleClickListener;
import com.tomecode.soa.dependency.analyzer.gui.tab.event.TabbedCloseButtonListener;
import com.tomecode.soa.oracle10g.MultiWorkspace;

/**
 * A JTabbedPane with some added UI functionalities. A close button in every tab
 * 
 * @author Frastia Tomas
 */

public class CloseTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = -2206864887425469931L;

	/**
	 * {@link KeyStroke} - CTRL+TABB
	 */
	private final static KeyStroke ctrlTabKeyStore = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK, true);

	/**
	 * {@link KeyStroke} - CTRL+W
	 */
	private final static KeyStroke ctrlDoubleWKeyStore = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK);

	/**
	 * self refenence
	 */
	private static CloseTabbedPane me;

	private int overTabIndex = -1;

	private CloseTabbedPaneUI paneUI;

	private WorkspaceChangeListener changeListener;

	/**
	 * Creates the <code>CloseAndMaxTabbedPane</code> with an enhanced UI if
	 * <code>enhancedUI</code> parameter is set to <code>true</code>.
	 * 
	 * @param changeListener
	 * 
	 * @param enhancedUI
	 *            whether the tabbedPane should use an enhanced UI
	 */
	public CloseTabbedPane(WorkspaceChangeListener changeListener) {
		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		paneUI = new CloseTabbedPaneEnhancedUI();
		super.setUI(paneUI);
		// me = this;
		this.changeListener = changeListener;

		keysActions();
		initCloseEvents();
	}

	/**
	 * 
	 * registering key actions
	 * 
	 */
	private final void keysActions() {
		// registering ctrl+tab
		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0)));
		InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		im.put(ctrlTabKeyStore, "ctrlTab");
		getActionMap().put("ctrlTab", new AbstractAction("ctrlTab") {

			private static final long serialVersionUID = -4338670465201740592L;

			public final void actionPerformed(ActionEvent e) {
				if (getTabCount() != 0) {
					// ctrl+tab as an action to move to next tab
					if (getSelectedIndex() + 1 >= getTabCount()) {
						setSelectedIndex(0);
					} else {
						setSelectedIndex(getSelectedIndex() + 1);
					}
				}
			}
		});

		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0)));

		im.put(ctrlDoubleWKeyStore, "ctrlW");
		getActionMap().put("ctrlW", new AbstractAction("ctrlW") {

			private static final long serialVersionUID = -4338670465201740592L;

			public final void actionPerformed(ActionEvent e) {
				if (getTabCount() != 0 && getSelectedIndex() != -1) {
					remove(getSelectedIndex());
				}
			}
		});

	}

	/**
	 * init close and double click listeners
	 */
	private final void initCloseEvents() {
		addDoubleClickListener(new DoubleClickListener() {
			@Override
			public final void doubleClickOperation(MouseEvent e) {
				if (getSelectedIndex() != -1) {
					remove(getSelectedIndex());
				}
			}
		});
		addCloseListener(new TabbedCloseButtonListener() {
			@Override
			public final void closeTab(MouseEvent e, int overTabIndex) {
				if (getSelectedIndex() != -1) {
					remove(getSelectedIndex());
				}
			}
		});
	}

	/**
	 * Returns the index of the last tab on which the mouse did an action.
	 */
	public final int getOverTabIndex() {
		return overTabIndex;
	}

	/**
	 * Returns <code>true</code> if the close icon is enabled.
	 */
	public final boolean isCloseEnabled() {
		return paneUI.isCloseEnabled();
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public final void setTabLayoutPolicy(int tabLayoutPolicy) {
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public final void setTabPlacement(int tabPlacement) {
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public final void setUI(TabbedPaneUI ui) {
	}

	/**
	 * Sets whether the tabbedPane should have a close icon or not.
	 * 
	 * @param b
	 *            whether the tabbedPane should have a close icon or not
	 */
	public final void setCloseIcon(boolean b) {
		paneUI.setCloseIcon(b);
	}

	/**
	 * Detaches the <code>index</code> tab in a seperate frame. When the frame
	 * is closed, the tab is automatically reinserted into the tabbedPane.
	 * 
	 * @param index
	 *            index of the tabbedPane to be detached
	 */
	@SuppressWarnings("deprecation")
	public final void detachTab(int index) {

		if (index < 0 || index >= getTabCount())
			return;

		final JFrame frame = new JFrame();

		Window parentWindow = SwingUtilities.windowForComponent(this);

		final int tabIndex = index;
		final JComponent c = (JComponent) getComponentAt(tabIndex);

		final Icon icon = getIconAt(tabIndex);
		final String title = getTitleAt(tabIndex);
		final String toolTip = getToolTipTextAt(tabIndex);
		final Border border = c.getBorder();

		removeTabAt(index);

		c.setPreferredSize(c.getSize());

		frame.setTitle(title);
		frame.getContentPane().add(c);
		frame.setLocation(parentWindow.getLocation());
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.dispose();

				insertTab(title, icon, c, toolTip, Math.min(tabIndex, getTabCount()));

				c.setBorder(border);
				setSelectedComponent(c);
			}

		});

		WindowFocusListener windowFocusListener = new WindowFocusListener() {
			long start;

			long end;

			public void windowGainedFocus(WindowEvent e) {
				start = System.currentTimeMillis();
			}

			public void windowLostFocus(WindowEvent e) {
				end = System.currentTimeMillis();
				long elapsed = end - start;
				// System.out.println(elapsed);
				if (elapsed < 100)
					frame.toFront();

				frame.removeWindowFocusListener(this);
			}
		};

		/*
		 * This is a small hack to avoid Windows GUI bug, that prevent a new
		 * window from stealing focus (without this windowFocusListener, most of
		 * the time the new frame would just blink from foreground to
		 * background). A windowFocusListener is added to the frame, and if the
		 * time between the frame beeing in foreground and the frame beeing in
		 * background is less that 100ms, it just brings the windows to the
		 * front once again. Then it removes the windowFocusListener. Note that
		 * this hack would not be required on Linux or UNIX based systems.
		 */

		frame.addWindowFocusListener(windowFocusListener);

		frame.show();
		frame.toFront();

	}

	/**
	 * Adds a <code>CloseListener</code> to the tabbedPane.
	 * 
	 * @param l
	 *            the <code>CloseListener</code> to add
	 * @see #fireCloseTabEvent
	 * @see #removeCloseListener
	 */
	public final synchronized void addCloseListener(TabbedCloseButtonListener l) {
		listenerList.add(TabbedCloseButtonListener.class, l);
	}

	/**
	 * Adds a <code>DoubleClickListener</code> to the tabbedPane.
	 * 
	 * @param l
	 *            the <code>DoubleClickListener</code> to add
	 * @see #fireDoubleClickTabEvent
	 * @see #removeDoubleClickListener
	 */
	public final synchronized void addDoubleClickListener(DoubleClickListener l) {
		listenerList.add(DoubleClickListener.class, l);
	}

	/**
	 * Removes a <code>CloseListener</code> from this tabbedPane.
	 * 
	 * @param l
	 *            the <code>CloseListener</code> to remove
	 * @see #fireCloseTabEvent
	 * @see #addCloseListener
	 */
	public final synchronized void removeCloseListener(TabbedCloseButtonListener l) {
		listenerList.remove(TabbedCloseButtonListener.class, l);
	}

	/**
	 * Removes a <code>DoubleClickListener</code> from this tabbedPane.
	 * 
	 * @param l
	 *            the <code>DoubleClickListener</code> to remove
	 * @see #fireDoubleClickTabEvent
	 * @see #addDoubleClickListener
	 */
	public final synchronized void removeDoubleClickListener(DoubleClickListener l) {
		listenerList.remove(DoubleClickListener.class, l);
	}

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>CloseListener</code>. The method also updates the
	 * <code>overTabIndex</code> of the tabbedPane with a value coming from the
	 * UI. This method method is called each time a <code>MouseEvent</code> is
	 * received from the UI when the user clicks on the close icon of the tab
	 * which index is <code>overTabIndex</code>.
	 * 
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 * @param overTabIndex
	 *            the index of a tab, usually the tab over which the mouse is
	 * 
	 * @see #addCloseListener
	 * @see EventListenerList
	 */
	public final void fireCloseTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		EventListener closeListeners[] = getListeners(TabbedCloseButtonListener.class);
		for (int i = 0; i < closeListeners.length; i++) {
			((TabbedCloseButtonListener) closeListeners[i]).closeTab(e, overTabIndex);
		}
	}

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>DoubleClickListener</code>. The method also updates the
	 * <code>overTabIndex</code> of the tabbedPane with a value coming from the
	 * UI. This method method is called each time a <code>MouseEvent</code> is
	 * received from the UI when the user double-clicks on the tab which index
	 * is <code>overTabIndex</code>.
	 * 
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 * @param overTabIndex
	 *            the index of a tab, usually the tab over which the mouse is
	 * 
	 * @see #addDoubleClickListener
	 * @see EventListenerList
	 */
	public final void fireDoubleClickTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		EventListener dClickListeners[] = getListeners(DoubleClickListener.class);
		for (int i = 0; i < dClickListeners.length; i++) {
			((DoubleClickListener) dClickListeners[i]).doubleClickOperation(e);
		}
	}

	/**
	 * add new {@link MultiWorkspace} tab
	 * 
	 * @param name
	 * @param multiWorkspace
	 */
	public final void addTable(String name, MultiWorkspace multiWorkspace) {
		addTab(name, new WorkspacePanel(multiWorkspace, changeListener));
		setSelectedIndex(getTabCount() - 1);
	}

	/**
	 * zoom in selected/displayed visual panel
	 */
	public final void zoomInSelectedVisualPanel() {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().zoomIn();
	}

	/**
	 * zoom out selected/displayed visual panel
	 */
	public final void zoomOutSelectedVisualPanel() {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().zoomOut();
	}

	/**
	 * reset zoom
	 */
	public final void zoomResetSelectedVisualPanel() {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().zoomReset();
	}

	/***
	 * export visual graph to file in selected tabb
	 * 
	 * @param file
	 * @throws IOException
	 */
	public final void exportToPng(File file) throws IOException {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().exportToPng(file);
	}

	/**
	 * export visual graph to file in selected tabb
	 * 
	 * @param file
	 * @throws Exception
	 */
	public final void exportToJpg(File file) throws Exception {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().exportToJpg(file);
	}

	public final static CloseTabbedPane init(WorkspaceChangeListener listener) {
		if (me == null) {
			me = new CloseTabbedPane(listener);
		}
		return me;
	}

	public static CloseTabbedPane getInstance() {
		return me;
	}
}
