package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectServicesNavigator;

/**
 * 
 * Show view: {@link BpelProcessStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowBpelProcessStructureViewAction extends Action implements CloseViewListener {

	public ShowBpelProcessStructureViewAction() {
		setChecked(true);
		setText("Show BPEL Structure Navigator View");
		setToolTipText("Show BPEL Structure Navigator View");
		setImageDescriptor(ImageFactory.service_structure_tree);
		WindowChangeListener.getInstance().register(BpelProcessStructureNavigator.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(BpelProcessStructureNavigator.ID);
		} else {
			GuiUtils.hideView(BpelProcessStructureNavigator.ID);
		}

	}

	@Override
	public void userClose() {
		setChecked(false);
	}

}
