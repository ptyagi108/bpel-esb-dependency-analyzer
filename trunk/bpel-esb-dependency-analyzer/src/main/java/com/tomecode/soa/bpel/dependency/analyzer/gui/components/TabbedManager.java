package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import com.tomecode.soa.oracle10g.model.Workspace;

/**
 * 
 * Tab panel container
 * 
 * @author Tomas Frastia
 * 
 */
public final class TabbedManager extends JTabbedPane {

	private static final long serialVersionUID = 3518536729848293337L;

	/**
	 * Constructor
	 */
	public TabbedManager() {
		super();
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

	/**
	 * add new tab panel
	 * 
	 * @param name
	 * @param workspace
	 */
	public final void addTable(String name, Workspace workspace) {
		addTab(name, new WorkspacePanel(workspace));
		setSelectedIndex(getTabCount() - 1);
	}
}
