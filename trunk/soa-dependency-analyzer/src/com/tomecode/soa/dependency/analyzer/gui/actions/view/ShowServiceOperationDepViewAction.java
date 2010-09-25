package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;

/**
 * 
 * Show view: {@link ServiceBusStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowServiceOperationDepViewAction extends Action implements CloseViewListener {

	public ShowServiceOperationDepViewAction() {
		setChecked(true);
		setText("Show Service Dependency View");
		setToolTipText("Show Service Dependency View");
		setImageDescriptor(ImageFactory.dependency_by_operation_tree);
		WindowChangeListener.getInstance().register(ServiceOperationsDepNavigator.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(ServiceOperationsDepNavigator.ID);
		} else {
			GuiUtils.hideView(ServiceOperationsDepNavigator.ID);
		}

	}

	@Override
	public void userClose() {
		setChecked(false);
	}

}
