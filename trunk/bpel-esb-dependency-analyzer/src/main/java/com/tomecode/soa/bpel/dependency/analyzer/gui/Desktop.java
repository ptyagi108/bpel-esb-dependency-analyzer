package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tomecode.soa.bpel.dependency.analyzer.gui.components.TabbedManager;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;
import com.tomecode.soa.oracle10g.parser.WorkspaceParser;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.HideNotifiListener;

/**
 * 
 * Desktop form
 * 
 * @author Tomas Frastia
 * 
 */
public final class Desktop extends Frame implements ActionListener {

	private static final long serialVersionUID = -9130398154838815230L;

	private final JMenuBar rootMenuBar;

	private final TabbedManager workspaceTabb;

	private Desktop() {
		super("BPEL and ESB Dependency analyzer", 944, 544, true, true);
		rootMenuBar = new JMenuBar();
		rootMenuBar.add(createMenuFile());
		workspaceTabb = new TabbedManager();
		addToContainer(rootMenuBar, BorderLayout.NORTH);
		addToContainer(workspaceTabb, BorderLayout.CENTER);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("resise");
			}

		});

	}

	/**
	 * create {@link JMenu} - File
	 * 
	 * @return
	 */
	private final JMenu createMenuFile() {
		JMenu menu = new JMenu("File");
		menu.add(createMenuItem("Open"));
		menu.add(createMenuItem("Exit"));
		menu.setMnemonic(KeyEvent.VK_ALT);
		return menu;
	}

	/**
	 * create {@link JMenuItem}
	 * 
	 * @param name
	 * @return
	 */
	private final JMenuItem createMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setActionCommand(name);
		menuItem.addActionListener(this);
		return menuItem;
	}

	public static final void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Desktop().setVisible(true);
			}
		});
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open")) {
			newWorkspace();
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}

	/**
	 * display {@link FrmOpenWorkspace} and
	 */
	private final void newWorkspace() {
		FrmOpenWorkspace.showMe(this, new HideNotifiListener() {
			@Override
			public final void hideForm(Object... returnObj) {
				if (returnObj != null && returnObj.length != 0) {
					openNewWorkspace((File) returnObj[0]);
				}
			}
		});
	}

	/**
	 * open new {@link Workspace}
	 * 
	 * @param name
	 * @param workspace
	 */
	private final void openNewWorkspace(File workspace) {
		try {

			workspaceTabb.addTable(new WorkspaceParser().parse(workspace));
		} catch (ServiceParserException e) {
			FrmError.showMe(e.getMessage(), e);
		}

	}
}
