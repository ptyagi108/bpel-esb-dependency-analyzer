package com.tomecode.soa.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tomecode.soa.dependency.analyzer.gui.panels.WorkspaceChangeListener;
import com.tomecode.soa.dependency.analyzer.gui.tab.CloseTabbedPane;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.settings.RecentFile;
import com.tomecode.soa.dependency.analyzer.settings.ReloadRecentMenuListener;
import com.tomecode.soa.dependency.analyzer.settings.SettingsManager;
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
public final class Desktop extends Frame implements ActionListener, ReloadRecentMenuListener, WorkspaceChangeListener {

	private static final long serialVersionUID = -9130398154838815230L;
	/**
	 * contains all recent files
	 */
	private JMenu menuRecentFiles;

	private final JMenuBar rootMenuBar;

	private final CloseTabbedPane workspaceTabb;

	private static Frame me;

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
		rootMenuBar.add(createMenuVisual());
		rootMenuBar.add(createMenuHelp());
		workspaceTabb = CloseTabbedPane.init(this);
		addToContainer(rootMenuBar, BorderLayout.NORTH);
		addToContainer(workspaceTabb, BorderLayout.CENTER);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("resise");
			}

		});

		me = this;
	}

	/**
	 * create {@link JMenu} - File
	 * 
	 * @return
	 */
	private final JMenu createMenuFile() {
		JMenu mFile = new JMenu("File");
		mFile.add(createMenuItem("Open Workspace", IconFactory.WORKSPACE, true));
		mFile.add(createMenuItem("Open Multi-Workspace", IconFactory.WORKSPACE, true));
		mFile.addSeparator();
		mFile.add(menuRecentFiles);
		mFile.addSeparator();
		mFile.add(createMenuItem("Exit", IconFactory.EXIT, true));
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
		mFile.add(createMenuItem("Report Issue", IconFactory.ISSUES, true));
		mFile.addSeparator();
		mFile.add(createMenuItem("About...", IconFactory.ABOUT, true));
		return mFile;
	}

	/**
	 * create edit file
	 * 
	 * @return
	 */
	// TODO: dokoncit zoomovanie + pridanei printu

	private final JMenu createMenuVisual() {
		JMenu mVisual = new JMenu("Visual");
		mVisual.add(createMenuItem("Zoom In", IconFactory.ZOOM_IN, false));
		mVisual.add(createMenuItem("Zoom Out", IconFactory.ZOOM_OUT, false));
		mVisual.add(createMenuItem("Reset Zoom", IconFactory.ZOOM_RESET, false));
		mVisual.addSeparator();
		mVisual.add(createMenuItem("Export", IconFactory.EXPORT, false));

		return mVisual;
	}

	/**
	 * create {@link JMenuItem}
	 * 
	 * @param name
	 * @return
	 */
	private final JMenuItem createMenuItem(String name, Icon icon, boolean enable) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setActionCommand(name);
		if (icon != null) {
			menuItem.setIcon(icon);
		}
		menuItem.setEnabled(enable);
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

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				FrmError.showMe(e.getMessage(), e);
			}
		});
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new Desktop().setVisible(true);
			}
		});

	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open Workspace")) {
			newWorkspace();
		} else if (e.getActionCommand().equals("Open Multi-Workspace")) {
			newMultiWorkspace();
		} else if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("Report Issue")) {
			Utils.openInDefaultBrowser("http://code.google.com/p/bpel-esb-dependency-analyzer/issues/list");
		} else if (e.getActionCommand().equals("About...")) {
			FrmAbout.showMe(this);
		} else if (e.getActionCommand().equals("Zoom In")) {
			workspaceTabb.zoomInSelectedVisualPanel();
		} else if (e.getActionCommand().equals("Zoom Out")) {
			workspaceTabb.zoomOutSelectedVisualPanel();
		} else if (e.getActionCommand().equals("Reset Zoom")) {
			workspaceTabb.zoomResetSelectedVisualPanel();
		} else if (e.getActionCommand().equals("Export")) {
			// workspaceTabb.exportVisualPanel();
			FrmExportVisualDependencies.showMe();
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

	@Override
	public final void displayVisualPanel() {
		enableEditMenuItems(true);
	}

	@Override
	public final void hideVisualPanel() {
		enableEditMenuItems(false);
	}

	/**
	 * set true/false for items in 'edit' menu
	 * 
	 * @param enable
	 */
	private final void enableEditMenuItems(boolean enable) {
		JMenu mEdit = rootMenuBar.getMenu(1);
		for (int i = 0; i <= mEdit.getItemCount() - 1; i++) {
			if (mEdit.getItem(i) != null) {
				mEdit.getItem(i).setEnabled(enable);
			}
		}
	}

	public final static Frame getFrame() {
		return me;
	}

}
