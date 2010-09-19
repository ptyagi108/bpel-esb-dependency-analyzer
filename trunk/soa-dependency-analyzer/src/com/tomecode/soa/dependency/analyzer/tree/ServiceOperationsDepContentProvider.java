package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.dependencies.BpelActivityDependency;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.dependnecies.OsbActivityDependency;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.Operation;

/**
 * 
 * Content provider for {@link ServiceOperationsDepNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class ServiceOperationsDepContentProvider implements ITreeContentProvider {

	private boolean showWithActivities;

	/**
	 * Constructor
	 */
	ServiceOperationsDepContentProvider() {
		this.showWithActivities = true;
	}

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof Operation) {
			return ((Operation) parent).getChildren();
		} else if (parent instanceof BpelOperations) {
			return ((BpelOperations) parent).getOperations().toArray();
		} else if (parent instanceof Service) {
			return ((Service) parent).getActivityDependency().getActivityDependencies().toArray();
		} else if (parent instanceof OsbActivityDependency) {
			return ((OsbActivityDependency) parent).getServices().toArray();
		} else if (parent instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) parent).getActivityDependencies().toArray();
		} else if (parent instanceof BpelActivityDependency) {
			return ((BpelActivityDependency) parent).getBpelProcesses().toArray();
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
		} else if (element instanceof Service) {
			return !((Service) element).getActivityDependency().getActivityDependencies().isEmpty();
		} else if (element instanceof OsbActivityDependency) {
			return !((OsbActivityDependency) element).getServices().isEmpty();
		} else if (element instanceof OpenEsbBpelProcess) {
			return !((OpenEsbBpelProcess) element).getActivityDependencies().isEmpty();
		} else if (element instanceof BpelActivityDependency) {
			return !((BpelActivityDependency) element).getBpelProcesses().isEmpty();
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
		} else if (element instanceof Service) {
			return ((Service) element).getActivityDependency().getActivityDependencies().toArray();
		} else if (element instanceof OsbActivityDependency) {
			return ((OsbActivityDependency) element).getServices().toArray();
		} else if (element instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) element).getActivityDependencies().toArray();
		} else if (element instanceof BpelActivityDependency) {
			return ((BpelActivityDependency) element).getBpelProcesses().toArray();
		}
		return null;
	}

	/**
	 * @param showWithActivities
	 *            the showWithActivities to set
	 */
	public final void setShowWithActivities(boolean showWithActivities) {
		this.showWithActivities = showWithActivities;
	}

	@Override
	public final void dispose() {

	}

	@Override
	public final void inputChanged(Viewer paramViewer, Object paramObject1, Object paramObject2) {

	}

}
