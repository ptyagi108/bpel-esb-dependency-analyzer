package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.tree.ProjectFilesNavigator;

/**
 * 
 * Show view: {@link ProjectFilesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowProjectFilesViewAction extends Action implements CloseViewListener {

	public ShowProjectFilesViewAction() {
		setChecked(true);
		setText("Show Project Files View");
		setToolTipText("Show Project Files View");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor("IMG_DEF_VIEW"));
		WindowChangeListener.getInstance().register(ProjectFilesNavigator.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(ProjectFilesNavigator.ID);
		} else {
			GuiUtils.hideView(ProjectFilesNavigator.ID);
		}

	}

	@Override
	public final void userClose() {
		setChecked(false);
	}
}
