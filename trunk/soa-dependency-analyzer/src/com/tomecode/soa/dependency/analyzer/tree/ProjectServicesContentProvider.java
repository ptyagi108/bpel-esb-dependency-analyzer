package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;

/**
 * 
 * Show all services in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class ProjectServicesContentProvider implements ITreeContentProvider {

	public ProjectServicesContentProvider() {
	}

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof OraSB10gProject) {
			return ((OraSB10gProject) parent).getServices().toArray();
		}
		return null;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).getChildren();
		} else if (element instanceof OraSB10gProject) {
			return ((OraSB10gProject) element).getServices().toArray();
		}
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).hasChildren();
		} else if (element instanceof OraSB10gProject) {
			return ((OraSB10gProject) element).getServices().isEmpty();
		}
		return false;
	}

	@Override
	public final Object getParent(Object arg0) {
		return null;
	}

	@Override
	public final void dispose() {

	}

	@Override
	public final void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}
}
