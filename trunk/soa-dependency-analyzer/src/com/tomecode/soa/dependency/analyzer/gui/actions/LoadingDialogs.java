package com.tomecode.soa.dependency.analyzer.gui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard.WorkspaceConfig;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * Contains helper methods which shows {@link ProgressMonitorDialog}
 * 
 * @author Tomas Frastia
 * 
 */
public final class LoadingDialogs {

	/**
	 * Show {@link ProgressMonitorDialog} which parse workspace
	 * 
	 * @param shell
	 * @param config
	 */
	public final static void showOpenNewWorkspace(final Shell shell, final WorkspaceConfig config) {

		final List<Object> returns = new ArrayList<Object>();
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Parse workspace: " + config.getWorkspaceType() + "...", 100);
					monitor.subTask("Prepare parser...");
					monitor.worked(15);
					if (config.getWorkspaceType() == WorkspaceType.ORACLE_1OG) {
						monitor.subTask("Parsing workspace...");
						monitor.worked(40);
						try {

							// parse workspace
							MultiWorkspace multiWorkspace = ApplicationManager.getInstance().parseOra10gMultiWorkspace(config);

							returns.add(multiWorkspace);
							monitor.subTask("Finish parsing workspace...");
							monitor.worked(100);

						} catch (ServiceParserException e) {
							// TODO:error
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						monitor.subTask("Finish parsing workspace...");
						monitor.worked(100);
					}

					monitor.done();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// wait for finish task
		dialog.open();
		// close dialog
		dialog.close();

		// If the task was performed a successful 'return' will contain the
		// result
		if (!returns.isEmpty()) {
			MultiWorkspace multiWorkspace = (MultiWorkspace) returns.get(0);
			if (config.isNewMWorkspace()) {
				GuiUtils.getWorkspacesNavigator().newMultiWorkspace(multiWorkspace);
			} else {
				GuiUtils.getWorkspacesNavigator().updateMultiWorkspace(multiWorkspace);
			}
		}

	}
}
