package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProcessStrukture;

/**
 * Content provider for {@link BpelProcessStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class BpelProcessStructureContentProvider implements ITreeContentProvider {

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof Ora10gBpelProcessStrukture) {
			return ((Ora10gBpelProcessStrukture) parent).getActivities().toArray();
		} else if (parent instanceof Activity) {
			return ((Activity) parent).getActivities().toArray();
		} else if (parent instanceof OpenEsbBpelProject) {
			return ((OpenEsbBpelProject) parent).getProcesses().toArray();
		} else if (parent instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) parent).getProcessStructureChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object paramObject) {
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).hasChildren();
		} else if (element instanceof Ora10gBpelProcessStrukture) {
			return !((Ora10gBpelProcessStrukture) element).getActivities().isEmpty();
		} else if (element instanceof Activity) {
			return !((Activity) element).getActivities().isEmpty();
		} else if (element instanceof OpenEsbBpelProject) {
			return !((OpenEsbBpelProject) element).getProcesses().isEmpty();
		} else if (element instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) element).hasProcessStructureChildren();
		}

		return false;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).getChildren();
		} else if (element instanceof Ora10gBpelProcessStrukture) {
			return ((Ora10gBpelProcessStrukture) element).getActivities().toArray();
		} else if (element instanceof Activity) {
			return ((Activity) element).getActivities().toArray();
		} else if (element instanceof OpenEsbBpelProject) {
			return ((OpenEsbBpelProject) element).getProcesses().toArray();
		} else if (element instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) element).getProcessStructureChildren();
		}
		return null;
	}

	@Override
	public final void dispose() {

	}

	@Override
	public final void inputChanged(Viewer paramViewer, Object paramObject1, Object paramObject2) {

	}

}
