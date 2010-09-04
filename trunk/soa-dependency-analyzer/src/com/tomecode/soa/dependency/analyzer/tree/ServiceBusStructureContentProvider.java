package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.ProxyStructure;

/**
 * Content provider for {@link ServiceBusStructureNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class ServiceBusStructureContentProvider implements ITreeContentProvider {

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof EmptyNode) {
			return ((EmptyNode) parent).getChildren();
		} else if (parent instanceof ProxyStructure) {
			return ((ProxyStructure) parent).getActivities().toArray();
		} else if (parent instanceof OsbActivity) {
			return ((OsbActivity) parent).getActivities().toArray();
		} else if (parent instanceof OraSB10gProject) {
			return ((OraSB10gProject) parent).getProxyServices().toArray();
		} else if (parent instanceof Proxy) {
			return ((Proxy) parent).getProxyStructure().getActivities().toArray();
		}
		return null;
	}

	@Override
	public final Object getParent(Object element) {
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).hasChildren();
		} else if (element instanceof ProxyStructure) {
			return !((ProxyStructure) element).getActivities().isEmpty();
		} else if (element instanceof OsbActivity) {
			return !((OsbActivity) element).getActivities().isEmpty();
		} else if (element instanceof OraSB10gProject) {
			return !((OraSB10gProject) element).getProxyServices().isEmpty();
		} else if (element instanceof Proxy) {
			return !((Proxy) element).getProxyStructure().getActivities().isEmpty();
		}
		return false;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof EmptyNode) {
			return ((EmptyNode) element).getChildren();
		} else if (element instanceof ProxyStructure) {
			return ((ProxyStructure) element).getActivities().toArray();
		} else if (element instanceof OsbActivity) {
			return ((OsbActivity) element).getActivities().toArray();
		} else if (element instanceof OraSB10gProject) {
			return ((OraSB10gProject) element).getProxyServices().toArray();
		} else if (element instanceof Proxy) {
			return ((Proxy) element).getProxyStructure().getActivities().toArray();
		}
		return null;
	}

	@Override
	public final void dispose() {

	}

	@Override
	public final void inputChanged(Viewer arg0, Object arg1, Object arg2) {

	}

}
