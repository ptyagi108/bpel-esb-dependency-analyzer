package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.tomecode.soa.dependency.analyzer.gui.displays.OpenNewWorkspaceWizard;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Open form for "Open new workspace"
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenWorkspaceAction extends Action {

	private IWorkbenchWindow window;

	// private int instanceNum = 0;

	public OpenWorkspaceAction() {
		setImageDescriptor(ImageFactory.open);
		setText("Open new workspace");

	}

	public OpenWorkspaceAction(IWorkbenchWindow window) {
		this();
		this.window = window;
	}

	public final void run() {

		OpenNewWorkspaceWizard wizard = new OpenNewWorkspaceWizard();
		WizardDialog dialog = null;
		if (window != null) {
			dialog = new WizardDialog(window.getShell(), wizard);
		} else {
			dialog = new WizardDialog(null, wizard);
		}

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
