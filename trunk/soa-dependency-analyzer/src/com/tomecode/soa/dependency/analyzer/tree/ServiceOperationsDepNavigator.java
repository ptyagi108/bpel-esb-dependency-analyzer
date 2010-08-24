package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.ora.suite10g.project.BpelProject;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class ServiceOperationsDepNavigator extends ViewPart {

	public static final String ID = "view.serviceoperationsdepnavigator";

	private ServiceOperationsDepLabelProvider labelProvider;

	private ServiceOperationsDepContentProvider contentProvider;

	public final EmptyNode emptyRootNode;
	private TreeViewer tree;

	public ServiceOperationsDepNavigator() {
		setTitleImage(ImageFactory.DEPENDNECY_BY_OPERATION_TREE);
		setTitleToolTip("Dependencies by operations");
		emptyRootNode = new EmptyNode();
		labelProvider = new ServiceOperationsDepLabelProvider();
		contentProvider = new ServiceOperationsDepContentProvider();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
	}

	@Override
	public final void setFocus() {

	}

	public final void showOperationDepenendecies(Object source) {
		if (source instanceof BpelProject) {
			BpelProject bpelProject = (BpelProject) source;
			emptyRootNode.set(bpelProject.getBpelOperations());
			tree.setInput(emptyRootNode);
			tree.expandAll();
		} else {
			tree.setInput(null);
		}
	}

}
