package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.WorkspaceRootNode;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.project.BpelProject;
import com.tomecode.soa.oracle10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.oracle10g.workspace.Ora10gWorkspace;

/**
 * 
 * {@link ITreeContentProvider} for {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class WorkspacesContentProvider implements ITreeContentProvider {

	public WorkspacesContentProvider() {
	}

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof WorkspaceRootNode) {
			return ((WorkspaceRootNode) parent).getChildren();
		} else if (parent instanceof Ora10gMultiWorkspace) {
			return ((Ora10gMultiWorkspace) parent).getChildren();
		} else if (parent instanceof Ora10gWorkspace) {
			return ((Ora10gWorkspace) parent).getChildren();
		} else if (parent instanceof BpelProject) {
			return ((BpelProject) parent).getDependencyProjects().toArray();
		} else if (parent instanceof EsbProject) {
			return ((EsbProject) parent).getProjectDependecies().toArray();
		} else if (parent instanceof OpenEsbMultiWorkspace) {
			return ((OpenEsbMultiWorkspace) parent).getWorkspaces().toArray();
		} else if (parent instanceof OpenEsbWorkspace) {
			return ((OpenEsbWorkspace) parent).getProjects().toArray();
		}

		return null;
	}

	@Override
	public final Object getParent(Object element) {
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof WorkspaceRootNode) {
			return ((WorkspaceRootNode) element).hasChildren();
		} else if (element instanceof Ora10gMultiWorkspace) {
			return ((Ora10gMultiWorkspace) element).hasChildren();
		} else if (element instanceof Ora10gWorkspace) {
			return ((Ora10gWorkspace) element).hasChildren();
		} else if (element instanceof BpelProject) {
			return !((BpelProject) element).getDependencyProjects().isEmpty();
		} else if (element instanceof EsbProject) {
			return !((EsbProject) element).getProjectDependecies().isEmpty();
		} else if (element instanceof OpenEsbMultiWorkspace) {
			return !((OpenEsbMultiWorkspace) element).getWorkspaces().isEmpty();
		} else if (element instanceof OpenEsbWorkspace) {
			return !((OpenEsbWorkspace) element).getProjects().isEmpty();
		}
		return false;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof WorkspaceRootNode) {
			return ((WorkspaceRootNode) element).getChildren();
		} else if (element instanceof Ora10gMultiWorkspace) {
			return ((Ora10gMultiWorkspace) element).getChildren();
		} else if (element instanceof Ora10gWorkspace) {
			return ((Ora10gWorkspace) element).getChildren();
		} else if (element instanceof BpelProject) {
			return ((BpelProject) element).getDependencyProjects().toArray();
		} else if (element instanceof EsbProject) {
			return ((EsbProject) element).getProjectDependecies().toArray();
		} else if (element instanceof OpenEsbMultiWorkspace) {
			return ((OpenEsbMultiWorkspace) element).getWorkspaces().toArray();
		} else if (element instanceof OpenEsbWorkspace) {
			return ((OpenEsbWorkspace) element).getProjects().toArray();
		}
		return null;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}

}
