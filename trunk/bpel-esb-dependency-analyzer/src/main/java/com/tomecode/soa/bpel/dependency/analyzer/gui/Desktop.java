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
import com.tomecode.soa.dependency.analyzer.utils.Utils;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.HideNotifiListener;

/**
 * 
 * Desktop form - main form
 * 
 * @author Tomas Frastia
 * 
 */
public final class Desktop extends Frame implements ActionListener, ReloadRecentMenuListener {

	private static final long serialVersionUID = -9130398154838815230L;
	/**
	 * contains all recent files
	 */
	private JMenu menuRecentFiles;

	private final JMenuBar rootMenuBar;

	private final TabbedManager workspaceTabb;

	/**
	 * Constructor
	 */
	private Desktop() {
		super("SOA: BPEL and ESB Dependency analyzer", 1044, 644, true, true);
		setIconImage(IconFactory.BDA_SMALL.getImage());
		SettingsManager.getInstance().setReloadRecentMenuListener(this);
		rootMenuBar = new JMenuBar();
		menuRecentFiles = new JMenu("Recent Files");
		rootMenuBar.add(createMenuFile());
		rootMenuBar.add(createMenuHelp());
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
		JMenu mFile = new JMenu("File");
		mFile.add(createMenuItem("Open", IconFactory.WORKSPACE));
		mFile.add(createMenuItem("Open Multi-Workspace", IconFactory.WORKSPACE));
		mFile.addSeparator();
		mFile.add(menuRecentFiles);
		mFile.addSeparator();
		mFile.add(createMenuItem("Exit", IconFactory.EXIT));
		mFile.setMnemonic(KeyEvent.VK_ALT);
		changesInRecentFiles();
		return mFile;
	}

	/**
	 * create menu help
	 * 
	 * @return
	 */
	private final JMenu createMenuHelp() {
		JMenu mFile = new JMenu("Help");
		mFile.add(createMenuItem("Report Issue", IconFactory.ISSUES));
		mFile.addSeparator();
		mFile.add(createMenuItem("About...", IconFactory.ABOUT));
		mFile.setMnemonic(KeyEvent.VK_ALT);
		return mFile;
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
		} else if (e.getActionCommand().equals("Report Issue")) {
			Utils.openInDefaultBrowser("http://code.google.com/p/bpel-esb-dependency-analyzer/issues/list");
		} else if (e.getActionCommand().equals("About...")) {
			FrmAbout.showMe(this);
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
