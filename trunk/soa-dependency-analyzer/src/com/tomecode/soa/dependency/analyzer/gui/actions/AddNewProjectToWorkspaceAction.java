package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;

import com.tomecode.soa.dependency.analyzer.gui.wizards.AddNewProjectToWorkspaceWizard;

/**
 * 
 * Add a new project to selected workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class AddNewProjectToWorkspaceAction extends Action {

	public AddNewProjectToWorkspaceAction() {
		setText("Add new project");
	}

	public final void run() {
		AddNewProjectToWorkspaceWizard wizard = new AddNewProjectToWorkspaceWizard();
		WizardDialog dialog = null;
		// if (window != null) {
		// dialog = new WizardDialog(window.getShell(), wizard);
		// } else {
		dialog = new WizardDialog(null, wizard);
		// }

		dialog.setBlockOnOpen(true);
		dialog.create();
		if (WizardDialog.OK == dialog.open()) {
			try {
				LoadingDialogs.showAddNewWorkspace(null, wizard.getConfig());
			} catch (OperationCanceledException e) {
				// Operation was canceled
			}

		}
	}
}
