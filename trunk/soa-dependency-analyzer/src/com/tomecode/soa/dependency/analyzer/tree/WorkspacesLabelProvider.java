package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.services.BpelProcess;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * {@link LabelProvider} for {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class WorkspacesLabelProvider extends LabelProvider {

	public final Image getImage(Object element) {
		if (element instanceof Project) {
			return ((Project) element).getImage();
		} else if (element instanceof MultiWorkspace) {
			return ImageFactory.MULTI_WORKSPACE;
		} else if (element instanceof Workspace) {
			return ImageFactory.WORKSPACE;
		} else if (element instanceof BpelProcess) {
			return ((BpelProcess) element).getImage();
		}

		return null;
	}
}
