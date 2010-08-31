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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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

import com.tomecode.soa.dependency.analyzer.gui.actions.GraphExpanderAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RefreshGraphLayout;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphExpanderAction.ExpandChangeListener;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction.LayoutActionType;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.icons.ImageUtils;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper.ZoomAction;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.PartnerLink;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.suite10g.esb.EsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.services.BpelProcess;
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

	private GraphViewer graphViewer;

	/**
	 * if {@link #isExpandInGraph} is true then this objects contains all
	 * expanded object which was selected with doubleClick
	 */
	private final List<Object> expandedInGraphObjects;
	/**
	 * if <b>true</b> then expand in graph if <b>false</b> then after double
	 * click show dependencies in new graph
	 * 
	 * 
	 * this field is controlled from {@link GraphExpanderAction}
	 */
	private boolean isExpandInGraph;
	// private BrowserManager browserManager;
	/**
	 * contains list of painted {@link GraphNode}
	 */
	private final List<GraphNode> graphNodes;
	/**
	 * contains list of painted {@link GraphConnection}
	 */
	private final List<GraphConnection> graphConnections;

	public VisualGraphView() {
		setTitleToolTip("Dependency Graph");
		setTitleImage(ImageFactory.GRAPH_VIEW);
		this.graphNodes = new ArrayList<GraphNode>();
		this.graphConnections = new ArrayList<GraphConnection>();
		this.expandedInGraphObjects = new ArrayList<Object>();
		this.isExpandInGraph = true;
	}

	@Override
	public final void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		graphViewer = new GraphViewer(parent, SWT.NONE);
		Graph graph = graphViewer.getGraphControl();
		// browserManager = new BrowserManager(this);
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

		graph.addMouseListener(new MouseAdapter() {
			public final void mouseDoubleClick(MouseEvent e) {
				List<?> list = graphViewer.getGraphControl().getSelection();
				if (!list.isEmpty()) {
					if (isExpandInGraph) {
						expandSelectedNodeInExistsGraph(list.get(0));
					} else {
						expandedInGraphObjects.clear();
						expandSelectedNodeInNewGraph(list.get(0), null);
					}
				}
			}
		});

		initMenus(graph);
	}

	/**
	 * 
	 * expand selected object in graph
	 * 
	 * @param selectedObject
	 */
	public final void expandSelectedNodeInExistsGraph(Object selectedObject) {
		if (selectedObject instanceof GraphNode) {
			if (!expandedInGraphObjects.contains(selectedObject)) {
				expandedInGraphObjects.add(selectedObject);
				// GuiUtils.getWorkspacesNavigator().showInTree(((GraphNode)
				// selectedObject).getData());
				expandSelectedNodeInNewGraph(selectedObject, (GraphNode) selectedObject);

				graphViewer.getGraphControl().applyLayout();
			}
		}
	}

	/**
	 * if double click on selected element then go to inside
	 * 
	 * @param list
	 */
	private final void expandSelectedNodeInNewGraph(Object selectedObject, GraphNode existsSource) {
		if (selectedObject instanceof GraphNode) {
			GraphNode selctedGraphNode = (GraphNode) selectedObject;
			if (selctedGraphNode.getData() instanceof Workspace) {
				Workspace workspace = (Workspace) selctedGraphNode.getData();
				clearGraph();
				createWorkspaceAndProjectsGraph(workspace, existsSource);
			} else if (selctedGraphNode.getData() instanceof Project) {
				Project project = (Project) selctedGraphNode.getData();
				clearGraph();
				createProjectAndProjectGraph(project, existsSource);
			} else if (selctedGraphNode.getData() instanceof BpelProcess) {
				BpelProcess bpelProcess = (BpelProcess) selctedGraphNode.getData();
				clearGraph();
				createProcessAndProcessGraph(bpelProcess, existsSource);
			}

		}
	}

	/**
	 * create menu for view and create context menu
	 */
	private final void initMenus(Graph graph) {
		// create menu
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
		RefreshGraphLayout refreshGraphLayout = new RefreshGraphLayout(graphViewer.getGraphControl());

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

		menuManager.add(new Separator());
		menuManager.add(refreshGraphLayout);

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

		popupMenuManager.add(refreshGraphLayout);
		menuManager.add(new Separator());

		ZoomHelper zoomManager = new ZoomHelper(graph);

		Vector<Double> v = new Vector<Double>(zoomManager.getZoomActions().keySet());
		Collections.sort(v);

		for (Enumeration<Double> e = v.elements(); e.hasMoreElements();) {
			ZoomAction zoomAction = zoomManager.getZoomActions().get(e.nextElement());
			menuManager.add(zoomAction);
			popupMenuManager.add(zoomAction);
		}

		// create popupmenu
		graph.setMenu(popupMenuManager.getMenu());

		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(refreshGraphLayout);

		GraphExpanderAction graphExpanderAction = new GraphExpanderAction();
		toolBarManager.add(graphExpanderAction);

		// change listener
		graphExpanderAction.expandChangeListener(new ExpandChangeListener() {

			@Override
			public final void change(boolean isExpandInNewGraph) {
				isExpandInGraph = isExpandInNewGraph;
			}
		});

		// toolBarManager.add(browserManager.getBackAction());
		// toolBarManager.add(browserManager.getForwardAction());

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
			GuiUtils.getBpelProcessStructureNavigator().showProcess(graphNode.getData());
			GuiUtils.getServiceOperationsDepNavigator().showOperationDepenendecies(graphNode.getData());
		}
	}

	@Override
	public final void setFocus() {

	}

	/**
	 * show dependency graph
	 * 
	 * @param source
	 */

	public final void showGraph(Object source, boolean addToBrowser) {
		boolean backup = isExpandInGraph;
		isExpandInGraph = false;
		expandedInGraphObjects.clear();
		clearGraph();

		// if (addToBrowser) {
		// browserManager.add(source);
		// }
		if (source instanceof MultiWorkspace) {
			MultiWorkspace multiWorkspace = (MultiWorkspace) source;
			createMultiWorkspaceAndWorkspaceGraph(multiWorkspace);
		} else if (source instanceof Workspace) {
			Workspace workspace = (Workspace) source;
			createWorkspaceAndProjectsGraph(workspace, null);
		} else if (source instanceof Project) {
			Project project = (Project) source;
			createProjectAndProjectGraph(project, null);
		} else if (source instanceof BpelProcess) {
			BpelProcess bpelProcess = (BpelProcess) source;
			createProcessAndProcessGraph(bpelProcess, null);
		}

		graphViewer.getGraphControl().applyLayout();

		isExpandInGraph = backup;
	}

	private final void createProcessAndProcessGraph(BpelProcess process, GraphNode existsSource) {
		if (process instanceof OpenEsbBpelProcess) {
			OpenEsbBpelProcess openEsbBpelProcess = (OpenEsbBpelProcess) process;
			applyDependnecies(openEsbBpelProcess, existsSource);
		}
	}

	/**
	 * create {@link GraphNode} and {@link GraphConnection} for
	 * {@link ProjectType#OPEN_ESB_BPEL}
	 * 
	 * @param bpelProcess
	 * @param existsSource
	 */
	private final void applyDependnecies(OpenEsbBpelProcess bpelProcess, GraphNode existsSource) {
		GraphNode source = existsSource == null ? createNode(bpelProcess.getName(), ImageFactory.OPEN_ESB_BPEL_PROCESS, bpelProcess) : existsSource;
		for (PartnerLink partnerLink : bpelProcess.getPartnerLinks()) {
			for (OpenEsbBpelProcess depBpelProcess : partnerLink.getDependenciesProcesses()) {

				GraphNode existsNode = findDataInNodes(depBpelProcess);
				if (existsNode != null) {

					GraphConnection existsConnection = findConnection(source, existsNode);
					if (existsConnection != null) {
						existsConnection.setCurveDepth(30);
						GraphConnection connection = createConnection(source, existsNode, partnerLink);
						connection.setCurveDepth(10);
					} else {
						createConnection(source, existsNode, partnerLink);
					}

				} else {
					GraphNode destination = createNode(depBpelProcess.getName(), ImageFactory.OPEN_ESB_BPEL_PROCESS, depBpelProcess);
					createConnection(source, destination, partnerLink);
				}
			}
		}
	}

	/**
	 * find {@link GraphConnection} in list of {@link #graphConnections} by
	 * source and destionation
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	private final GraphConnection findConnection(GraphNode source, GraphNode destination) {
		for (GraphConnection connection : graphConnections) {
			if (connection.getSource().equals(destination) && connection.getDestination().equals(source)) {
				return connection;
			}
		}
		return null;
	}

	/**
	 * create dependency graph between {@link Project} and {@link Project}
	 * 
	 * @param project
	 * @param existsSource
	 *            if not null then create connection from exists graph node
	 */
	private final void createProjectAndProjectGraph(Project project, GraphNode existsSource) {
		if (project.getType() == ProjectType.ORACLE10G_BPEL) {
			BpelProject bpelProject = (BpelProject) project;
			applyDependencies(bpelProject, bpelProject.getPartnerLinkBindings(), existsSource);
		} else if (project.getType() == ProjectType.OPEN_ESB_BPEL) {
			OpenEsbBpelProject bpelProject = (OpenEsbBpelProject) project;
			applyDependenciesOpenEsbBpel(bpelProject, bpelProject.getProcesses(), existsSource);
		}
	}

	/**
	 * create dependency graph between {@link Project} and {@link Process}
	 * 
	 * @param bpelProject
	 * @param processes
	 * @param existsSource
	 */
	private final void applyDependenciesOpenEsbBpel(OpenEsbBpelProject bpelProject, List<OpenEsbBpelProcess> processes, GraphNode existsSource) {
		GraphNode source = existsSource == null ? createNode(bpelProject.getName(), ImageFactory.OPEN_ESB_BPEL_PROCESS, bpelProject) : existsSource;
		for (OpenEsbBpelProcess openEsbBpelProcess : processes) {
			GraphNode destination = createNode(openEsbBpelProcess.getName(), ImageFactory.OPEN_ESB_BPEL_PROCESS, openEsbBpelProcess);
			createConnection(source, destination, openEsbBpelProcess);
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
	 * @param existSource
	 */
	private final void applyDependencies(BpelProject bpelProject, List<PartnerLinkBinding> partnerLinkBindings, GraphNode existSource) {
		int curveDepth = 30;
		GraphNode source = existSource == null ? createNode(bpelProject.getName(), ImageFactory.ORACLE_10G_BPEL_PROCESS, bpelProject) : existSource;
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
					if (existSource != null) {
						// create connection to exists object in graph
						GraphNode existsDestination = findDataInNodes(project);
						createConnection(source, existsDestination, partnerLinkBinding);
					} else {
						// new object in graph
						GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
						createConnection(source, destination, partnerLinkBinding);
					}
				}
			}
			// ESB project dependency
			else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				EsbProject esbProject = partnerLinkBinding.getDependencyEsbProject();

				if (existSource != null) {
					// create connection to exists object in graph
					GraphNode existsDestination = findDataInNodes(esbProject);
					createConnection(source, existsDestination, partnerLinkBinding);
				} else {
					// new object in graph
					GraphNode destination = createNode(esbProject.getName(), ImageFactory.ORACLE_10G_ESB, esbProject);
					createConnection(source, destination, partnerLinkBinding);
				}
			}
			// Unknown project dependency
			else if (partnerLinkBinding.getUnknownProject() != null) {
				UnknownProject project = partnerLinkBinding.getUnknownProject();

				if (existSource != null) {
					// create connection to exists object in graph
					GraphNode destination = findDataInNodes(project.getName());
					createConnection(source, destination, partnerLinkBinding);
				} else {
					// new object in graph
					GraphNode destination = createNode(project.getName(), ImageFactory.ORACLE_10G_UNKNOWN, project);
					createConnection(source, destination, partnerLinkBinding);
				}
			}
		}
	}

	/**
	 * find {@link GraphNode} in graph by data
	 * 
	 * @param data
	 * @return
	 */
	private final GraphNode findDataInNodes(Object data) {
		for (GraphNode graphNode : graphNodes) {
			if (graphNode.getData() != null) {
				if (graphNode.getData().equals(data)) {
					return graphNode;
				}
			}
		}
		return null;
	}

	/**
	 * create dependency graph between {@link Workspace} and {@link Project}
	 * 
	 * @param workspace
	 *            root graph node
	 * 
	 * @param existSource
	 *            if not null then create connection from exists source
	 */
	private final void createWorkspaceAndProjectsGraph(Workspace workspace, GraphNode existSource) {
		if (workspace instanceof Ora10gWorkspace) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;
			GraphNode source = existSource == null ? createNode(workspace.getName(), ImageFactory.WORKSPACE, ora10gWorkspace) : existSource;
			for (Project project : ora10gWorkspace.getProjects()) {
				GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
				createConnection(source, destination, project);
			}

		} else if (workspace instanceof OpenEsbWorkspace) {
			OpenEsbWorkspace openEsbWorkspace = (OpenEsbWorkspace) workspace;
			GraphNode source = existSource == null ? createNode(workspace.getName(), ImageFactory.WORKSPACE, openEsbWorkspace) : existSource;
			for (Project project : openEsbWorkspace.getProjects()) {

				GraphNode existsProjectGraphNode = findDataInNodes(project);
				if (existsProjectGraphNode != null && existsConnection(source, existsProjectGraphNode)) {

				} else {
					GraphNode destination = createNode(project.getName(), ImageUtils.getProjectImage(project), project);
					createConnection(source, destination, project);
				}
			}
		}
	}

	/**
	 * check,
	 * 
	 * @param source
	 * @param existsProjectGraphNode
	 * @return
	 */
	private final boolean existsConnection(GraphNode source, GraphNode existsProjectGraphNode) {
		for (GraphConnection connection : graphConnections) {
			if (source.equals(connection.getSource()) && existsProjectGraphNode.equals(connection.getDestination())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * remove all {@link GraphConnection} and {@link GraphNode} from
	 * {@link #graph}
	 */
	private final void clearGraph() {
		if (!isExpandInGraph) {
			for (GraphConnection connection : graphConnections) {
				connection.dispose();
			}
			for (GraphNode node : graphNodes) {
				node.dispose();
			}

			graphConnections.clear();
			graphNodes.clear();
			graphViewer.getGraphControl().applyLayout();
		}
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
				createConnection(multiWorkspaceNode, workspaceNode, workspace);
			}
		} else if (multiWorkspace instanceof OpenEsbMultiWorkspace) {
			OpenEsbMultiWorkspace esbMultiWorkspace = (OpenEsbMultiWorkspace) multiWorkspace;
			for (OpenEsbWorkspace workspace : esbMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), ImageFactory.WORKSPACE, workspace);
				createConnection(multiWorkspaceNode, workspaceNode, workspace);
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
		GraphConnection connection = new GraphConnection(graphViewer.getGraphControl(), SWT.ARROW_DOWN, source, destination);
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
		GraphNode graphNode = new GraphNode(graphViewer.getGraphControl(), SWT.NONE, name, image);
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
		graphViewer.getGraphControl().setLayoutAlgorithm(layoutAlgorithm, true);
		IContributionItem[] items = getViewSite().getActionBars().getMenuManager().getItems();
		for (IContributionItem item : items) {
			GraphLayoutAction action = (GraphLayoutAction) ((ActionContributionItem) item).getAction();
			if (action.getType() != type) {
				action.setChecked(false);
			} else {
				action.setChecked(true);
			}
		}
	}

}
