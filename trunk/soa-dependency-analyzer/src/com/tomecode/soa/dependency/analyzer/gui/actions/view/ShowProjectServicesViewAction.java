package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.tree.ProjectServicesNavigator;
import com.tomecode.soa.dependency.analyzer.view.graph.FlowGraphView;

/**
 * 
 * Show view: {@link ProjectServicesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowProjectServicesViewAction extends Action implements CloseViewListener {

	public ShowProjectServicesViewAction() {
		setChecked(true);
		setText("Show Project Services View");
		setToolTipText("Show Project Services View");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor("IMG_DEF_VIEW"));
		WindowChangeListener.getInstance().register(ProjectServicesNavigator.ID, this);
		WindowChangeListener.getInstance().register(FlowGraphView.ID, this);
	}

	public final void run() {

		try {
			FlowGraphView flowGraphView = new FlowGraphView();

			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(flowGraphView, FlowGraphView.ID,false);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isChecked()) {
			GuiUtils.showView(ProjectServicesNavigator.ID);
		} else {
			GuiUtils.hideView(ProjectServicesNavigator.ID);
		}

	}

	@Override
	public final void userClose() {
		setChecked(false);
	}
}
