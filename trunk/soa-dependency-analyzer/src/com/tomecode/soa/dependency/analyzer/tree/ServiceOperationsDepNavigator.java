package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.suite10g.esb.EsbGrp;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.EsbSys;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.Operation;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Show the activities or services by dependencies
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ServiceOperationsDepNavigator extends ViewPart implements HideView, IDoubleClickListener, ISelectionChangedListener {

	public static final String ID = "view.serviceoperationsdepnavigator";

	private ServiceOperationsDepLabelProvider labelProvider;

	private ServiceOperationsDepContentProvider contentProvider;

	public final EmptyNode rootNode;
	private TreeViewer tree;

	public ServiceOperationsDepNavigator() {
		setTitleImage(ImageFactory.DEPENDNECY_BY_OPERATION_TREE);
		setTitleToolTip("Dependencies by operations");
		rootNode = new EmptyNode();
		labelProvider = new ServiceOperationsDepLabelProvider();
		contentProvider = new ServiceOperationsDepContentProvider();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SIMPLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.addDoubleClickListener(this);
		tree.setContentProvider(contentProvider);
		tree.addSelectionChangedListener(this);
		tree.setLabelProvider(labelProvider);
		ColumnViewerToolTipSupport.enableFor(tree);
	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	@Override
	public final void setFocus() {

	}

	/**
	 * show data
	 * 
	 * @param source
	 */
	public final void show(Object source) {
		if (source instanceof Ora10gBpelProject) {
			Ora10gBpelProject bpelProject = (Ora10gBpelProject) source;
			setDataToTree(bpelProject.getBpelOperations());
		} else if (source instanceof Service) {
			setDataToTree((Service) source);
		} else if (source instanceof OpenEsbBpelProcess) {
			setDataToTree((OpenEsbBpelProcess) source);
		} else if (source instanceof OraSB10gProject) {
			setDataToTree((OraSB10gProject) source);
		} else if (source instanceof Ora10gEsbProject) {
			setDataToTree((Ora10gEsbProject) source);
		} else if (source instanceof EsbSvc) {
			setDataToTree(((EsbSvc) source).getProject());
		} else {
			clearTree();
		}

	}

	private final void setDataToTree(Object data) {
		rootNode.set(data);
		tree.setInput(rootNode);
		tree.expandToLevel(3);
	}

	private final void clearTree() {
		rootNode.set(null);
		tree.setInput(null);
	}

	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		MultiWorkspace multiWorkspaceInTree = findMultiWorkspaceInTree();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			clearTree();
		}
	}

	private final MultiWorkspace findMultiWorkspaceInTree() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof Project) {
				return ((Project) objectInTree).getWorkpsace().getMultiWorkspace();
			}
		}
		return null;
	}

	private final Workspace findWorkspaceInTree() {
		if (rootNode.hasChildren()) {
			Object objectInTree = rootNode.getChildren()[0];
			if (objectInTree instanceof Ora10gBpelProject) {
				return ((Ora10gBpelProject) objectInTree).getWorkpsace();
			}
		}
		return null;
	}

	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceInTree();
		if (workspace.equals(workspaceInTree)) {
			clearTree();
		}
	}

	public final void showWithActivities() {
		contentProvider.setShowWithActivities(true);
		tree.refresh();
	}

	public final void showWithoutActivities() {
		contentProvider.setShowWithActivities(false);
		tree.refresh();
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Label provider for {@link ServiceOperationsDepNavigator}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	final class ServiceOperationsDepLabelProvider extends CellLabelProvider {

		public final Image getImage(Object element) {
			if (element instanceof BpelOperations) {
				return ImageFactory.ORACLE_10G_BPEL_PROCESS;
			} else if (element instanceof ImageFace) {
				return ((ImageFace) element).getImage();
			}
			return null;
		}

		@Override
		public final void update(ViewerCell cell) {
			cell.setText(cell.getElement().toString());
			cell.setImage(getImage(cell.getElement()));
		}

		public final String getToolTipText(Object element) {
			if (element instanceof ImageFace) {
				return ((ImageFace) element).getToolTip();
			}
			return element.toString();
		}
	}

	@Override
	public final void doubleClick(DoubleClickEvent event) {
		try {
			IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
			if (!selection.isEmpty()) {

				if (!(selection.getFirstElement() instanceof Operation)) {
				} else if (!(selection.getFirstElement() instanceof EsbSys)) {
				} else if (!(selection.getFirstElement() instanceof EsbGrp)) {
				} else {

					VisualGraphView visualGraphView = GuiUtils.getVisualGraphView();
					if (visualGraphView != null) {
						visualGraphView.showGraph(selection.getFirstElement());
					}
					BpelProcessStructureNavigator bpelStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
					if (bpelStructureNavigator != null) {
						bpelStructureNavigator.show(selection.getFirstElement());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "oops error:" + e.getMessage());
		}

	}

	@Override
	public final void selectionChanged(SelectionChangedEvent event) {
		try {
			IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
			if (!selection.isEmpty()) {
				BpelProcessStructureNavigator structureNavigator = GuiUtils.getBpelProcessStructureNavigator();
				if (structureNavigator != null) {
					structureNavigator.showActvityInTree(selection.getFirstElement());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "oops error:" + e.getMessage());
		}
	}

}
