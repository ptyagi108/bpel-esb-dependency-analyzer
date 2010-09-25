package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Link with navigator
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class LinkWithNavigatorAction extends Action {

	public boolean checked = true;

	public LinkWithNavigatorAction() {
		setText("Link with navigator");
		setToolTipText("Link with navigator");
		setImageDescriptor(ImageFactory.link_with_navigator_on);
		setChecked(true);
	}

	public final void run() {
		if (checked) {
			checked = false;
			// setImageDescriptor(ImageFactory.link_with_navigator_off);
		} else {
			checked = true;
			// setImageDescriptor(ImageFactory.link_with_navigator_on);
		}
		setChecked(checked);
	}

}
