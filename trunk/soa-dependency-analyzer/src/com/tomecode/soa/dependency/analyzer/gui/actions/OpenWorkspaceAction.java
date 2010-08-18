package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard;

/**
 * Open form for "Open new workspace"
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenWorkspaceAction extends Action {

	private final IWorkbenchWindow window;

	// private int instanceNum = 0;

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

				LoadingDialogs.showOpenNewWorkspace(wizard.getShell(), wizard.getConfig());

				// window.getActivePage().showView(VisualGraphView.ID,
				// Integer.toString(instanceNum++),
				// IWorkbenchPage.VIEW_ACTIVATE);
			} catch (OperationCanceledException e) {
				// Operation was canceled
			}

		}

	}

}
