package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.oracle10g.project.BpelOperations;
import com.tomecode.soa.oracle10g.project.Operation;

/**
 * 
 * Content provider for {@link ServiceOperationsDepNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class ServiceOperationsDepContentProvider implements ITreeContentProvider {

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof Operation) {
			return ((Operation) parent).getChildren();
		} else if (parent instanceof BpelOperations) {
			return ((BpelOperations) parent).getOperations().toArray();
		}
		return null;
	}

	@Override
	public final Object getParent(Object paramObject) {
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).hasChildren();
		} else if (element instanceof Operation) {
			return ((Operation) element).hasChildren();
		} else if (element instanceof BpelOperations) {
			return !((BpelOperations) element).getOperations().isEmpty();
		}
		return false;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).getChildren();
		} else if (element instanceof Operation) {
			return ((Operation) element).getChildren();
		} else if (element instanceof BpelOperations) {
			return ((BpelOperations) element).getOperations().toArray();
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
