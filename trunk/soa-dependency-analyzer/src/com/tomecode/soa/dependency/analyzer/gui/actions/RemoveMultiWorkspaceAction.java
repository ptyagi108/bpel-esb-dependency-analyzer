package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * 
 * Remove selected multi workspace in {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
public final class RemoveMultiWorkspaceAction extends Action {

	public RemoveMultiWorkspaceAction() {
		super();
		setText("Remove Multi Workspace");
		setToolTipText("Remove Multi Workspace");
		setImageDescriptor(ImageFactory.trash);
	}

	public final void run() {
		MultiWorkspace multiWorkspace = GuiUtils.getWorkspacesNavigator().removeMultiWorkspace();
		if (multiWorkspace != null) {
			GuiUtils.getProjectStructureNavigator().removeMultiWorkspace(multiWorkspace);
			GuiUtils.getBpelProcessStructureNavigator().removeMultiWorkspace(multiWorkspace);
			GuiUtils.getPropertiesView().removeMultiWorkspace(multiWorkspace);
			GuiUtils.getServiceBusStructureNavigator().removeMultiWorkspace(multiWorkspace);
			GuiUtils.getServiceOperationsDepNavigator().removeMultiWorkspace(multiWorkspace);

			GuiUtils.getVisualGraphView().clearContentFromGraph();
		}
	}

	public final void setEnableFor(Object object) {
		setEnabled((object instanceof MultiWorkspace));
	}
}
