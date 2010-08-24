package com.tomecode.soa.dependency.analyzer.gui.actions;

import org.eclipse.jface.action.Action;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * 
 * Action for refresh all workspaces..., projects ...
 * 
 * @author Tomas Frastia
 * 
 */
public final class RefreshAction extends Action {

	private Object selectedNode;

	public RefreshAction() {
		setText("Refresh...");
	//	setImageDescriptor(ImageFactory.r);
	}

	public final void run() {
		if (selectedNode instanceof Ora10gMultiWorkspace) {
			ApplicationManager.getInstance().refershOrale10g((Ora10gMultiWorkspace) selectedNode);
		} else if (selectedNode instanceof OpenEsbMultiWorkspace) {
			ApplicationManager.getInstance().refreshOpenEsb((OpenEsbMultiWorkspace) selectedNode);
		}

		else if (selectedNode instanceof Project) {
			Project project = (Project) selectedNode;
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				ApplicationManager.getInstance().refershOrale10g((Ora10gMultiWorkspace) project.getWorkpsace().getMultiWorkspace());
			} else if (project.getType() == ProjectType.OPEN_ESB_BPEL) {
				ApplicationManager.getInstance().refreshOpenEsb((OpenEsbMultiWorkspace) project.getWorkpsace().getMultiWorkspace());
			}
		}

	}

	/**
	 * node which will be refreshed
	 * 
	 * @param selectedNode
	 */
	public final void setSelectectedNode(Object selectedNode) {
		this.selectedNode = selectedNode;
	}
}
