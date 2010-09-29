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
import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.tomecode.soa.dependency.analyzer.gui.actions.ExportGraphToImageAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphExpanderAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.RefreshGraphLayout;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphExpanderAction.ExpandChangeListener;
import com.tomecode.soa.dependency.analyzer.gui.actions.GraphLayoutAction.LayoutActionType;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper.ZoomAction;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.PartnerLink;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownFile;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
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
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * View for Workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
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
				} else {
					GuiUtils.getServiceBusStructureNavigator().show(null);
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
			GraphNode selectedGraphNode = (GraphNode) selectedObject;
			if (selectedGraphNode.getData() instanceof Workspace) {
				Workspace workspace = (Workspace) selectedGraphNode.getData();
				clearGraph();
				createWorkspaceAndProjectsGraph(workspace, existsSource);
			} else if (selectedGraphNode.getData() instanceof Project) {
				Project project = (Project) selectedGraphNode.getData();
				clearGraph();
				createProjectAndProjectGraph(project, existsSource);
			} else if (selectedGraphNode.getData() instanceof BpelProcess) {
				BpelProcess bpelProcess = (BpelProcess) selectedGraphNode.getData();
				clearGraph();
				createProcessAndProcessGraph(bpelProcess, existsSource);
			} else if (selectedGraphNode.getData() instanceof Service) {
				Service service = (Service) selectedGraphNode.getData();
				clearGraph();
				createProxyAndService(service, existsSource);
			}
		}
	}

	/**
	 * create menu for view and create context menu
	 */
	private final void initMenus(final Graph graph) {
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
		ExportGraphToImageAction exportGraphToImageAction = new ExportGraphToImageAction(graph);
		menuManager.add(exportGraphToImageAction);
		menuManager.add(new Separator());
		menuManager.add(refreshGraphLayout);

		MenuManager popupMenuManager = new MenuManager("#PopupMenu");
		popupMenuManager.createContextMenu(graph);

		popupMenuManager.add(exportGraphToImageAction);

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
			GuiUtils.getPropertiesView().show(connection.getData());
			if (connection.getData() != null) {
				ProjectStructureNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
				if (projectStructureNavigator != null) {
					projectStructureNavigator.showProjectFiles(connection.getData());
				}
			}

			GuiUtils.getServiceBusStructureNavigator().show(connection.getData());
		} else if (object instanceof GraphNode) {
			GraphNode graphNode = (GraphNode) object;

			PropertiesView propertiesView = GuiUtils.getPropertiesView();
			if (propertiesView != null) {
				propertiesView.show(graphNode.getData());
			}
			BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
			if (bpelProcessStructureNavigator != null) {
				bpelProcessStructureNavigator.showProcessStructure(graphNode.getData());
			}
			ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceBusStructureNavigator.show(graphNode.getData());
			}
			ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
			if (serviceOperationsDepNavigator != null) {
				serviceOperationsDepNavigator.show(graphNode.getData());
			}
			ProjectStructureNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
			if (projectStructureNavigator != null) {
				projectStructureNavigator.showProjectFiles(graphNode.getData());
			}
		}
	}

	@SuppressWarnings("restriction")
	@Override
	public final void setFocus() {
		ViewSite site = (ViewSite) getSite();
		if (site.getSecondaryId() == null) {
			GuiUtils.setActivateViewId(0);
		} else {
			int i = Integer.parseInt(site.getSecondaryId());
			GuiUtils.setActivateViewId(i);
		}
	}

	public final void dispose() {
		GuiUtils.dropInstanceVisualGraph();
		super.dispose();
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

	/**
	 * remove all objects from graph
	 */
	public final void clearContentFromGraph() {
		boolean backup = isExpandInGraph;
		isExpandInGraph = false;
		expandedInGraphObjects.clear();
		clearGraph();
		graphViewer.getGraphControl().applyLayout();

		isExpandInGraph = backup;
	}

	/**
	 * create dependencies between PROXY and target services dependencies
	 * 
	 * @param proxy
	 * @param existsSource
	 */
	private final void createProxyAndService(Service service, GraphNode existsSource) {
		GraphNode source = existsSource != null ? existsSource : createNode(service.getName(), service.getImage(), service);
		for (ServiceDependency serviceDependency : service.getServiceDependencies().getDependnecies()) {

			if (serviceDependency.getServices().isEmpty()) {
				if (findUnknownConnection(source, serviceDependency.getRefPath()) == null) {
					GraphNode unknowNode = createNode(serviceDependency.getRefPath(), ImageFactory.UNKNOWN, serviceDependency);
					createConnection(source, unknowNode, serviceDependency, false);
				}
			} else {
				GraphNode destination = findDataInNodes(serviceDependency.getServices().get(0));
				if (destination == null) {
					Service target = serviceDependency.getServices().get(0);
					destination = createNode(target.getName(), target.getImage(), target);
					createConnection(source, destination, serviceDependency, false);
				} else {
					if (findConnection(source, destination) == null) {
						createConnection(source, destination, serviceDependency, false);
					}
				}
			}
		}
	}

	/**
	 * find connection
	 * 
	 * @param source
	 * @param refPath
	 * @return
	 */
	private final GraphConnection findUnknownConnection(GraphNode source, String refPath) {
		for (GraphConnection connection : graphConnections) {
			if (connection.getSource().getData().equals(source.getData())) {
				if (connection.getDestination().getData() != null && connection.getDestination().getData().toString().equals(refPath)) {
					return connection;
				}
			}
		}
		return null;
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

		GraphNode source = existsSource == null ? createNode(bpelProcess.getName(), bpelProcess.getImage(), bpelProcess) : existsSource;
		for (PartnerLink partnerLink : bpelProcess.getPartnerLinks()) {
			for (OpenEsbBpelProcess depBpelProcess : partnerLink.getDependenciesProcesses()) {

				GraphNode existsNode = findDataInNodes(depBpelProcess);
				if (existsNode != null) {

					GraphConnection existsConnection = findConnection(source, existsNode);
					if (existsConnection != null) {
						existsConnection.setCurveDepth(30);
						GraphConnection connection = createConnection(source, existsNode, partnerLink, true);
						connection.setCurveDepth(10);
					} else {
						if (source.equals(existsNode)) {
							GraphConnection connection = createConnection(source, existsNode, partnerLink, true);
							connection.setCurveDepth(30);
						} else {
							createConnection(source, existsNode, partnerLink, true);
						}
					}

				} else {
					GraphNode destination = createNode(depBpelProcess.getName(), depBpelProcess.getImage(), depBpelProcess);
					createConnection(source, destination, partnerLink, true);
				}
			}
		}
	}

	/**
	 * find {@link GraphConnection} in list of {@link #graphConnections} by
	 * source and destination
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
		} else if (project.getType() == ProjectType.ORACLE_SERVICE_BUS_1OG) {
			OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
			applyDependencies(oraSB10gProject, existsSource);
		}
	}

	/**
	 * create dependency graph for {@link OraSB10gProject}
	 * 
	 * @param project
	 * @param existsSource
	 */
	private final void applyDependencies(OraSB10gProject project, GraphNode existsSource) {
		GraphNode source = existsSource == null ? createNode(project.getName(), project.getImage(), project) : existsSource;
		for (Service service : project.getServices()) {

			GraphNode destination = findDataInNodes(service);
			if (destination == null && !(service instanceof UnknownFile)) {
				destination = createNode(service.getName(), service.getImage(), service);
				createConnection(source, destination, null, false);
			}

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
		GraphNode source = existsSource == null ? createNode(bpelProject.getName(), bpelProject.getImage(), bpelProject) : existsSource;
		for (OpenEsbBpelProcess openEsbBpelProcess : processes) {
			GraphNode destination = createNode(openEsbBpelProcess.getName(), openEsbBpelProcess.getImage(), openEsbBpelProcess);
			createConnection(source, destination, openEsbBpelProcess, false);
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
	 * @param existsSource
	 */
	private final void applyDependencies(BpelProject bpelProject, List<PartnerLinkBinding> partnerLinkBindings, GraphNode existsSource) {
		int curveDepth = 30;
		GraphNode source = existsSource == null ? createNode(bpelProject.getName(), bpelProject.getImage(), bpelProject) : existsSource;
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			// BPEL dependency
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				BpelProject project = partnerLinkBinding.getDependencyBpelProject();
				// self dependency
				if (bpelProject.equals(project)) {
					GraphConnection connection = createConnection(source, source, partnerLinkBinding, true);
					connection.setCurveDepth(curveDepth);
					curveDepth++;
				} else {
					if (existsSource != null) {
						// create connection to exists object in graph
						GraphNode existsDestination = findDataInNodes(project);
						createConnection(source, existsDestination, partnerLinkBinding, true);
					} else {
						// new object in graph
						GraphNode destination = createNode(project.getName(), project.getImage(), project);
						createConnection(source, destination, partnerLinkBinding, true);
					}
				}
			}
			// ESB project dependency
			else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				EsbProject esbProject = partnerLinkBinding.getDependencyEsbProject();

				if (existsSource != null) {
					// create connection to exists object in graph
					GraphNode existsDestination = findDataInNodes(esbProject);
					createConnection(source, existsDestination, partnerLinkBinding, true);
				} else {
					// new object in graph
					GraphNode destination = createNode(esbProject.getName(), esbProject.getImage(), esbProject);
					createConnection(source, destination, partnerLinkBinding, true);
				}
			}
			// Unknown project dependency
			else if (partnerLinkBinding.getUnknownProject() != null) {
				UnknownProject project = partnerLinkBinding.getUnknownProject();

				if (existsSource != null) {
					// create connection to exists object in graph
					GraphNode destination = findDataInNodes(project);
					if (destination == null) {
						destination = createNode(project.getName(), project.getImage(), project);
						createConnection(source, destination, partnerLinkBinding, true);
					}

				} else {
					// new object in graph
					GraphNode destination = createNode(project.getName(), project.getImage(), project);
					createConnection(source, destination, partnerLinkBinding, true);
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
		if (data == null) {
			return null;
		}
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
		if (workspace.getType() == WorkspaceType.ORACLE_1OG) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;
			GraphNode source = existSource == null ? createNode(ora10gWorkspace.getName(), ora10gWorkspace.getImage(), ora10gWorkspace) : existSource;
			for (Project project : ora10gWorkspace.getProjects()) {
				GraphNode existsProjectGraphNode = findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = createNode(project.getName(), project.getImage(), project);
					createConnection(source, destination, project, false);
				}
			}

		} else if (workspace.getType() == WorkspaceType.OPEN_ESB) {
			OpenEsbWorkspace openEsbWorkspace = (OpenEsbWorkspace) workspace;
			GraphNode source = existSource == null ? createNode(openEsbWorkspace.getName(), openEsbWorkspace.getImage(), openEsbWorkspace) : existSource;
			for (Project project : openEsbWorkspace.getProjects()) {

				GraphNode existsProjectGraphNode = findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = createNode(project.getName(), project.getImage(), project);
					createConnection(source, destination, project, false);
				}
				// if (existsProjectGraphNode != null &&
				// existsConnection(source, existsProjectGraphNode)) {
				//
				// } else {
				// GraphNode destination = createNode(project.getName(),
				// project.getImage(), project);
				// createConnection(source, destination, project, false);
				// }
			}
		} else if (workspace.getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
			OraSB10gWorkspace oraSB10gWorkspace = (OraSB10gWorkspace) workspace;
			GraphNode source = existSource == null ? createNode(oraSB10gWorkspace.getName(), oraSB10gWorkspace.getImage(), oraSB10gWorkspace) : existSource;
			for (OraSB10gProject project : oraSB10gWorkspace.getProjects()) {
				GraphNode existsProjectGraphNode = findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = createNode(project.getName(), project.getImage(), project);
					createConnection(source, destination, project, false);
				}
			}
		}
	}

	// /**
	// * check,
	// *
	// * @param source
	// * @param existsProjectGraphNode
	// * @return
	// */
	// private final boolean existsConnection(GraphNode source, GraphNode
	// existsProjectGraphNode) {
	// for (GraphConnection connection : graphConnections) {
	// if (source.equals(connection.getSource()) &&
	// existsProjectGraphNode.equals(connection.getDestination())) {
	// return true;
	// }
	// }
	// return false;
	// }

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
		GraphNode multiWorkspaceNode = createNode(multiWorkspace.getName(), ImageFactory.MULTI_WORKSPACE, multiWorkspace);

		if (multiWorkspace.getType() == WorkspaceType.ORACLE_1OG) {
			Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
			for (Ora10gWorkspace workspace : ora10gMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), workspace.getImage(), workspace);
				createConnection(multiWorkspaceNode, workspaceNode, workspace, false);
			}
		} else if (multiWorkspace.getType() == WorkspaceType.OPEN_ESB) {
			OpenEsbMultiWorkspace esbMultiWorkspace = (OpenEsbMultiWorkspace) multiWorkspace;
			for (OpenEsbWorkspace workspace : esbMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), workspace.getImage(), workspace);
				createConnection(multiWorkspaceNode, workspaceNode, workspace, false);
			}
		} else if (multiWorkspace.getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
			OraSB10gMultiWorkspace oraSB10gMultiWorkspace = (OraSB10gMultiWorkspace) multiWorkspace;
			for (OraSB10gWorkspace workspace : oraSB10gMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = createNode(workspace.getName(), workspace.getImage(), workspace);
				createConnection(multiWorkspaceNode, workspaceNode, workspace, false);
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
	private final GraphConnection createConnection(GraphNode source, GraphNode destination, Object data, boolean withText) {
		GraphConnection connection = new GraphConnection(graphViewer.getGraphControl(), SWT.ARROW_DOWN, source, destination);
		connection.setData(data);
		if (data != null && withText) {
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

	public final Graph getGraph() {
		return graphViewer.getGraphControl();
	}

	/**
	 * if found that node in graph is this mutli-workspace then clear graph
	 * 
	 * @param multiWorkspace
	 */
	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		if (!graphNodes.isEmpty()) {
			for (GraphNode graphNode : graphNodes) {
				if (graphNode.getData() != null) {
					if (graphNode.getData() instanceof Project) {
						if (multiWorkspace.equals(((Project) graphNode.getData()).getWorkpsace().getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof BpelProcess) {
						if (multiWorkspace.equals(((BpelProcess) graphNode.getData()).getProject().getWorkpsace().getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof MultiWorkspace) {
						if (multiWorkspace.equals(graphNode.getData())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof Workspace) {
						if (multiWorkspace.equals(((Workspace) graphNode.getData()).getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof Service) {
						if (multiWorkspace.equals(((Service) graphNode.getData()).getProject().getWorkpsace().getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * if found that node in graph is this workspace then clear graph
	 * 
	 * @param workspace
	 */
	public final void removeWorkspace(Workspace workspace) {
		if (!graphNodes.isEmpty()) {
			for (GraphNode graphNode : graphNodes) {
				if (graphNode.getData() != null) {
					if (graphNode.getData() instanceof Project) {
						MultiWorkspace multiWorkspace = ((Project) graphNode.getData()).getWorkpsace().getMultiWorkspace();
						if (multiWorkspace.containsWorkspace(workspace)) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof BpelProcess) {
						if (workspace.equals(((BpelProcess) graphNode.getData()).getProject().getWorkpsace().getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof MultiWorkspace) {
						if (workspace.equals(graphNode.getData())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof Workspace) {
						if (workspace.equals(((Workspace) graphNode.getData()).getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					} else if (graphNode.getData() instanceof Service) {
						if (workspace.equals(((Service) graphNode.getData()).getProject().getWorkpsace().getMultiWorkspace())) {
							clearContentFromGraph();
							break;
						}
					}
				}
			}
		}
	}
}
