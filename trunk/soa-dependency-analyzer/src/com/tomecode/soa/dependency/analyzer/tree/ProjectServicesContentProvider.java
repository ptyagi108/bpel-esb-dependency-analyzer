package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.suite10g.esb.EsbGrp;
import com.tomecode.soa.ora.suite10g.esb.EsbSys;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Show all services in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class ProjectServicesContentProvider implements ITreeContentProvider {

	private final Object[] emptyData = new Object[] {};

	public ProjectServicesContentProvider() {
	}

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof OraSB10gProject) {
			return ((OraSB10gProject) parent).getFolders().getAll();// .getServices().toArray();
		} else if (parent instanceof OraSB10gFolder) {
			return ((OraSB10gFolder) parent).getAll();
		} else if (parent instanceof Ora10gBpelProject) {
			return emptyData;
		} else if (parent instanceof Ora10gEsbProject) {
			return ((Ora10gEsbProject) parent).getBasicEsbNodes().toArray();
		} else if (parent instanceof EsbSys) {
			return ((EsbSys) parent).getBasicEsbNodes().toArray();
		} else if (parent instanceof EsbGrp) {
			return ((EsbGrp) parent).getBasicEsbNodes().toArray();
		} else if (parent instanceof EsbSys) {
			return ((EsbSys) parent).getBasicEsbNodes().toArray();
		}
		return emptyData;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).getChildren();
		} else if (element instanceof OraSB10gProject) {
			return ((OraSB10gProject) element).getFolders().getAll();
		} else if (element instanceof OraSB10gFolder) {
			return ((OraSB10gFolder) element).getAll();
		} else if (element instanceof Ora10gBpelProject) {
			return emptyData;
		} else if (element instanceof Ora10gEsbProject) {
			return ((Ora10gEsbProject) element).getBasicEsbNodes().toArray();
		} else if (element instanceof EsbSys) {
			return ((EsbSys) element).getBasicEsbNodes().toArray();
		} else if (element instanceof EsbGrp) {
			return ((EsbGrp) element).getBasicEsbNodes().toArray();
		} else if (element instanceof EsbSys) {
			return ((EsbSys) element).getBasicEsbNodes().toArray();
		}
		return emptyData;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).hasChildren();
		} else if (element instanceof OraSB10gProject) {
			return ((OraSB10gProject) element).getFolders().getAll().length != 0;
		} else if (element instanceof OraSB10gFolder) {
			return ((OraSB10gFolder) element).getAll().length != 0;
		} else if (element instanceof Ora10gBpelProject) {
			return false;
		} else if (element instanceof Ora10gEsbProject) {
			return !((Ora10gEsbProject) element).getBasicEsbNodes().isEmpty();
		} else if (element instanceof EsbSys) {
			return !((EsbSys) element).getBasicEsbNodes().isEmpty();
		} else if (element instanceof EsbGrp) {
			return !((EsbGrp) element).getBasicEsbNodes().isEmpty();
		} else if (element instanceof EsbSys) {
			return !((EsbSys) element).getBasicEsbNodes().isEmpty();
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
