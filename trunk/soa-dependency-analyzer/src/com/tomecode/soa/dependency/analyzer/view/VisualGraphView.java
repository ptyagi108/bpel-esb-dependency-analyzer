package com.tomecode.soa.dependency.analyzer.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction.LayoutActionType;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.icons.ImageUtils;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper.ZoomAction;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.suite10g.esb.EsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * View for Workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class VisualGraphView extends ViewPart {

	public static final String ID = "view.visualgraph";

	private Graph graph;

	private GraphViewer graphViewer;

	/**
	 * contains list of painted {@link GraphNode}
	 */
	private final List<GraphNode> graphNodes;
	/**
	 * contains list of painted {@link GraphConnection}
	 */
	private final List<GraphConnection> graphConnections;

	public VisualGraphView() {
		this.graphNodes = new ArrayList<GraphNode>();
		this.graphConnections = new ArrayList<GraphConnection>();
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		graphViewer = new GraphViewer(parent, SWT.NONE);
		graph = graphViewer.getGraphControl();
		// graph = new Graph(parent, SWT.NONE);
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(1), true);
		graph.addSelectionListener(new SelectionAdapter() {

			@Override
			public final void widgetSelected(SelectionEvent event) {
				List<?> list = ((Graph) event.widget).getSelection();
				if (!list.isEmpty()) {
					showPropertiesAboutSelectedNode(list.get(0));
				}
			}

		});

		initMenus();
	}

	/**
	 * create menu for view and create context menu
	 */
	private final void initMenus() {
		// create menu
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
		final GraphLayoutAction defaultAction = new GraphLayoutAction(LayoutActionType.SRING_LAYOUT);
		defaultAction.setChecked(true);
		menuManager.add(defaultAction);
		final GraphLayoutAction actionTreeLyout = new GraphLayoutAction(LayoutActionType.TREE_LAYOUT);
		menuManager.add(actionTreeLyout);
		final GraphLayoutAction actionVerticalLayout = new GraphLayoutAction(LayoutActionType.VERTICAL_LAYOUT);
		menuManager.add(actionVerticalLayout);
		final GraphLayoutAction actionRadialLayout = new GraphLayoutAction(LayoutActionType.RADIAL_LAYOUT);
		menuManager.add(actionRadialLayout);
		final GraphLayoutAction actionHorizontalTreeLayout = new GraphLayoutAction(LayoutActionType.HORIZONTAL_TREE_LAYOUT);
		menuManager.add(actionHorizontalTreeLayout);
		final GraphLayoutAction actionHorizontalLayout = new GraphLayoutAction(LayoutActionType.HORIZONTAL_LAYOUT);
		menuManager.add(actionHorizontalLayout);
		final GraphLayoutAction actionGridLayout = new GraphLayoutAction(LayoutActionType.GRID_LAYOUT);
		menuManager.add(actionGridLayout);
		final GraphLayoutAction actionDirectedLayout = new GraphLayoutAction(LayoutActionType.DIRECTED_LAYOUT);
		menuManager.add(actionDirectedLayout);

		MenuManager popupMenuManager = new MenuManager("#PopupMenu");
		popupMenuManager.createContextMenu(graph);

		popupMenuManager.add(new Separator());
		popupMenuManager.add(defaultAction);
		popupMenuManager.add(actionTreeLyout);
		popupMenuManager.add(actionVerticalLayout);
		popupMenuManager.add(actionRadialLayout);
		popupMenuManager.add(actionHorizontalTreeLayout);
		popupMenuManager.add(actionHorizontalLayout);
		popupMenuManager.add(actionGridLayout);
		popupMenuManager.add(actionDirectedLayout);
		popupMenuManager.add(new Separator());
		ZoomHelper zoomManager = new ZoomHelper(graph);
		menuManager.add(new Separator());

		Vector<Double> v = new Vector<Double>(zoomManager.getZoomActions().keySet());
		Collections.sort(v);

		for (Enumeration<Double> e = v.elements(); e.hasMoreElements();) {
			ZoomAction zoomAction = zoomManager.getZoomActions().get(e.nextElement());
			menuManager.add(zoomAction);
			popupMenuManager.add(zoomAction);
		}

		// create popupmenu
		graph.setMenu(popupMenuManager.getMenu());
		graph.setFocus();

		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(zoomManager.getZoomInAction());
		toolBarManager.add(zoomManager.getZoomOutAction());
		toolBarManager.add(zoomManager.getZoomResetAction());
	}

	/**
	 * show properties in {@link PropertiesView} about select object graph
	 * 
	 * @param object
	 */
	private final void showPropertiesAboutSelectedNode(Object object) {
		if (object instanceof GraphConnection) {
			GraphConnection connection = (GraphConnection) object;
			GuiUtils.getPropertiesView().showProperties(connection.getData());
		} else if (object instanceof GraphNode) {
			GraphNode graphNode = (GraphNode) object;
			GuiUtils.getPropertiesView().showProperties(graphNode.getData());
		}
	}

	@Override
	public void setFocus() {

	}

	/**
	 * show dependency graph
	 * 
	 * @param source
	 */
	public final void showGraph(Object source) {
		clearGraph();

		if (source instanceof MultiWorkspace) {
			MultiWorkspace multiWorkspace = (MultiWorkspace) source;
			createMultiWorkspaceAndWorkspaceGraph(multiWorkspace);
		} else if (source instanceof Workspace) {
			Workspace workspace = (Workspace) source;
			createWorkspaceAndProjectsGraph(workspace);
		} else if (source instanceof Project) {
			Project project = (Project) source;
			createProjectAndProjectGraph(project);
		}
		graph.applyLayout();
	}

	/**
	 * create dependency graph between {@link Project} and {@link Project}
	 * 
	 * @param project
	 */
	private final void createProjectAndProjectGraph(Project project) {
		if (project.getType() == ProjectType.ORACLE10G_BPEL) {
			BpelProject bpelProject = (BpelProject) project;
			applyDependencies(bpelProject, bpelProject.getPartnerLinkBindings());
		}
	}

	/**
	 * create dependency graph where source object is {@link BpelProject} and
	 * destination is list of dependencies project
	 * 
	 * @param bpelProject
	 *            source {@link BpelProject}
	 * @param dependencyProjects
	 *            list of dependency project
	 */
	private final void applyDependencies(BpelProject bpelProject, List<PartnerLinkBinding> partnerLinkBindings) {
		int curveDepth = 30;
		GraphNode source = createNode(bpelProject.getName(), ImageFactory.ORACLE_10G_BPEL_PROCESS, bpelProject);
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			// BPEL dependency
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				BpelProject project = partnerLinkBinding.getDependencyBpelProject();
				// self dependency
				if (bpelProject.equals(project)) {
					GraphConnection connection = createConnection(source, source, partnerLinkBinding);
					connection.setCurveDepth(curveDepth);
					curveDepth++;
				} else {
					GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
					createConnection(source, destination, null);
				}
			}
			// ESB project dependency
			else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				EsbProject esbProject = partnerLinkBinding.getDependencyEsbProject();
				GraphNode destination = createNode(esbProject.getName(), ImageFactory.ORACLE_10G_ESB, esbProject);
				createConnection(source, destination, partnerLinkBinding);
			}
			// Unknown project dependency
			else if (partnerLinkBinding.getUnknownProject() != null) {

			}
		}
	}

	/**
	 * create dependency graph between {@link Workspace} and {@link Project}
	 * 
	 * @param workspace
	 */
	private final void createWorkspaceAndProjectsGraph(Workspace workspace) {
		if (workspace instanceof Ora10gWorkspace) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;
			GraphNode source = createNode(workspace.getName(), ImageFactory.WORKSPACE, ora10gWorkspace);
			for (Project project : ora10gWorkspace.getProjects()) {
				GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
				createConnection(source, destination, project);
			}

		} else if (workspace instanceof OpenEsbWorkspace) {
			OpenEsbWorkspace openEsbWorkspace = (OpenEsbWorkspace) workspace;
			GraphNode source = createNode(workspace.getName(), ImageFactory.WORKSPACE, openEsbWorkspace);
			for (Project project : openEsbWorkspace.getProjects()) {
				GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
				createConnection(source, destination, project);
			}
		}
	}

	/**
	 * remove all {@link GraphConnection} and {@link GraphNode} from
	 * {@link #graph}
	 */
	private final void clearGraph() {
		for (GraphConnection connection : graphConnections) {
			connection.dispose();
		}
		for (GraphNode node : graphNodes) {
			node.dispose();
		}

		graphConnections.clear();
		graphNodes.clear();
		graph.applyLayout();
	}

	/**
	 * show dependency graph for {@link MultiWorkspace} and {@link Workspace}
	 * 
	 * @param multiWorkspace
	 */
	private final void createMultiWorkspaceAndWorkspaceGraph(MultiWorkspace multiWorkspace) {
		GraphNode multiWorkspaceNode = createNode(multiWorkspace.getName(), ImageUtils.getMultiWorkspaceImage(multiWorkspace), multiWorkspace);

		if (multiWorkspace instanceof Ora10gMultiWorkspace) {
			Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
			for (Ora10gWorkspace workspace : ora10gMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), ImageFactory.WORKSPACE, workspace);
				createConnection(multiWorkspaceNode, workspaceNode, null);
			}
		} else if (multiWorkspace instanceof OpenEsbMultiWorkspace) {
			OpenEsbMultiWorkspace esbMultiWorkspace = (OpenEsbMultiWorkspace) multiWorkspace;
			for (OpenEsbWorkspace workspace : esbMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), ImageFactory.WORKSPACE, workspace);
				createConnection(multiWorkspaceNode, workspaceNode, null);
			}
		}

	}

	/**
	 * create connection between source and target {@link GraphNode}
	 * 
	 * @param source
	 * @param destination
	 * @param data
	 *            business object
	 * @return {@link GraphConnection} from source and destination
	 */
	private final GraphConnection createConnection(GraphNode source, GraphNode destination, Object data) {
		GraphConnection connection = new GraphConnection(graph, SWT.ARROW_DOWN, source, destination);
		connection.setData(data);
		if (data != null) {
			connection.setText(data.toString());
		}
		graphConnections.add(connection);
		return connection;
	}

	/**
	 * create {@link GraphNode}
	 * 
	 * @param name
	 *            node title
	 * @param image
	 *            node image
	 * @param data
	 *            node business object
	 * @return new {@link GraphNode}
	 */
	private final GraphNode createNode(String name, Image image, Object data) {
		GraphNode graphNode = new GraphNode(graph, SWT.NONE, name, image);
		graphNode.setData(data);
		graphNodes.add(graphNode);
		return graphNode;
	}

	// /**
	// * create {@link GraphNode}
	// *
	// * @param name
	// * @param image
	// * @param data
	// * @param backgroundColor
	// * @return
	// */
	// private final GraphNode createNode(String name, Image image, Object data,
	// Color backgroundColor) {
	// GraphNode graphNode = createNode(name, image, data);
	// graphNode.setBackgroundColor(backgroundColor);
	// return graphNode;
	// }

	/**
	 * // * change layout algorithm {@link #graph}
	 */
	public final void changeLayout(AbstractLayoutAlgorithm layoutAlgorithm, LayoutActionType type) {
		graph.setLayoutAlgorithm(layoutAlgorithm, true);
		IContributionItem[] items = getViewSite().getActionBars().getMenuManager().getItems();
		for (IContributionItem item : items) {
			GraphLayoutAction action = (GraphLayoutAction) ((ActionContributionItem) item).getAction();
			if (action.getType() != type) {
				action.setChecked(false);
			}
		}
	}

}
