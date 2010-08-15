package com.tomecode.soa.dependency.analyzer.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.icons.ImageUtils;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.project.BpelProject;
import com.tomecode.soa.oracle10g.project.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.oracle10g.workspace.Ora10gWorkspace;
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

	// private GraphViewer graphViewer;
	private Graph graph;

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
		graph = new Graph(parent, SWT.NONE);

		// GraphViewer graphViewer = new GraphViewer(parent, SWT.NONE);
		// graph = graphViewer.getGraphControl();
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(1), true);
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

	/**
	 * create {@link GraphNode}
	 * 
	 * @param name
	 * @param image
	 * @param data
	 * @param backgroundColor
	 * @return
	 */
	private final GraphNode createNode(String name, Image image, Object data, Color backgroundColor) {
		GraphNode graphNode = createNode(name, image, data);
		graphNode.setBackgroundColor(backgroundColor);
		return graphNode;
	}
}
