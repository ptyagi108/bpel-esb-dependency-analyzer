package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;

/**
 * 
 * Show view: {@link PropertiesView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowPropertiesViewAction extends Action implements CloseViewListener {

	/**
	 * Constructor
	 */
	public ShowPropertiesViewAction() {
		setChecked(true);
		setText("Show Properties View");
		setToolTipText("Show Properties View");
		setImageDescriptor(ImageFactory.properties);
		WindowChangeListener.getInstance().register(PropertiesView.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(PropertiesView.ID);
		} else {
			GuiUtils.hideView(PropertiesView.ID);
		}

	}

	@Override
	public final void userClose() {
		setChecked(false);
	}
}
