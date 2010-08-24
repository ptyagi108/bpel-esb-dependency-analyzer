package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.ora.suite10g.project.BpelProject;

/**
 * Show BPEL process structure (tree structure of activities)
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProcessStructureNavigator extends ViewPart {

	public static final String ID = "view.bpelprocessstructurenavigator";

	private final BpelProcessStructureContentProvider contentProvider;

	private final BpelProcessStructureLabelProvider labelProvider;

	private final EmptyNode emptyRootNode;

	private TreeViewer tree;

	public BpelProcessStructureNavigator() {
		setTitleImage(ImageFactory.BPEL_PROCESS_STRUCTURE_TREE);
		setTitleToolTip("BPEL process structure");
		emptyRootNode = new EmptyNode();
		contentProvider = new BpelProcessStructureContentProvider();
		labelProvider = new BpelProcessStructureLabelProvider();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLabelProvider(labelProvider);
		tree.setContentProvider(contentProvider);
	}

	@Override
	public final void setFocus() {

	}

	public final void showProcess(Object source) {
		if (source instanceof BpelProject) {
			emptyRootNode.set(((BpelProject) source).getBpelProcessStrukture());
			tree.setInput(emptyRootNode);
			tree.expandAll();
		} else if (source instanceof OpenEsbBpelProject) {
			// TODO: is ok?
			// OpenEsbBpelProject project = ((OpenEsbBpelProject) source);
			emptyRootNode.set(source);
			tree.setInput(emptyRootNode);
			tree.expandAll();
		} else {
			tree.setInput(null);
		}
	}

}
