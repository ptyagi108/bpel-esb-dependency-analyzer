package com.tomecode.soa.dependency.analyzer.view.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.tomecode.soa.dependency.analyzer.gui.actions.graph.DeleteSelectedNodeInGraph;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.ExportGraphToImageAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.GraphExpanderAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.GraphExpanderAction.ExpandChangeListener;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.GraphLayoutAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.GraphLayoutAction.LayoutActionType;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.OpenVisualGraphAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.graph.RefreshGraphLayout;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectFilesNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectServicesNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceBusStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.PropertiesViewOsbAdapter;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper;
import com.tomecode.soa.dependency.analyzer.view.visual.ZoomHelper.ZoomAction;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.activity.PartnerLink;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownFile;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointEJB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFTP;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFile;
import com.tomecode.soa.ora.osb10g.services.config.EndpointHttp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSFTP;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite10g.esb.EsbGrp;
import com.tomecode.soa.ora.suite10g.esb.EsbOperation;
import com.tomecode.soa.ora.suite10g.esb.EsbRoutingRule;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.EsbSys;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.protocols.ejb.EjbHome;
import com.tomecode.soa.protocols.ejb.EjbMethod;
import com.tomecode.soa.protocols.ejb.EjbObject;
import com.tomecode.soa.protocols.ejb.EjbProvider;
import com.tomecode.soa.protocols.ftp.FtpServer;
import com.tomecode.soa.protocols.ftp.SFtpServer;
import com.tomecode.soa.protocols.http.HttpServer;
import com.tomecode.soa.protocols.http.HttpUrl;
import com.tomecode.soa.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.protocols.jms.JMSQueue;
import com.tomecode.soa.protocols.jms.JMSServer;
import com.tomecode.soa.services.BpelProcess;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Visual graph for visualizing dependencies between services/processes
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 * 
 */
public final class VisualGraphView extends EditorPart implements IEditorInput {// ViewPart
																				// {

	public static final String ID = "view.visualgraph";

	private GraphViewer graphViewer;

	private VisualGraphObjectFactory objectFactory;

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

	// *******************************ACTIONS**************************
	/**
	 * open new {@link VisualGraphView} from selected node in graph
	 */
	private final OpenVisualGraphAction openVisualGraphAction;
	/**
	 * open new {@link FlowGraphView} from selected node in graph
	 */
	// private final OpenFlowGraphAction openFlowGraphAction;

	/**
	 * delete selected node in graph
	 */
	private final DeleteSelectedNodeInGraph deleteSelectedNodeInGraph;

	// *******************************ACTIONS**************************

	public VisualGraphView() {
		super();
		setTitleToolTip("Dependency Graph");
		setTitleImage(ImageFactory.VISUAL_GRAPH_VIEW);

		this.expandedInGraphObjects = new ArrayList<Object>();
		this.isExpandInGraph = true;
		this.openVisualGraphAction = new OpenVisualGraphAction();
		this.openVisualGraphAction.setEnabled(false);
		// this.openFlowGraphAction = new OpenFlowGraphAction();
		// this.openFlowGraphAction.setEnabled(false);
		this.deleteSelectedNodeInGraph = new DeleteSelectedNodeInGraph();
		this.deleteSelectedNodeInGraph.setEnabled(false);
	}

	@Override
	public final void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		graphViewer = new GraphViewer(parent, SWT.NONE);
		Graph graph = graphViewer.getGraphControl();
		objectFactory = new VisualGraphObjectFactory(graph);
		// browserManager = new BrowserManager(this);
		// graph = new Graph(parent, SWT.NONE);
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		graph.setRedraw(true);
		graph.addSelectionListener(new SelectionAdapter() {

			/**
			 * select in graph
			 */
			@Override
			public final void widgetSelected(SelectionEvent event) {
				objectFactory.clearLastSelectedNode();

				List<?> list = ((Graph) event.widget).getSelection();
				if (!list.isEmpty()) {
					showPropertiesAboutSelectedNode(list.get(0));
				} else {
					// no selection in graph
					ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
					if (serviceBusStructureNavigator != null) {
						GuiUtils.getServiceBusStructureNavigator().show(null);
					}
					setDataForGraphActions(null);
					HighlightFactory.clearAllHighlight(objectFactory.getLastSelectedNode(), objectFactory.getGraphNodes(), objectFactory.getGraphConnections());
				}
			}
		});

		graph.addMouseListener(new MouseAdapter() {
			/**
			 * double click in graph
			 */
			public final void mouseDoubleClick(MouseEvent e) {
				objectFactory.clearLastSelectedNode();

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
				applyDependenciesOSB10g(service, existsSource);
			} else if (selectedGraphNode.getData() instanceof EsbSvc) {
				EsbSvc esbSvc = (EsbSvc) selectedGraphNode.getData();
				clearGraph();
				createEsbSvcGraph(esbSvc, existsSource);
			}
			// else if (selectedGraphNode.getData() instanceof JMSServer) {
			// JMSServer jmsServer = (JMSServer) selectedGraphNode.getData();
			// clearGraph();
			// createJMSConnectionGraph(jmsServer, existsSource);
			// } else if (selectedGraphNode.getData() instanceof
			// JMSConnectionFactory) {
			// JMSConnectionFactory jmsConnectionFactory =
			// (JMSConnectionFactory) selectedGraphNode.getData();
			// clearGraph();
			// createJMSModuleGraph(jmsConnectionFactory, existsSource);
			// }

		}
	}

	/**
	 * delete selected node and all connections
	 * 
	 * @param selectedNode
	 */
	public final void deleteSelectedNode(Object selectedNode) {
		objectFactory.removeSelectedNode(selectedNode);
		setDataForGraphActions(null);
	}

