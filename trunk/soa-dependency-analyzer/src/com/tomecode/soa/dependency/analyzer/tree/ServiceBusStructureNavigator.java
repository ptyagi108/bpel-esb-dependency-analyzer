package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Proxy;

/**
 * 
 * Show structure of selected service bus
 * 
 * @author Tomas Frastia
 * 
 */
public final class ServiceBusStructureNavigator extends ViewPart {

	public static final String ID = "view.servicebusstructurenavigator";

	private final ServiceBusStructureContentProvider contentProvider;

	private final EmptyNode emptyRootNode;

	private TreeViewer tree;

	public ServiceBusStructureNavigator() {
		emptyRootNode = new EmptyNode();
		contentProvider = new ServiceBusStructureContentProvider();
		setTitleToolTip("Service Bus - (Proxy or SplitJoin) Structure");
	}

	@Override
	public void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(contentProvider);
	}

	@Override
	public void setFocus() {

	}

	public final void showStructure(Object source) {
		if (source == null) {
			emptyRootNode.set(null);
			tree.setInput(emptyRootNode);
		} else {
			if (source instanceof Proxy) {
				emptyRootNode.set(((Proxy) source).getProxyStructure());
				tree.setInput(emptyRootNode);
				tree.expandAll();
			} else if (source instanceof OraSB10gProject) {
				emptyRootNode.set((OraSB10gProject) source);
				tree.setInput(emptyRootNode);
				tree.expandAll();
			} else {
				emptyRootNode.set(null);
				tree.setInput(emptyRootNode);
			}
		}

	}
}
