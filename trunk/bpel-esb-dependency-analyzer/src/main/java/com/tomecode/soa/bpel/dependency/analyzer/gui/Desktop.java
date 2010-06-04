package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tomecode.soa.bpel.dependency.analyzer.gui.components.TabbedManager;
import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.bpel.dependency.analyzer.settings.RecentFile;
import com.tomecode.soa.bpel.dependency.analyzer.settings.ReloadRecentMenuListener;
import com.tomecode.soa.bpel.dependency.analyzer.settings.SettingsManager;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.HideNotifiListener;

/**
 * 
 * Desktop form
 * 
 * @author Tomas Frastia
 * 
 */
public final class Desktop extends Frame implements ActionListener, ReloadRecentMenuListener {

	private static final long serialVersionUID = -9130398154838815230L;

	private JMenu menuRecentFiles;

	private final JMenuBar rootMenuBar;

	private final TabbedManager workspaceTabb;

	private Desktop() {
		super("BPEL and ESB Dependency analyzer", 1044, 644, true, true);
		SettingsManager.getInstance().setReloadRecentMenuListener(this);
		rootMenuBar = new JMenuBar();
		menuRecentFiles = new JMenu("Recent Files");
		rootMenuBar.add(createMenuFile());
		workspaceTabb = new TabbedManager();
		addToContainer(rootMenuBar, BorderLayout.NORTH);
		addToContainer(workspaceTabb, BorderLayout.CENTER);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
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
		menu.add(createMenuItem("Open", IconFactory.WORKSPACE));
		menu.add(createMenuItem("Open Multi-Workspace", IconFactory.WORKSPACE));
		menu.addSeparator();
		menu.add(menuRecentFiles);
		menu.addSeparator();
		menu.add(createMenuItem("Exit", IconFactory.EXIT));
		menu.setMnemonic(KeyEvent.VK_ALT);
		changesInRecentFiles();
		return menu;
	}

	/**
	 * create {@link JMenuItem}
	 * 
	 * @param name
	 * @return
	 */
	private final JMenuItem createMenuItem(String name, Icon icon) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setActionCommand(name);
		if (icon != null) {
			menuItem.setIcon(icon);
		}
		menuItem.addActionListener(this);
		return menuItem;
	}

	/**
	 * create special {@link JMenuItem} for recent files
	 * 
	 * @param text
	 * @param filePath
	 * @return
	 */
	private final JMenuItem createMenuItemOpenRecentFiles(RecentFile recentFile, int index) {
		JMenuItem menuItem = new JMenuItem(recentFile.getName() + " - " + recentFile.getFile().getPath());
		menuItem.setActionCommand(String.valueOf(index));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public final void actionPerformed(ActionEvent e) {
				int index = Integer.parseInt(e.getActionCommand());
				File workspaceFolder = SettingsManager.getInstance().getRecentFiles().get(index).getFile();
				FrmLoadingWorkspace.openNewWorkspace(workspaceFolder, index, workspaceTabb);
			}
		});
		return menuItem;
	}

	public static final void main(String[] args) {
		System.setProperty("sun.java2d.d3d", "false");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new Desktop().setVisible(true);
			}
		});

	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open")) {
			newWorkspace();
		} else if (e.getActionCommand().equals("Open Multi-Workspace")) {
			newMultiWorkspace();
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}

	/**
	 * display {@link FrmOpenWorkspace} and
	 */
	private final void newWorkspace() {
		FrmOpenWorkspace.showMe(this, false, new HideNotifiListener() {
			@Override
			public final void hideForm(Object... returnObj) {
				if (returnObj != null && returnObj.length != 0) {
					FrmLoadingWorkspace.openNewWorkspace((File) returnObj[0], -1, workspaceTabb);
				}
			}
		});
	}

	private final void newMultiWorkspace() {
		FrmOpenWorkspace.showMe(this, true, new HideNotifiListener() {
			@Override
			public final void hideForm(Object... returnObj) {
				if (returnObj != null && returnObj.length != 0) {
					FrmLoadingWorkspace.openNewMultipleWorkspace((String) returnObj[0], (File) returnObj[1], -1, workspaceTabb);
				}
			}
		});
	}

	@Override
	public final void changesInRecentFiles() {
		menuRecentFiles.removeAll();
		List<RecentFile> files = SettingsManager.getInstance().getRecentFiles();
		for (int i = 0; i <= files.size() - 1; i++) {
			RecentFile recentFile = files.get(i);
			menuRecentFiles.add(createMenuItemOpenRecentFiles(recentFile, i));
		}
		menuRecentFiles.updateUI();
	}

}
