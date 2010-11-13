package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * 
 * Open/Close {@link VisualGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OpenNewVisualViewAction extends Action {

	public OpenNewVisualViewAction() {
		setText("Open new denpendecy graph");
		setToolTipText("Open new denpendecy graph");
		setImageDescriptor(ImageFactory.visual_graph_view);
	}

	public final void run() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new VisualGraphView(), VisualGraphView.ID, false);
		} catch (PartInitException e) {
			MessageDialog.openError(null, "Error", "Opps...Error opening view:" + e.getMessage());
		}
	}

}