	/**
	 * create menu for view and create context menu
	 */
	private final void initMenus(final Graph graph) {

		Separator separator = new Separator();
		MenuManager popupMenuManager = new MenuManager("#PopupMenu#VisualGraph");
		popupMenuManager.createContextMenu(graph);

		popupMenuManager.add(openVisualGraphAction);
		// popupMenuManager.add(openFlowGraphAction);
		popupMenuManager.add(separator);
		popupMenuManager.add(deleteSelectedNodeInGraph);
		popupMenuManager.add(separator);

		GraphLayoutAction defaultAction = new GraphLayoutAction(LayoutActionType.SPRING_LAYOUT);
		defaultAction.setChecked(true);
		GraphLayoutAction actionTreeLyout = new GraphLayoutAction(LayoutActionType.TREE_LAYOUT);
		GraphLayoutAction actionVerticalLayout = new GraphLayoutAction(LayoutActionType.VERTICAL_LAYOUT);
		GraphLayoutAction actionRadialLayout = new GraphLayoutAction(LayoutActionType.RADIAL_LAYOUT);
		GraphLayoutAction actionHorizontalTreeLayout = new GraphLayoutAction(LayoutActionType.HORIZONTAL_TREE_LAYOUT);
		GraphLayoutAction actionHorizontalLayout = new GraphLayoutAction(LayoutActionType.HORIZONTAL_LAYOUT);
		GraphLayoutAction actionGridLayout = new GraphLayoutAction(LayoutActionType.GRID_LAYOUT);
		GraphLayoutAction actionDirectedLayout = new GraphLayoutAction(LayoutActionType.DIRECTED_LAYOUT);

		MenuManager menuLayout = new MenuManager("Layout");
		menuLayout.add(separator);
		menuLayout.add(defaultAction);
		menuLayout.add(actionTreeLyout);
		menuLayout.add(actionVerticalLayout);
		menuLayout.add(actionRadialLayout);
		menuLayout.add(actionHorizontalTreeLayout);
		menuLayout.add(actionHorizontalLayout);
		menuLayout.add(actionGridLayout);
		menuLayout.add(actionDirectedLayout);
		menuLayout.add(separator);
		popupMenuManager.add(menuLayout);
		popupMenuManager.add(separator);

		RefreshGraphLayout refreshGraphLayout = new RefreshGraphLayout(graphViewer.getGraphControl());
		popupMenuManager.add(refreshGraphLayout);
		popupMenuManager.add(separator);
		ExportGraphToImageAction exportGraphToImageAction = new ExportGraphToImageAction(graph);
		popupMenuManager.add(exportGraphToImageAction);
		popupMenuManager.add(separator);

		MenuManager menuZoom = new MenuManager("Zoom");
		ZoomHelper zoomManager = new ZoomHelper(graph);
		Vector<Double> v = new Vector<Double>(zoomManager.getZoomActions().keySet());
		Collections.sort(v);

		for (Enumeration<Double> e = v.elements(); e.hasMoreElements();) {
			ZoomAction zoomAction = zoomManager.getZoomActions().get(e.nextElement());
			menuZoom.add(zoomAction);
		}
		popupMenuManager.add(menuZoom);
		// create popupmenu
		graph.setMenu(popupMenuManager.getMenu());

		// IToolBarManager toolBarManager =
		// getViewSite().getActionBars().getToolBarManager();
		IToolBarManager toolBarManager = getEditorSite().getActionBars().getToolBarManager();
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
	 * show properties in {@link PropertiesView} about select object in graph
	 * 
	 * @param object
	 */
	private final void showPropertiesAboutSelectedNode(Object object) {
		if (object instanceof GraphConnection) {
			GraphConnection connection = (GraphConnection) object;
			GuiUtils.getPropertiesView().show(connection.getData());
			if (connection.getData() != null) {
				ProjectFilesNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
				if (projectStructureNavigator != null) {
					projectStructureNavigator.showProjectFiles(connection.getData());
				}
			}
			ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceBusStructureNavigator.show(connection.getData());
			}

		} else if (object instanceof GraphNode) {
			GraphNode graphNode = (GraphNode) object;

			setDataForGraphActions(graphNode);

			Object selectedData = graphNode.getData();
			PropertiesView propertiesView = GuiUtils.getPropertiesView();
			if (propertiesView != null) {
				propertiesView.show(selectedData);
			}
			BpelProcessStructureNavigator bpelProcessStructureNavigator = GuiUtils.getBpelProcessStructureNavigator();
			if (bpelProcessStructureNavigator != null) {
				bpelProcessStructureNavigator.show(selectedData);
			}
			ServiceBusStructureNavigator serviceBusStructureNavigator = GuiUtils.getServiceBusStructureNavigator();
			if (serviceBusStructureNavigator != null) {
				serviceBusStructureNavigator.show(selectedData);
			}
			ServiceOperationsDepNavigator serviceOperationsDepNavigator = GuiUtils.getServiceOperationsDepNavigator();
			if (serviceOperationsDepNavigator != null) {
				serviceOperationsDepNavigator.show(selectedData);
				serviceBusStructureNavigator.showInTree(selectedData);
			}
			ProjectFilesNavigator projectStructureNavigator = GuiUtils.getProjectStructureNavigator();
			if (projectStructureNavigator != null) {
				projectStructureNavigator.showProjectFiles(selectedData);
			}
			PropertiesViewOsbAdapter propertiesViewOsbAdapter = GuiUtils.getPropertiesViewOsbAdapter();
			if (propertiesViewOsbAdapter != null) {
				propertiesViewOsbAdapter.show(selectedData);
			}
			ProjectServicesNavigator projectServicesNavigator = GuiUtils.getProjectServicesNavigator();
			if (projectServicesNavigator != null) {
				projectServicesNavigator.selectInTree(selectedData);
			}
			selectInGraph(graphNode);
			HighlightFactory.highlightObjectDependencies(graphNode, objectFactory.getGraphNodes(), objectFactory.getGraphConnections());
		}

	}

