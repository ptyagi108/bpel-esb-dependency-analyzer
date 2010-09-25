package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;

/**
 * 
 * Show view: {@link ServiceBusStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowServiceBusStructureViewAction extends Action implements CloseViewListener {

	public ShowServiceBusStructureViewAction() {
		setChecked(true);
		setText("Show Service Bus Structure Navigator View");
		setToolTipText("Show Service Bus Structure Navigator View");
		setToolTipText("Show Properties View");
		setImageDescriptor(ImageFactory.service_structure_tree);
		WindowChangeListener.getInstance().register(ServiceBusStructureNavigator.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(ServiceBusStructureNavigator.ID);
		} else {
			GuiUtils.hideView(ServiceBusStructureNavigator.ID);
		}

	}

	@Override
	public final void userClose() {
		setChecked(false);
	}

}
