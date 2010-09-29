package com.tomecode.soa.dependency.analyzer.gui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
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
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Remove selected workspace in {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 */
public final class RemoveWorkspaceAction extends Action {

	public RemoveWorkspaceAction() {
		super();
		setText("Remove Workspace");
		setToolTipText("Remove Workspace");
		setImageDescriptor(ImageFactory.trash);
	}

	public final void run() {
		final WorkspacesNavigator workspacesNavigator = GuiUtils.getWorkspacesNavigator();
		final Workspace removeWorkspace = workspacesNavigator.removeSelectedWorkspace();

		if (removeWorkspace != null) {
			ProjectStructureNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
			if (projectStructureNavigator != null) {
				projectStructureNavigator.removeWorkspace(removeWorkspace);
			}
			BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
			if (bpelProcessStructureNavigator != null) {
				bpelProcessStructureNavigator.removeWorkspace(removeWorkspace);
			}
			PropertiesView propertiesView = GuiUtils.getPropertiesView();
			if (propertiesView != null) {
				propertiesView.removeWorkspace(removeWorkspace);
			}
			ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceBusStructureNavigator.removeWorkspace(removeWorkspace);
			}
			ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceOperationsDepNavigator.removeWorkspace(removeWorkspace);
			}
			List<Integer> instanceNumbers = GuiUtils.getAllVisualGraphInstances();
			for (Integer intanceNumber : instanceNumbers) {
				VisualGraphView visualGraphView = GuiUtils.getVisualGraphView(intanceNumber);
				if (visualGraphView != null) {
					visualGraphView.removeWorkspace(removeWorkspace);
				}
			}

		}

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(null);
		try {
			dialog.run(true, false, new IRunnableWithProgress() {

				@Override
				public final void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					ApplicationManager.getInstance().removeWorkspace(removeWorkspace);
					monitor.setTaskName("Refresh dependencies...");
					MultiWorkspace newMultiWorkspace = ApplicationManager.getInstance().refreshMultiWorkspace(removeWorkspace.getMultiWorkspace());
					workspacesNavigator.updateMultiWorkspace(removeWorkspace.getMultiWorkspace(), newMultiWorkspace);
				}
			});

			dialog.open();
			workspacesNavigator.refreshTree();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			dialog.close();
		}

	}

	public final void setEnableFor(Object selectedObject) {
		setEnabled(((selectedObject instanceof Workspace)));
	}
}