	/**
	 * set data (selected node) for graph actions
	 * 
	 * @param graphNode
	 */
	// TODO: disabled flow graph action
	private final void setDataForGraphActions(GraphNode graphNode) {
		if (graphNode == null) {
			openVisualGraphAction.setData(null);
			// openFlowGraphAction.setData(null);
		} else {
			openVisualGraphAction.setData(graphNode.getData());
			// openFlowGraphAction.setData(graphNode.getData());
		}

		deleteSelectedNodeInGraph.setData(graphNode);
	}

	@Override
	public final void setFocus() {
		// EditorSite site = (EditorSite) getSite();
		// if (site.getId() == null) {
		// GuiUtils.setActivateViewId(0);
		// } else {
		// int i = Integer.parseInt(site.getId());
		// GuiUtils.setActivateViewId(i);
		// }
	}

	public final void dispose() {
		// GuiUtils.dropActiveVisaulGraph();
		super.dispose();
	}

	/**
	 * show dependency graph
	 * 
	 * @param source
	 */

	public final void showGraph(Object source) {
		objectFactory.clearLastSelectedNode();
		if (!ignore(source)) {

			boolean backup = isExpandInGraph;
			isExpandInGraph = false;
			expandedInGraphObjects.clear();
			clearGraph();

			// if (addToBrowser) {
			// browserManager.add(source);
			// }

			if (source instanceof BpelOperations) {
				source = ((BpelOperations) source).getBpelProcess();
			}

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
			} else if (source instanceof EsbSvc) {
				EsbSvc esbSvc = (EsbSvc) source;
				// createProjectAndProjectGraph(esbSvc.getOwnerEsbProject(),
				// null);
				createEsbSvcGraph(esbSvc, null);
			} else if (source instanceof Service) {
				// applyDependencies((Service) source, null);
				applyDependenciesOSB10g((Service) source, null);
			}

			graphViewer.getGraphControl().applyLayout();
			isExpandInGraph = backup;

			selectInGraph(source);
		}
	}

	/**
	 * select node in graph
	 * 
	 * @param source
	 */
	public final void selectInGraph(Object source) {
		objectFactory.clearLastSelectedNode();
		objectFactory.highlightSelectedNode(source);
	}

	private final boolean ignore(Object source) {
		if (source instanceof EsbGrp) {
			return true;
		} else if (source instanceof EsbSys) {
			return true;
		}
		return false;
	}

	/**
	 * show ESB service and him parent project
	 * 
	 * @param sourceEsbSvc
	 * @param existsSource
	 */
	private final void createEsbSvcGraph(EsbSvc sourceEsbSvc, GraphNode existsSource) {
		GraphNode source = existsSource == null ? objectFactory.createNode(sourceEsbSvc) : existsSource;

		Ora10gEsbProject rootEsbProject = sourceEsbSvc.getProject();
		GraphNode esbProjectNode = objectFactory.findDataInNodes(rootEsbProject);
		if (esbProjectNode == null) {
			esbProjectNode = objectFactory.createNode(rootEsbProject);
			objectFactory.createConnectionOutbound(esbProjectNode, source, null, null, false);
		} else {

			GraphNode exitsSourceNode = objectFactory.findDataInNodes(sourceEsbSvc);
			if (exitsSourceNode != null) {
				if (objectFactory.findConnection(esbProjectNode, exitsSourceNode) == null) {
					objectFactory.createConnectionOutbound(esbProjectNode, source, null, null, false);
				}
			}
		}

		for (EsbOperation esbOperation : sourceEsbSvc.getEsbOperations()) {
			for (Object dep : esbOperation.getDependencies()) {
				GraphNode destination = objectFactory.findDataInNodes(dep);
				if (destination != null) {
					if (objectFactory.findConnection(source, destination) == null) {
						objectFactory.createConnectionOutbound(source, destination, esbOperation, null, false);
					}
					if (objectFactory.findConnection(esbProjectNode, destination) == null) {
						objectFactory.createConnectionOutbound(esbProjectNode, destination, null, null, false);
					}
				} else {
					if (dep instanceof Ora10gBpelProject) {
						GraphNode depDestination = objectFactory.findDataInNodes(dep);
						if (depDestination == null) {
							depDestination = objectFactory.createNode((Ora10gBpelProject) dep);
							objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
						} else {
							if (objectFactory.findConnection(source, depDestination) == null) {
								objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
							}
						}

					} else if (dep instanceof Ora10gEsbProject) {
						GraphNode depDestination = objectFactory.findDataInNodes(dep);
						if (depDestination == null) {
							depDestination = objectFactory.createNode((Ora10gEsbProject) dep);
							objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
						} else {
							objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
						}

						if (objectFactory.findConnection(esbProjectNode, depDestination) == null) {
							objectFactory.createConnectionOutbound(esbProjectNode, depDestination, null, null, false);
						}

					} else if (dep instanceof EsbRoutingRule) {
						EsbRoutingRule esbRoutingRule = (EsbRoutingRule) dep;
						for (EsbSvc rsEsbSvc : esbRoutingRule.getEsbSvcs()) {
							GraphNode depDestination = objectFactory.findDataInNodes(rsEsbSvc);
							if (depDestination == null) {
								depDestination = objectFactory.createNode(rsEsbSvc);
								objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
							} else {
								objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
							}

						}

					} else if (dep instanceof EsbSvc) {
						GraphNode depDestination = objectFactory.findDataInNodes(dep);
						if (depDestination == null) {
							depDestination = objectFactory.createNode((EsbSvc) dep);
							objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
						} else {
							objectFactory.createConnectionOutbound(source, depDestination, esbOperation, null, false);
						}

						if (objectFactory.findConnection(esbProjectNode, depDestination) == null) {
							objectFactory.createConnectionOutbound(esbProjectNode, depDestination, null, null, false);
						}
					}

				}
			}
		}
	}

	/**
	 * show dependency between source node and {@link EndpointConfig}
	 * 
	 * @param endpointConfig
	 * @param nodeSourceService
	 */
	private final void createOsbEndpoints(Service service, GraphNode nodeSourceService) {
		EndpointConfig endpointConfig = service.getEndpointConfig();
		if (endpointConfig != null) {
			if (endpointConfig.getProtocol() == ProviderProtocol.JMS) {
				boolean isRequest = false, isResponse = false;
				if (service.getBinding() != null) {
					isRequest = service.getBinding().getRequest() != null;
					isResponse = service.getBinding().getResponse() != null;
				}
				EndpointJms endpointJms = (EndpointJms) endpointConfig;
				// request JMS
				createEndpointJMSServer(endpointJms.getJmsServers(), nodeSourceService, isRequest);
				// response JMS
				ProviderSpecificJms providerSpecificJms = endpointJms.getProviderSpecificJms();
				if (providerSpecificJms != null) {
					createEndpointJMSServer(providerSpecificJms.getJmsServers(), nodeSourceService, !isResponse);
				}
			} else if (endpointConfig.getProtocol() == ProviderProtocol.HTTP) {
				createEndpointHttpServer((EndpointHttp) endpointConfig, nodeSourceService);
			} else if (endpointConfig.getProtocol() == ProviderProtocol.FILE) {
				createEndpointFileServer((EndpointFile) endpointConfig, nodeSourceService);
			} else if (endpointConfig.getProtocol() == ProviderProtocol.FTP) {
				createEndpointFtpServer((EndpointFTP) endpointConfig, nodeSourceService);
			} else if (endpointConfig.getProtocol() == ProviderProtocol.SFTP) {
				createEndpointSFtpServer((EndpointSFTP) endpointConfig, nodeSourceService);
			} else if (endpointConfig.getProtocol() == ProviderProtocol.EJB) {
				createEndpointEJBServer((EndpointEJB) endpointConfig, nodeSourceService);
			} else {
				createEndpointUnknown(endpointConfig, nodeSourceService);
			}
		}
	}

	/**
	 * Create dependency for {@link EndpointEJB}
	 * 
	 * @param endpointConfig
	 * @param nodeSourceService
	 */
	private final void createEndpointEJBServer(EndpointEJB endpointConfig, GraphNode nodeSourceService) {
		if (endpointConfig.getEjbProvider() != null) {
			// for (EjbProvider ejbProvider : endpointConfig.getEjbProvider()) {
			GraphNode nodeEjbProvider = HighlightFactory.findTextDataInNodes(endpointConfig.getEjbProvider().toString(), EjbProvider.class, objectFactory.getGraphNodes());
			if (nodeEjbProvider == null) {
				nodeEjbProvider = objectFactory.createNode(endpointConfig.getEjbProvider());
				objectFactory.createConnectionRequestResponse(nodeSourceService, nodeEjbProvider, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeEjbProvider, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceService, nodeEjbProvider, null, null, false);
				}
			}
			createEndpointEjbHome(endpointConfig.getEjbProvider(), nodeEjbProvider);
		}
	}

	/**
	 * create dependency for {@link EjbHome}
	 * 
	 * @param ejbProvider
	 */
	private final void createEndpointEjbHome(EjbProvider ejbProvider, GraphNode nodeSourceEjbProvider) {
		for (EjbHome ejbHome : ejbProvider.getEjbHomes()) {

			GraphNode nodeEjbHome = HighlightFactory.findTextDataInNodes(ejbHome.toString(), EjbHome.class, objectFactory.getGraphNodes());
			if (nodeEjbHome == null) {
				nodeEjbHome = objectFactory.createNode(ejbHome);
				objectFactory.createConnectionRequestResponse(nodeSourceEjbProvider, nodeEjbHome, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceEjbProvider, nodeEjbHome, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceEjbProvider, nodeEjbHome, null, null, false);
				}
			}

			createEndpointEjbObject(ejbHome, nodeEjbHome);
		}
	}

	/**
	 * create dependency for {@link EjbObject}
	 * 
	 * @param ejbHome
	 * @param nodeSourceEjbHome
	 */
	private final void createEndpointEjbObject(EjbHome ejbHome, GraphNode nodeSourceEjbHome) {
		for (EjbObject ejbObject : ejbHome.getEjbObjects()) {

			GraphNode nodeEjbObject = HighlightFactory.findTextDataInNodes(ejbObject.toString(), EjbObject.class, objectFactory.getGraphNodes());
			if (nodeEjbObject == null) {
				nodeEjbObject = objectFactory.createNode(ejbObject);
				objectFactory.createConnectionRequestResponse(nodeSourceEjbHome, nodeEjbObject, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceEjbHome, nodeEjbObject, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceEjbHome, nodeEjbObject, null, null, false);
				}
			}
			createEndpointEjbMethod(ejbObject, nodeEjbObject);
		}
	}

	/**
	 * create dependency for {@link EjbMethod}
	 * 
	 * @param ejbObject
	 * @param nodeSourceEjbObject
	 */
	private final void createEndpointEjbMethod(EjbObject ejbObject, GraphNode nodeSourceEjbObject) {
		for (EjbMethod ejbMethod : ejbObject.getEjbMethods()) {

			GraphNode nodeEjbMethod = HighlightFactory.findTextDataInNodes(ejbMethod.toString(), EjbMethod.class, objectFactory.getGraphNodes());
			if (nodeEjbMethod == null) {
				nodeEjbMethod = objectFactory.createNode(ejbMethod);
				objectFactory.createConnectionRequestResponse(nodeSourceEjbObject, nodeEjbMethod, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceEjbObject, nodeEjbMethod, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceEjbObject, nodeEjbMethod, null, null, false);
				}
			}
		}
	}

	/**
	 * create dependency for {@link EndpointFTP}
	 * 
	 * @param endpointFtp
	 * @param nodeSourceService
	 */
	private final void createEndpointFtpServer(EndpointFTP endpointFtp, GraphNode nodeSourceService) {
		for (FtpServer ftpServer : endpointFtp.getFtpServers()) {
			GraphNode nodeFtpServer = HighlightFactory.findTextDataInNodes(ftpServer.toString(), FtpServer.class, objectFactory.getGraphNodes());
			if (nodeFtpServer == null) {
				nodeFtpServer = objectFactory.createNode(ftpServer);
				objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFtpServer, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeFtpServer, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFtpServer, null, null, false);
				}
			}
		}
	}

	/**
	 * create dependency for {@link EndpointSFTP}
	 * 
	 * @param endpointSFtp
	 * @param nodeSourceService
	 */
	private final void createEndpointSFtpServer(EndpointSFTP endpointSFtp, GraphNode nodeSourceService) {

		for (SFtpServer ftpServer : endpointSFtp.getSFtpServers()) {
			GraphNode nodeFtpServer = HighlightFactory.findTextDataInNodes(ftpServer.toString(), SFtpServer.class, objectFactory.getGraphNodes());
			if (nodeFtpServer == null) {
				nodeFtpServer = objectFactory.createNode(ftpServer);
				objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFtpServer, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeFtpServer, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFtpServer, null, null, false);
				}
			}
		}
	}

	/**
	 * create dependency for {@link EndpointFile}
	 * 
	 * @param endpointFile
	 * @param nodeSourceService
	 */
	private final void createEndpointFileServer(EndpointFile endpointFile, GraphNode nodeSourceService) {
		if (!endpointFile.getUris().isEmpty()) {
			String uri = endpointFile.getUris().get(0);
			GraphNode nodeFileServer = HighlightFactory.findTextDataInNodes(uri, EndpointFile.class, objectFactory.getGraphNodes());
			if (nodeFileServer == null) {
				nodeFileServer = objectFactory.createNode(endpointFile);
				objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFileServer, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeFileServer, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceService, nodeFileServer, null, null, false);
				}
			}
		}
	}

	/**
	 * create dependency graph for {@link EndpointConfig}
	 * 
	 * @param endpointConfig
	 */
	private final void createEndpointUnknown(EndpointConfig endpointConfig, GraphNode nodeSourceService) {
		String uris = "";
		Iterator<String> i = endpointConfig.getUris().iterator();
		while (i.hasNext()) {
			uris += i.next();
			if (i.hasNext()) {
				uris += "\n";
			}
		}

		GraphNode nodeFileServer = HighlightFactory.findTextDataInNodes(uris, EndpointConfig.class, objectFactory.getGraphNodes());
		if (nodeFileServer == null) {
			nodeFileServer = objectFactory.createNode(uris, endpointConfig);
			objectFactory.createConnectionOutbound(nodeSourceService, nodeFileServer, null, null, false);
		} else {
			if (objectFactory.findConnection(nodeSourceService, nodeFileServer, false) == null) {
				objectFactory.createConnectionOutbound(nodeSourceService, nodeFileServer, null, null, false);
			}
		}
	}

	/**
	 * create dependency graph for {@link EndpointHttp}
	 * 
	 * @param endpointConfig
	 * @param nodeSourceService
	 */
	private final void createEndpointHttpServer(EndpointHttp endpointConfig, GraphNode nodeSourceService) {
		for (HttpServer httpServer : endpointConfig.getHttpServers()) {
			GraphNode nodeHttpServer = HighlightFactory.findTextDataInNodes(httpServer.toString(), JMSServer.class, objectFactory.getGraphNodes());
			if (nodeHttpServer == null) {
				nodeHttpServer = objectFactory.createNode(httpServer);
				objectFactory.createConnectionRequestResponse(nodeSourceService, nodeHttpServer, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeHttpServer, false) == null) {
					objectFactory.createConnectionRequestResponse(nodeSourceService, nodeHttpServer, null, null, false);
				}
			}
			createEndpointHttpUrl(httpServer, nodeHttpServer);
		}
	}

	/**
	 * create dependency graph for {@link EndpointHttp}
	 * 
	 * @param httpServer
	 * @param nodeSourceHttpServer
	 */
	private final void createEndpointHttpUrl(HttpServer httpServer, GraphNode nodeSourceHttpServer) {
		for (HttpUrl httpUrl : httpServer.getHttpUrls()) {

			GraphNode nodeHttpUrl = HighlightFactory.findTextDataInNodes(httpUrl.toString(), HttpUrl.class, objectFactory.getGraphNodes());
			if (nodeHttpUrl == null) {
				nodeHttpUrl = objectFactory.createNode(httpUrl);
				objectFactory.createConnectionBasic(nodeSourceHttpServer, nodeHttpUrl, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceHttpServer, nodeHttpUrl) == null) {
					objectFactory.createConnectionBasic(nodeSourceHttpServer, nodeHttpUrl, null, null, false);
				}
			}
		}
	}

	/**
	 * show dependency between {@link Service} and {@link JMSServer}
	 * 
	 * @param endpointJms
	 * @param nodeSourceService
	 */
	private final void createEndpointJMSServer(List<JMSServer> jmsServers, GraphNode nodeSourceService, boolean isRequest) {
		for (JMSServer server : jmsServers) {

			GraphNode nodeJmsServer = HighlightFactory.findTextDataInNodes(server.toString(), JMSServer.class, objectFactory.getGraphNodes());
			if (nodeJmsServer == null) {
				nodeJmsServer = objectFactory.createNode(server);
				objectFactory.createConnectionRequest(nodeSourceService, nodeJmsServer, null, null, false, isRequest);
			} else {
				if (objectFactory.findConnection(nodeSourceService, nodeJmsServer, isRequest) == null) {
					objectFactory.createConnectionRequest(nodeSourceService, nodeJmsServer, null, null, false, isRequest);
				}
			}
			createEndpointJMSConnFactory(server, nodeJmsServer, isRequest);
		}
	}

	/**
	 * show dependency between {@link JMSServer} and
	 * {@link JMSConnectionFactory}
	 * 
	 * @param jmsServer
	 * @param existNodeJmsServer
	 */
	private final void createEndpointJMSConnFactory(JMSServer jmsServer, GraphNode existNodeJmsServer, boolean isRequest) {
		GraphNode nodeSourceJmsServer = existNodeJmsServer == null ? objectFactory.createNode(jmsServer) : existNodeJmsServer;

		for (JMSConnectionFactory connectionFactory : jmsServer.getConnectionFactories()) {
			GraphNode nodeJmsConnFactory = HighlightFactory.findTextDataInNodes(connectionFactory.toString(), JMSConnectionFactory.class, objectFactory.getGraphNodes());

			if (nodeJmsConnFactory == null) {
				nodeJmsConnFactory = objectFactory.createNode(connectionFactory);
				objectFactory.createConnectionBasic(nodeSourceJmsServer, nodeJmsConnFactory, null, null, false);
			} else {
				if (objectFactory.findConnection(nodeSourceJmsServer, nodeJmsConnFactory) == null) {
					objectFactory.createConnectionBasic(nodeSourceJmsServer, nodeJmsConnFactory, null, null, false);
				}
			}

			createEndpointJMSQueue(connectionFactory, nodeJmsConnFactory, isRequest);
		}
	}

	/**
	 * show dependency between {@link JMSConnectionFactory} and
	 * {@link JMSServer}
	 * 
	 * @param connectionFactory
	 * @param existsNodeJmsConnFactory
	 */
	private final void createEndpointJMSQueue(JMSConnectionFactory connectionFactory, GraphNode existsNodeJmsConnFactory, boolean isRequest) {
		GraphNode sourceJmsConnFactory = existsNodeJmsConnFactory == null ? objectFactory.createNode(connectionFactory) : existsNodeJmsConnFactory;

		for (JMSQueue queue : connectionFactory.getJmsQueues()) {
			GraphNode nodeJmsQueue = HighlightFactory.findTextDataInNodes(queue.toString(), JMSQueue.class, objectFactory.getGraphNodes());

			if (nodeJmsQueue == null) {
				nodeJmsQueue = objectFactory.createNode(queue);
				objectFactory.createConnectionRequest(sourceJmsConnFactory, nodeJmsQueue, null, null, false, isRequest);
			} else {
				if (objectFactory.findConnection(sourceJmsConnFactory, nodeJmsQueue) == null) {
					objectFactory.createConnectionRequest(sourceJmsConnFactory, nodeJmsQueue, null, null, false, isRequest);
				}
			}
		}
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
	 * create dependencies between PROXY and target services dependencies, etc
	 * 
	 * @param service
	 * @param existsSource
	 */
	private final void applyDependenciesOSB10g(Service service, GraphNode existsSource) {

		GraphNode source = existsSource != null ? existsSource : objectFactory.createNode(service);
		for (ServiceDependency serviceDependency : service.getServiceDependencies().getDependnecies()) {

			if (serviceDependency.getServices().isEmpty()) {
				if (objectFactory.findUnknownConnection(source, serviceDependency.getRefPath()) == null) {
					// TODO: tool tip is null
					GraphNode unknowNode = objectFactory.createNode(serviceDependency);
					objectFactory.createConnectionOutbound(source, unknowNode, serviceDependency, null, false);
				}
			} else {
				GraphNode destination = objectFactory.findDataInNodes(serviceDependency.getServices().get(0));
				if (destination == null) {
					Service target = serviceDependency.getServices().get(0);
					destination = objectFactory.createNode(target);
					objectFactory.createConnectionOutbound(source, destination, serviceDependency, null, false);
				} else {
					if (objectFactory.findConnection(source, destination) == null) {
						objectFactory.createConnectionOutbound(source, destination, serviceDependency, null, false);
					}
				}
			}
		}
		createOsbEndpoints(service, source);
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

		GraphNode source = existsSource == null ? objectFactory.createNode(bpelProcess) : existsSource;
		for (PartnerLink partnerLink : bpelProcess.getPartnerLinks()) {
			for (OpenEsbBpelProcess depBpelProcess : partnerLink.getDependenciesProcesses()) {

				GraphNode existsNode = objectFactory.findDataInNodes(depBpelProcess);
				if (existsNode != null) {

					GraphConnection existsConnection = objectFactory.findConnection(source, existsNode);
					if (existsConnection != null) {
						existsConnection.setCurveDepth(30);
						GraphConnection connection = objectFactory.createConnectionOutbound(source, existsNode, partnerLink, null, true);
						connection.setCurveDepth(10);
					} else {
						if (source.equals(existsNode)) {
							GraphConnection connection = objectFactory.createConnectionOutbound(source, existsNode, partnerLink, null, true);
							connection.setCurveDepth(30);
						} else {
							objectFactory.createConnectionOutbound(source, existsNode, partnerLink, null, true);
						}
					}

				} else {
					GraphNode destination = objectFactory.createNode(depBpelProcess);
					objectFactory.createConnectionOutbound(source, destination, partnerLink, null, true);
				}
			}
		}
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
			Ora10gBpelProject bpelProject = (Ora10gBpelProject) project;
			applyDependencies(bpelProject, existsSource);
		} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
			Ora10gEsbProject esbProject = (Ora10gEsbProject) project;
			applyDependencies(esbProject, existsSource);
		} else if (project.getType() == ProjectType.OPEN_ESB_BPEL) {
			OpenEsbBpelProject bpelProject = (OpenEsbBpelProject) project;
			applyDependenciesOpenEsbBpel(bpelProject, bpelProject.getProcesses(), existsSource);
		} else if (project.getType() == ProjectType.ORACLE_SERVICE_BUS_1OG) {
			OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
			applyDependencies(oraSB10gProject, existsSource);
		} else if (project.getType() == ProjectType.UNKNOWN) {
			if (objectFactory.findDataInNodes(project) == null) {
				objectFactory.createNode(project);
			}
		}
	}

	/**
	 * 
	 * @param project
	 * @param existsSource
	 */
	private final void applyDependencies(Ora10gEsbProject project, GraphNode existsSource) {
		GraphNode source = existsSource == null ? objectFactory.createNode(project) : existsSource;

		for (Project depProject : project.getProjectDependecies()) {
			GraphNode destination = objectFactory.findDataInNodes(depProject);
			if (destination != null && !(depProject instanceof UnknownProject)) {
				destination = objectFactory.createNode(project);
				objectFactory.createConnectionOutbound(source, destination, null, null, false);
			}
		}

		for (EsbSvc esbSvc : project.getAllEsbSvc()) {
			GraphNode destination = objectFactory.findDataInNodes(esbSvc);
			if (destination == null) {
				destination = objectFactory.createNode(esbSvc);
				objectFactory.createConnectionOutbound(source, destination, esbSvc, null, false);
			} else {
				GraphConnection connection = objectFactory.findConnection(source, destination);
				if (connection == null) {
					objectFactory.createConnectionOutbound(source, destination, esbSvc, null, false);
				}
			}
		}

	}

	/**
	 * create dependency graph for {@link OraSB10gProject}
	 * 
	 * @param project
	 * @param existsSource
	 */
	private final void applyDependencies(OraSB10gProject project, GraphNode existsSource) {
		GraphNode source = existsSource == null ? objectFactory.createNode(project) : existsSource;
		for (Service service : project.getServices()) {

			GraphNode destination = objectFactory.findDataInNodes(service);
			if (destination == null && !(service instanceof UnknownFile)) {
				destination = objectFactory.createNode(service);
				objectFactory.createConnectionOutbound(source, destination, null, null, false);
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
		GraphNode source = existsSource == null ? objectFactory.createNode(bpelProject) : existsSource;
		for (OpenEsbBpelProcess openEsbBpelProcess : processes) {
			GraphNode destination = objectFactory.createNode(openEsbBpelProcess);
			objectFactory.createConnectionOutbound(source, destination, openEsbBpelProcess, null, false);
		}
	}

	/**
	 * create dependency graph where source object is {@link Ora10gBpelProject}
	 * and destination is list of dependencies project
	 * 
	 * @param bpelProject
	 * @param existsSource
	 */
	private final void applyDependencies(Ora10gBpelProject bpelProject, GraphNode existsSource) {
		int curveDepth = 30;
		GraphNode source = existsSource == null ? objectFactory.createNode(bpelProject) : existsSource;
		for (PartnerLinkBinding partnerLinkBinding : bpelProject.getPartnerLinkBindings()) {
			// BPEL dependency
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				Ora10gBpelProject project = partnerLinkBinding.getDependencyBpelProject();
				// self dependency
				if (bpelProject.equals(project)) {
					GraphConnection connection = objectFactory.createConnectionOutbound(source, source, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					connection.setCurveDepth(curveDepth);
					curveDepth++;
				} else {
					if (existsSource != null) {
						// create connection to exists object in graph
						GraphNode existsDestination = objectFactory.findDataInNodes(project);
						if (existsDestination == null) {
							GraphNode graphNode = objectFactory.createNode(project);
							objectFactory.createConnectionOutbound(source, graphNode, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
						} else {
							objectFactory.createConnectionOutbound(source, existsDestination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
						}
					} else {
						// new object in graph
						GraphNode destination = objectFactory.createNode(project);
						objectFactory.createConnectionOutbound(source, destination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					}
				}
			}
			// ESB project dependency
			else if (partnerLinkBinding.getDependencyEsbProject() != null) {

				EsbSvc esbSvc = partnerLinkBinding.getDependencyEsbProject().getEsbSvc();
				if (esbSvc != null) {
					GraphNode existsDestination = objectFactory.findDataInNodes(esbSvc);
					if (existsDestination != null) {
						if (objectFactory.findConnection(source, existsDestination) == null) {
							objectFactory.createConnectionOutbound(source, existsDestination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
						}
					} else {
						GraphNode destination = objectFactory.createNode(esbSvc);
						objectFactory.createConnectionOutbound(source, destination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					}

				} else {
					Ora10gEsbProject esbProject = partnerLinkBinding.getDependencyEsbProject().getEsbProject();

					if (existsSource != null) {
						// create connection to exists object in graph
						GraphNode existsDestination = objectFactory.findDataInNodes(esbProject);
						objectFactory.createConnectionOutbound(source, existsDestination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					} else {
						// new object in graph
						GraphNode destination = objectFactory.createNode(esbProject);
						objectFactory.createConnectionOutbound(source, destination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					}
				}
			}
			// Unknown project dependency
			else if (partnerLinkBinding.getUnknownProject() != null) {
				UnknownProject project = partnerLinkBinding.getUnknownProject();

				if (existsSource != null) {
					// create connection to exists object in graph
					GraphNode destination = objectFactory.findDataInNodes(project);
					if (destination == null) {
						destination = objectFactory.createNode(project);
						objectFactory.createConnectionOutbound(source, destination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
					}

				} else {
					// new object in graph
					GraphNode destination = objectFactory.createNode(project);
					objectFactory.createConnectionOutbound(source, destination, partnerLinkBinding, ToolTipFactory.createToolTip(partnerLinkBinding), true);
				}
			}
		}
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
			GraphNode source = existSource == null ? objectFactory.createNode(ora10gWorkspace) : existSource;
			for (Project project : ora10gWorkspace.getProjects()) {
				GraphNode existsProjectGraphNode = objectFactory.findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = objectFactory.createNode(project);
					objectFactory.createConnectionOutbound(source, destination, project, null, false);
				}
			}

		} else if (workspace.getType() == WorkspaceType.OPEN_ESB) {
			OpenEsbWorkspace openEsbWorkspace = (OpenEsbWorkspace) workspace;
			GraphNode source = existSource == null ? objectFactory.createNode(openEsbWorkspace) : existSource;
			for (Project project : openEsbWorkspace.getProjects()) {

				GraphNode existsProjectGraphNode = objectFactory.findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = objectFactory.createNode(project);
					objectFactory.createConnectionOutbound(source, destination, project, null, false);
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
			GraphNode source = existSource == null ? objectFactory.createNode(oraSB10gWorkspace) : existSource;
			for (Project project : oraSB10gWorkspace.getProjects()) {
				GraphNode existsProjectGraphNode = objectFactory.findDataInNodes(project);
				if (existsProjectGraphNode == null) {
					GraphNode destination = objectFactory.createNode(project);
					objectFactory.createConnectionOutbound(source, destination, project, null, false);
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
			objectFactory.clearObjects();
			graphViewer.getGraphControl().applyLayout();
		}
	}

	/**
	 * show dependency graph for {@link MultiWorkspace} and {@link Workspace}
	 * 
	 * @param multiWorkspace
	 */
	private final void createMultiWorkspaceAndWorkspaceGraph(MultiWorkspace multiWorkspace) {
		GraphNode multiWorkspaceNode = objectFactory.createNode(multiWorkspace);

		if (multiWorkspace.getType() == WorkspaceType.ORACLE_1OG) {
			Ora10gMultiWorkspace ora10gMultiWorkspace = (Ora10gMultiWorkspace) multiWorkspace;
			for (Workspace workspace : ora10gMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = objectFactory.createNode(workspace);
				objectFactory.createConnectionOutbound(multiWorkspaceNode, workspaceNode, workspace, null, false);
			}
		} else if (multiWorkspace.getType() == WorkspaceType.OPEN_ESB) {
			OpenEsbMultiWorkspace esbMultiWorkspace = (OpenEsbMultiWorkspace) multiWorkspace;
			for (Workspace workspace : esbMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = objectFactory.createNode(workspace);
				objectFactory.createConnectionOutbound(multiWorkspaceNode, workspaceNode, workspace, null, false);
			}
		} else if (multiWorkspace.getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
			OraSB10gMultiWorkspace oraSB10gMultiWorkspace = (OraSB10gMultiWorkspace) multiWorkspace;
			for (Workspace workspace : oraSB10gMultiWorkspace.getWorkspaces()) {
				GraphNode workspaceNode = objectFactory.createNode(workspace);
				objectFactory.createConnectionOutbound(multiWorkspaceNode, workspaceNode, workspace, null, false);
			}
		}

	}

	/**
	 * // * change layout algorithm {@link #graph}
	 */
	public final void changeLayout(AbstractLayoutAlgorithm layoutAlgorithm, LayoutActionType type) {
		Graph graph = graphViewer.getGraphControl();
		graph.setLayoutAlgorithm(layoutAlgorithm, true);

		MenuItem[] menuItems = graph.getMenu().getItems();
		for (MenuItem item : menuItems) {
			if ("Layout".equals(item.getText())) {
				MenuItem[] menuItemsLayouts = item.getMenu().getItems();
				for (MenuItem itemLayout : menuItemsLayouts) {
					ActionContributionItem actionContributionItem = (ActionContributionItem) itemLayout.getData();
					GraphLayoutAction action = (GraphLayoutAction) actionContributionItem.getAction();
					if (action.getType() != type) {
						action.setChecked(false);
					} else {
						action.setChecked(true);
					}
				}
				break;
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
		if (!objectFactory.getGraphNodes().isEmpty()) {
			for (GraphNode graphNode : objectFactory.getGraphNodes()) {
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
		if (!objectFactory.getGraphNodes().isEmpty()) {
			for (GraphNode graphNode : objectFactory.getGraphNodes()) {
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

	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public final void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		// getCommandStack().addCommandStackListener(this);
		// getSite().getWorkbenchWindow().getSelectionService()
		// .addSelectionListener(this);
		// initializeActionRegistry();
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "Dependency ";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final String getToolTipText() {
		return "Dependency view";
	}

}
