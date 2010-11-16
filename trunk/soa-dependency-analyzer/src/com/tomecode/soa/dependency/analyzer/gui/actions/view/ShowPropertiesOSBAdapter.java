package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.PropertiesViewOsbAdapter;

/**
 * 
 * Show view: {@link PropertiesViewOsbAdapter}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowPropertiesOSBAdapter extends Action implements CloseViewListener {

	public ShowPropertiesOSBAdapter() {
		setChecked(true);
		setText("Show Properties about OSB 10g Adapter");
		setToolTipText("Show Properties about OSB 10g Adapter");
		WindowChangeListener.getInstance().register(PropertiesViewOsbAdapter.ID, this);
		setImageDescriptor(ImageFactory.properties);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(PropertiesViewOsbAdapter.ID);
		} else {
			GuiUtils.hideView(PropertiesViewOsbAdapter.ID);
		}
	}

	@Override
	public void userClose() {
		setChecked(false);
	}

}
