package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import com.tomecode.soa.oracle10g.MultiWorkspace;

/**
 * 
 * Tab panel container
 * 
 * @author Tomas Frastia
 * 
 */
public final class TabbedManager extends JTabbedPane {

	private static final long serialVersionUID = 3518536729848293337L;

	private WorkspaceChangeListener changeListener;
	/**
	 * helper reference
	 */
	private static TabbedManager me;

	/**
	 * Constructor
	 */
	public TabbedManager(WorkspaceChangeListener changeListener) {
		super();
		me = this;
		this.changeListener = changeListener;
		KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK);
		// registering ctrl+tab key
		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.singleton(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0)));
		InputMap im = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		im.put(ctrlTab, "ctrlTab");
		getActionMap().put("ctrlTab", new AbstractAction("ctrlTab") {

			private static final long serialVersionUID = -4338670465201740592L;

			public final void actionPerformed(ActionEvent e) {
				// ctrl+tab as an action to move to next tab
				if (getSelectedIndex() + 1 >= getComponentCount()) {
					setSelectedIndex(0);
				} else {
					setSelectedIndex(getSelectedIndex() + 1);
				}
			}
		});

	}

	public final void addTable(String name, MultiWorkspace multiWorkspace) {
		addTab(name, new WorkspacePanel(multiWorkspace, changeListener));
		setSelectedIndex(getTabCount() - 1);
	}

	public final static TabbedManager getInstance() {
		return me;
	}

	/**
	 * zoom in selected/displayed visual panel
	 */
	public final void zoomInSelectedVisualPanel() {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().zoomIn();
	}

	public final void zoomOutSelectedVisualPanel() {
		WorkspacePanel workspacePanel = (WorkspacePanel) getComponent(getSelectedIndex());
		workspacePanel.getVisualPanel().zoomOut();
	}

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
}
