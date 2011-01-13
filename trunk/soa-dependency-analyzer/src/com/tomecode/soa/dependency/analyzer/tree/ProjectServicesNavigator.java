package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PopupMenuUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.tree.node.EmptyNode;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.PropertiesViewOsbAdapter;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolders;
import com.tomecode.soa.project.Project;

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
public final class ProjectServicesNavigator extends ViewPart implements HideView, IDoubleClickListener {

	public static final String ID = "view.projectServiceNavigator";

	/***
	 * selected object/tree item in tree
	 */
	private TreeItem selectedItem;

	private TreeViewer tree;

	private final ProjectServicesLabelProvider labelProvider;

	private final ProjectServicesContentProvider contentProvider;

	private final EmptyNode rootNode;

	public ProjectServicesNavigator() {
		super();
		setTitleToolTip("Project Services");
		rootNode = new EmptyNode();
		labelProvider = new ProjectServicesLabelProvider();
		contentProvider = new ProjectServicesContentProvider();
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLabelProvider(labelProvider);
		tree.setContentProvider(contentProvider);
		tree.addDoubleClickListener(this);
		ColumnViewerToolTipSupport.enableFor(tree);
		hookContextMenu();
	}

	@Override
	public final void setFocus() {

	}

	private final void hookContextMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenuServicesNavigator");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public final void menuAboutToShow(IMenuManager manager) {

				IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
				if (!selection.isEmpty()) {
					PopupMenuUtils.fillServicesNavigator(selection.getFirstElement(), manager);
				}
			}
		});

		Menu menu = menuManager.createContextMenu(tree.getControl());
		tree.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tree);
	}

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	public final void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	public final void show(Object source) {
		if (source == null) {
			clearTree();
		} else if (source instanceof Project) {
			tree.setInput(source);
		} else {
			clearTree();
		}
	}

	private final void clearTree() {
		rootNode.set(null);
		tree.setInput(rootNode);
	}

	@Override
	public final void doubleClick(DoubleClickEvent event) {
		clearSelectedItem();
		try {
			IStructuredSelection selection = (IStructuredSelection) tree.getSelection();
			if (!selection.isEmpty()) {
				Object selectedData = selection.getFirstElement();
				if (canShow(selectedData)) {
					VisualGraphView visualGraphView = GuiUtils.getVisualGraphView();
					if (visualGraphView != null) {
						visualGraphView.showGraph(selectedData);
						visualGraphView.selectInGraph(selectedData);
					}
					ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
					if (serviceOperationsDepNavigator != null) {
						serviceOperationsDepNavigator.show(selectedData);
					}
					ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
					if (serviceBusStructureNavigator != null) {
						serviceBusStructureNavigator.show(selectedData);
					}

					BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
					if (bpelProcessStructureNavigator != null) {
						bpelProcessStructureNavigator.show(selectedData);
					}
					PropertiesView propertiesView = GuiUtils.getPropertiesView();
					if (propertiesView != null) {
						propertiesView.show(selectedData);
					}

					PropertiesViewOsbAdapter propertiesViewOsbAdapter = GuiUtils.getPropertiesViewOsbAdapter();
					if (propertiesViewOsbAdapter != null) {
						propertiesViewOsbAdapter.show(selectedData);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error", "oops2 error:" + e.getMessage());
		}
	}

	private final boolean canShow(Object object) {
		if (object instanceof OraSB10gFolder) {
			return false;
		} else if (object instanceof OraSB10gFolders) {
			return false;
		}

		return true;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * {@link LabelProvider} for {@link ProjectServicesNavigator}
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	final class ProjectServicesLabelProvider extends CellLabelProvider {

		public final Image getImage(Object element) {
			if (element instanceof ImageFace) {
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

	/**
	 * select object in tree
	 * 
	 * @param object
	 */
	public final void selectInTree(Object object) {
		clearSelectedItem();
		TreeItem[] items = tree.getTree().getItems();
		selectItemInTree(items, object);
	}

	private final void selectItemInTree(TreeItem[] items, Object object) {
		for (TreeItem item : items) {
			if (object.equals(item.getData())) {
				item.setFont(GuiUtils.FONT_ITALIC_BOLD);
				item.setForeground(GuiUtils.COLOR_RED);
				item.setExpanded(true);
				tree.update(item, null);
				selectedItem = item;

				TreeItem i = item;
				while (i.getParentItem() != null) {
					i = i.getParentItem();
					i.setExpanded(true);
					tree.update(i, null);
				}

				return;
			} else {
				selectItemInTree(item.getItems(), object);
			}
		}
	}

	private final void clearSelectedItem() {
		if (selectedItem != null) {
			if (selectedItem.isDisposed()) {
				selectedItem = null;
			} else {
				selectedItem.setFont(null);
				selectedItem.setForeground(GuiUtils.COLOR_BLACK);
			}
		}
	}

}
