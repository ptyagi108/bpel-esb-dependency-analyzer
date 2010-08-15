package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard;
import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard.WorkspaceConfig;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;

/**
 * Open form for "Open new workspace"
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenWorkspaceAction extends Action {

	private final IWorkbenchWindow window;

	private int instanceNum = 0;

	public OpenWorkspaceAction(IWorkbenchWindow window) {
		this.window = window;
		setText("Open workspace");

	}

	public final void run() {
		OpenNewWorkspaceWizard wizard = new OpenNewWorkspaceWizard();
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.setBlockOnOpen(true);
		dialog.create();
		if (WizardDialog.OK == dialog.open()) {

			try {
				WorkspaceConfig config = wizard.getConfig();

				System.err.println("finish" + config);

				window.getActivePage().showView(VisualGraphView.ID, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}
}
