package com.tomecode.soa.dependency.analyzer.gui.actions.view;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.event.CloseViewListener;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;

/**
 * 
 * Show view: {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ShowWorkspaceNavigatorAction extends Action implements CloseViewListener {

	public ShowWorkspaceNavigatorAction() {
		setChecked(true);
		setText("Show Workspace View");
		setToolTipText("Show Workspace View");
		setImageDescriptor(ImageFactory.workspace_navigator);
		WindowChangeListener.getInstance().register(WorkspacesNavigator.ID, this);
	}

	public final void run() {
		if (isChecked()) {
			GuiUtils.showView(WorkspacesNavigator.ID);
		} else {
			GuiUtils.hideView(WorkspacesNavigator.ID);
		}

	}

	@Override
	public void userClose() {
		setChecked(false);
	}

}
