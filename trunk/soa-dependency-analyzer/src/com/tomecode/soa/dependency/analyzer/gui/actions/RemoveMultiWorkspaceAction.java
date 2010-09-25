package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;
import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * 
 * Remove selected multi workspace in {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
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
			ProjectStructureNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
			if (projectStructureNavigator != null) {
				projectStructureNavigator.removeMultiWorkspace(multiWorkspace);
			}
			BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
			if (bpelProcessStructureNavigator != null) {
				bpelProcessStructureNavigator.removeMultiWorkspace(multiWorkspace);
			}
			PropertiesView propertiesView = GuiUtils.getPropertiesView();
			if (propertiesView != null) {
				propertiesView.removeMultiWorkspace(multiWorkspace);
			}
			ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceBusStructureNavigator.removeMultiWorkspace(multiWorkspace);
			}
			ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
			if (serviceOperationsDepNavigator != null) {
				serviceOperationsDepNavigator.removeMultiWorkspace(multiWorkspace);
			}
			VisualGraphView visualGraphView = GuiUtils.getVisualGraphView();
			if (visualGraphView != null) {
				visualGraphView.clearContentFromGraph();
			}
		}
	}

	public final void setEnableFor(Object object) {
		setEnabled((object instanceof MultiWorkspace));
	}
}
