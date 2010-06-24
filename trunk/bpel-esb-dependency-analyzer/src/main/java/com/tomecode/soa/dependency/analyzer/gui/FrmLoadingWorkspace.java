package com.tomecode.soa.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.tomecode.soa.dependency.analyzer.gui.panels.TabbedManager;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.settings.SettingsManager;
import com.tomecode.soa.dependency.analyzer.settings.RecentFile.RecentFileType;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.parser.MultiWorkspaceParser;
import com.tomecode.soa.oracle10g.parser.ServiceParserException;
import com.tomecode.util.gui.Dialog;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * 
 * Form for loading workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class FrmLoadingWorkspace extends Dialog {

	private static final long serialVersionUID = -571212973983820836L;

	private final JProgressBar progressBar;

	private final JLabel message;

	/**
	 * Constructor
	 * 
	 * @param owner
	 */
	public FrmLoadingWorkspace(Frame owner) {
		super(owner, "Loading workspace...", 400, 160, false, true, true, true);
		setIconImage(IconFactory.BDA_SMALL.getImage());
		message = new JLabel();
		progressBar = new JProgressBar(0, 50);
		progressBar.setPreferredSize(new Dimension(100, 10));
		progressBar.setIndeterminate(true);
		message.setIcon(IconFactory.SEARCH);

		JPanel pMiddle = PanelFactory.createBorderLayout("");
		pMiddle.add(message, BorderLayout.CENTER);
		pMiddle.add(PanelFactory.wrapByBorderLayout(progressBar, BorderLayout.SOUTH));

		panelRoot.add(PanelFactory.createBorderLayout(pMiddle, 15, 15, 15, 15), BorderLayout.CENTER);
	}

	/**
	 * open new {@link MultiWorkspace}
	 * 
	 * @param name
	 * @param workspaceFolder
	 * @param index
	 * @param tabbedManager
	 */
	public final static void openNewMultipleWorkspace(final String name, final File workspaceFolder, final int index, final TabbedManager tabbedManager) {

		final FrmLoadingWorkspace frm = new FrmLoadingWorkspace(null);
		final Runnable runnable = new Runnable() {

			@Override
			public final void run() {
				try {
					MultiWorkspace multiWorkspace = new MultiWorkspaceParser().parse(workspaceFolder);
					if (index == -1) {
						SettingsManager.getInstance().addRecentFile(name, RecentFileType.ORACLE10G_MULTIPLE_WORKSPACE, workspaceFolder);
					} else {
						SettingsManager.getInstance().addRecentFile(RecentFileType.ORACLE10G_WORKSPACE, index);
					}
					frm.setVisible(false);
					tabbedManager.addTable(name, multiWorkspace);
				} catch (ServiceParserException e) {
					FrmError.showMe(e.getMessage(), e);
				}
			}
		};

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new Thread(runnable).start();
				frm.setVisible(true);
			}
		});
	}

	/**
	 * open new {@link Workspace}
	 * 
	 * @param workspaceFolder
	 * @param index
	 * @param tabbedManager
	 */
	public static final void openNewWorkspace(final File workspaceFolder, final int index, final TabbedManager tabbedManager) {

		final FrmLoadingWorkspace frm = new FrmLoadingWorkspace(null);
		final Runnable runnable = new Runnable() {

			@Override
			public final void run() {
				try {

					MultiWorkspace multiWorkspace = new MultiWorkspaceParser().parse(workspaceFolder);
					String name = multiWorkspace.getWorkspaces().isEmpty() ? "not found workspace" : multiWorkspace.getWorkspaces().get(0).getName();
					if (index == -1) {
						SettingsManager.getInstance().addRecentFile(name, RecentFileType.ORACLE10G_MULTIPLE_WORKSPACE, workspaceFolder);
					} else {
						SettingsManager.getInstance().addRecentFile(RecentFileType.ORACLE10G_MULTIPLE_WORKSPACE, index);
					}

					frm.setVisible(false);
					tabbedManager.addTable(name, multiWorkspace);
				} catch (ServiceParserException e) {
					FrmError.showMe(e.getMessage(), e);
				}
			}
		};

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new Thread(runnable).start();
				frm.setVisible(true);
			}
		});

	}
}
