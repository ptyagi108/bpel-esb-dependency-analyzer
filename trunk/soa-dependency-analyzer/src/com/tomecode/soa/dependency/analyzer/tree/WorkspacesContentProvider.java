package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.WorkspaceRootNode;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * {@link ITreeContentProvider} for {@link WorkspacesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
			return ((Ora10gMultiWorkspace) parent).getWorkspaces().toArray();
		} else if (parent instanceof Ora10gWorkspace) {
			return ((Ora10gWorkspace) parent).getChildren();
		} else if (parent instanceof Ora10gBpelProject) {
			return ((Ora10gBpelProject) parent).getDependencyProjects().toArray();
		} else if (parent instanceof Ora10gEsbProject) {
			return ((Ora10gEsbProject) parent).getProjectDependecies().toArray();
		} else if (parent instanceof OpenEsbMultiWorkspace) {
			return ((OpenEsbMultiWorkspace) parent).getWorkspaces().toArray();
		} else if (parent instanceof OpenEsbWorkspace) {
			return ((OpenEsbWorkspace) parent).getProjects().toArray();
		} else if (parent instanceof OpenEsbBpelProject) {
			return ((OpenEsbBpelProject) parent).getProcesses().toArray();
		} else if (parent instanceof OraSB10gMultiWorkspace) {
			return ((OraSB10gMultiWorkspace) parent).getWorkspaces().toArray();
		} else if (parent instanceof OraSB10gWorkspace) {
			return ((OraSB10gWorkspace) parent).getProjects().toArray();
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
		} else if (element instanceof Ora10gBpelProject) {
			return !((Ora10gBpelProject) element).getDependencyProjects().isEmpty();
		} else if (element instanceof Ora10gEsbProject) {
			return !((Ora10gEsbProject) element).getProjectDependecies().isEmpty();
		} else if (element instanceof OpenEsbMultiWorkspace) {
			return !((OpenEsbMultiWorkspace) element).getWorkspaces().isEmpty();
		} else if (element instanceof OpenEsbWorkspace) {
			return !((OpenEsbWorkspace) element).getProjects().isEmpty();
		} else if (element instanceof OpenEsbBpelProject) {
			return !((OpenEsbBpelProject) element).getProcesses().isEmpty();
		} else if (element instanceof OraSB10gMultiWorkspace) {
			return !((OraSB10gMultiWorkspace) element).getWorkspaces().isEmpty();
		} else if (element instanceof OraSB10gWorkspace) {
			return !((OraSB10gWorkspace) element).getProjects().isEmpty();
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
		} else if (element instanceof Ora10gBpelProject) {
			return ((Ora10gBpelProject) element).getDependencyProjects().toArray();
		} else if (element instanceof Ora10gEsbProject) {
			return ((Ora10gEsbProject) element).getProjectDependecies().toArray();
		} else if (element instanceof OpenEsbMultiWorkspace) {
			return ((OpenEsbMultiWorkspace) element).getWorkspaces().toArray();
		} else if (element instanceof OpenEsbWorkspace) {
			return ((OpenEsbWorkspace) element).getProjects().toArray();
		} else if (element instanceof OpenEsbBpelProject) {
			return ((OpenEsbBpelProject) element).getProcesses().toArray();
		} else if (element instanceof OraSB10gMultiWorkspace) {
			return ((OraSB10gMultiWorkspace) element).getWorkspaces().toArray();
		} else if (element instanceof OraSB10gWorkspace) {
			return ((OraSB10gWorkspace) element).getProjects().toArray();
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
