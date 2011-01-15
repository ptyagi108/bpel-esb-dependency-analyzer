package com.tomecode.soa.dependency.analyzer.view.graph;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFile;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
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
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 * 
 */
final class VisualGraphObjectFactory {

	/**
	 * last selected node in graph
	 */
	private GraphNode lastSelectedNode;
	/**
	 * contains list of painted {@link GraphNode}
	 */
	private final List<GraphNode> graphNodes;
	/**
	 * contains list of painted {@link GraphConnection}
	 */
	private final List<GraphConnection> graphConnections;

	private final Graph graph;

	/**
	 * Constructor
	 * 
	 * @param graph
	 */
	VisualGraphObjectFactory(Graph graph) {
		this.graphNodes = new ArrayList<GraphNode>();
		this.graphConnections = new ArrayList<GraphConnection>();
		this.graph = graph;
	}

	/**
	 * * create connection between source and target {@link GraphNode}
	 * 
	 * config: SWT.ARROW_DOWN - outbound
	 * 
	 * @param source
	 * @param destination
	 * @param data
	 * @param toolTip
	 * @param withText
	 * @return
	 */
	final GraphConnection createConnectionOutbound(GraphNode source, GraphNode destination, Object data, IFigure toolTip, boolean withText) {
		GraphConnection connection = new GraphConnection(graph, SWT.ARROW_DOWN, source, destination);
		return createConnection(connection, data, toolTip, withText);
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param data
	 * @param toolTip
	 * @param withText
	 * @param isRequest
	 * @return
	 */
	final GraphConnection createConnectionRequest(GraphNode source, GraphNode destination, Object data, IFigure toolTip, boolean withText, boolean isRequest) {
		GraphConnection connection = null;
		if (isRequest) {
			connection = new GraphConnection(graph, SWT.ARROW_DOWN, destination, source);
		} else {
			connection = new GraphConnection(graph, SWT.ARROW_DOWN, source, destination);
		}
		return createConnection(connection, data, toolTip, withText);
	}

	/**
	 * create request and response connections
	 * 
	 * @param source
	 * @param destination
	 * @param data
	 * @param toolTip
	 * @param withText
	 * @param isRequest
	 * @return
	 */
	final GraphConnection createConnectionRequestResponse(GraphNode source, GraphNode destination, Object data, IFigure toolTip, boolean withText) {
		createConnection(new GraphConnection(graph, SWT.ARROW_DOWN, source, destination), data, toolTip, withText);
		createConnection(new GraphConnection(graph, SWT.ARROW_DOWN, destination, source), data, toolTip, withText);
		return null;
	}

	/**
	 * create connection without arrow
	 * 
	 * @param source
	 * @param destination
	 * @param data
	 * @param toolTip
	 * @param withText
	 * @return
	 */
	final GraphConnection createConnectionBasic(GraphNode source, GraphNode destination, Object data, IFigure toolTip, boolean withText) {
		GraphConnection connection = new GraphConnection(graph, SWT.NONE, source, destination);
		return createConnection(connection, data, toolTip, withText);
	}

	private final GraphConnection createConnection(GraphConnection connection, Object data, IFigure toolTip, boolean withText) {
		connection.setData(data);
		if (data != null && withText) {
			connection.setText(data.toString());
		}
		if (toolTip != null) {
			connection.setTooltip(toolTip);
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
	final GraphNode createNode(String name, Image image, Object data, IFigure toolTip) {
		GraphNode graphNode = new GraphNode(graph, SWT.NONE, name, image);
		graphNode.setData(data);
		if (toolTip != null) {
			graphNode.setTooltip(toolTip);
		}
		graphNodes.add(graphNode);
		return graphNode;
	}

	/**
	 * 'unhighlight' on the last selected node in graph
	 */
	final void clearLastSelectedNode() {
		if (lastSelectedNode != null) {
			try {
				lastSelectedNode.unhighlight();
			} catch (java.lang.IndexOutOfBoundsException e) {

			}
		}
		HighlightFactory.clearAllHighlight(lastSelectedNode, graphNodes, graphConnections);
		lastSelectedNode = null;
	}

	final void highlightSelectedNode(Object source) {
		for (GraphNode graphNode : graphNodes) {
			if (graphNode.getData().equals(source)) {
				lastSelectedNode = graphNode;
				lastSelectedNode.highlight();

			}
		}
	}

	/**
	 * delete selected node and all connections
	 * 
	 * @param selectedNode
	 */
	final void removeSelectedNode(Object selectedNode) {
		GraphNode graphNode = (GraphNode) selectedNode;

		List<GraphConnection> forRemove = new ArrayList<GraphConnection>();
		for (GraphConnection connection : graphConnections) {
			if (graphNode.equals(connection.getSource()) || graphNode.equals(connection.getDestination())) {
				forRemove.add(connection);
			}
		}

		for (GraphConnection remove : forRemove) {
			graphConnections.remove(remove);
			remove.dispose();
		}

		graphNodes.remove(graphNode);
		graphNode.dispose();
	}

	/**
	 * find connection between two {@link GraphNode}
	 * 
	 * @param source
	 * @param refPath
	 * @return
	 */
	final GraphConnection findUnknownConnection(GraphNode source, String refPath) {
		for (GraphConnection connection : graphConnections) {
			if (connection.getSource().getData().equals(source.getData())) {
				if (connection.getDestination().getData() != null && connection.getDestination().getData().toString().equals(refPath)) {
					return connection;
				}
			}
		}
		return null;
	}

	/**
	 * find {@link GraphConnection} in list of {@link #graphConnections} by
	 * source and destination
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	final GraphConnection findConnection(GraphNode source, GraphNode destination) {
		for (GraphConnection connection : graphConnections) {
			if (connection.getSource().equals(destination) && connection.getDestination().equals(source)) {
				return connection;
			}
		}
		return null;
	}

	final GraphConnection findConnection(GraphNode source, GraphNode destination, boolean isRequest) {
		if (isRequest) {
			return findConnection(source, destination);
		}

		return findConnection(destination, source);
	}

	/**
	 * find {@link GraphNode} in graph by data
	 * 
	 * @param data
	 * @return
	 */
	final GraphNode findDataInNodes(Object data) {
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
	 * remove all {@link GraphConnection} and {@link GraphNode} from
	 * {@link #graph}
	 */
	final void clearObjects() {
		for (GraphConnection connection : graphConnections) {
			if (!connection.isDisposed()) {
				connection.dispose();
			}
		}
		for (GraphNode node : graphNodes) {
			if (!node.isDisposed()) {
				node.dispose();
			}
		}

		graphConnections.clear();
		graphNodes.clear();
	}

	final List<GraphNode> getGraphNodes() {
		return graphNodes;
	}

	final GraphNode getLastSelectedNode() {
		return lastSelectedNode;
	}

	final List<GraphConnection> getGraphConnections() {
		return graphConnections;
	}

	/**
	 * create node for {@link HttpServer}
	 * 
	 * @param httpServer
	 * @return
	 */
	final GraphNode createNode(HttpServer httpServer) {
		return createNode(httpServer.getServer(), httpServer.getImage(false), httpServer, ToolTipFactory.createToolTip(httpServer));
	}

	/**
	 * create node for {@link JMSServer}
	 * 
	 * @param jmsServer
	 * @return
	 */
	final GraphNode createNode(JMSServer jmsServer) {
		return createNode(jmsServer.toString(), jmsServer.getImage(false), jmsServer, ToolTipFactory.createToolTip(jmsServer));
	}

	/**
	 * create node for {@link JMSConnectionFactory}
	 * 
	 * @param connectionFactory
	 * @return
	 */
	final GraphNode createNode(JMSConnectionFactory connectionFactory) {
		return createNode(connectionFactory.toString(), connectionFactory.getImage(false), connectionFactory, ToolTipFactory.createToolTip(connectionFactory));
	}

	/**
	 * create node for {@link EsbSvc}
	 * 
	 * @param sourceEsbSvc
	 * @return
	 */
	final GraphNode createNode(EsbSvc sourceEsbSvc) {
		return createNode(sourceEsbSvc.getName(), sourceEsbSvc.getImage(false), sourceEsbSvc, ToolTipFactory.createToolTip(sourceEsbSvc));
	}

	/**
	 * create node for {@link Ora10gEsbProject}
	 * 
	 * @param rootEsbProject
	 * @return
	 */
	final GraphNode createNode(Ora10gEsbProject rootEsbProject) {
		return createNode(rootEsbProject.getName(), rootEsbProject.getImage(false), rootEsbProject, ToolTipFactory.createToolTip(rootEsbProject));
	}

	/**
	 * create node for {@link JMSQueue}
	 * 
	 * @param queue
	 * @return
	 */
	final GraphNode createNode(JMSQueue queue) {
		return createNode(queue.toString(), queue.getImage(false), queue, ToolTipFactory.createToolTip(queue));
	}

	/**
	 * create node for {@link Service}
	 * 
	 * @param service
	 * @return
	 */
	final GraphNode createNode(Service service) {
		return createNode(service.getName(), service.getImage(), service, ToolTipFactory.createToolTip(service));
	}

	/**
	 * create node for {@link ServiceDependency}
	 * 
	 * @param serviceDependency
	 * @return
	 */
	final GraphNode createNode(ServiceDependency serviceDependency) {
		return createNode(serviceDependency.getRefPath(), ImageFactory.UNKNOWN, serviceDependency, null);
	}

	/**
	 * create node for {@link OpenEsbBpelProcess}
	 * 
	 * @param bpelProcess
	 * @return
	 */
	final GraphNode createNode(OpenEsbBpelProcess bpelProcess) {
		return createNode(bpelProcess.getName(), bpelProcess.getImage(false), bpelProcess, ToolTipFactory.createToolTip(bpelProcess));
	}

	/**
	 * create node for {@link Project}
	 * 
	 * @param project
	 * @return
	 */
	final GraphNode createNode(Project project) {
		return createNode(project.getName(), project.getImage(true), project, ToolTipFactory.createToolTip(project));
	}

	/**
	 * create node for {@link Ora10gWorkspace}
	 * 
	 * @param ora10gWorkspace
	 * @return
	 */
	final GraphNode createNode(Ora10gWorkspace ora10gWorkspace) {
		return createNode(ora10gWorkspace.getName(), ora10gWorkspace.getImage(false), ora10gWorkspace, ToolTipFactory.createToolTip(ora10gWorkspace));
	}

	/**
	 * create node for {@link OraSB10gWorkspace}
	 * 
	 * @param oraSB10gWorkspace
	 * @return
	 */
	final GraphNode createNode(OraSB10gWorkspace oraSB10gWorkspace) {
		return createNode(oraSB10gWorkspace.getName(), oraSB10gWorkspace.getImage(false), oraSB10gWorkspace, ToolTipFactory.createToolTip(oraSB10gWorkspace));
	}

	/**
	 * create node for {@link OpenEsbWorkspace}
	 * 
	 * @param openEsbWorkspace
	 * @return
	 */
	final GraphNode createNode(OpenEsbWorkspace openEsbWorkspace) {
		return createNode(openEsbWorkspace.getName(), openEsbWorkspace.getImage(false), openEsbWorkspace, ToolTipFactory.createToolTip(openEsbWorkspace));
	}

	/**
	 * create node for {@link MultiWorkspace}
	 * 
	 * @param multiWorkspace
	 * @return
	 */
	final GraphNode createNode(MultiWorkspace multiWorkspace) {
		return createNode(multiWorkspace.getName(), multiWorkspace.getImage(false), multiWorkspace, ToolTipFactory.createToolTip(multiWorkspace));
	}

	/**
	 * create node for {@link Workspace}
	 * 
	 * @param workspace
	 * @return
	 */
	final GraphNode createNode(Workspace workspace) {
		return createNode(workspace.getName(), workspace.getImage(false), workspace, ToolTipFactory.createToolTip(workspace));
	}

	/**
	 * create node for {@link HttpUrl}
	 * 
	 * @param httpUrl
	 * @return
	 */
	final GraphNode createNode(HttpUrl httpUrl) {
		return createNode(httpUrl.getUrl(), httpUrl.getImage(false), httpUrl, ToolTipFactory.createToolTip("HTTP Url", httpUrl.getUrl(), httpUrl.getImage(true)));
	}

	/**
	 * create node for {@link EndpointFile}
	 * 
	 * @param endpointFile
	 * @return
	 */
	final GraphNode createNode(EndpointFile endpointFile) {
		return createNode(endpointFile.getUris().get(0), endpointFile.getImage(false), endpointFile, ToolTipFactory.createToolTip(endpointFile));
	}

	/**
	 * create node for {@link FtpServer}
	 * 
	 * @param ftpServer
	 * @return
	 */
	final GraphNode createNode(FtpServer ftpServer) {
		return createNode(ftpServer.toString(), ftpServer.getImage(false), ftpServer, ToolTipFactory.createToolTip("FTP Server", ftpServer.getServer(), ftpServer.getPort(), ftpServer.getImage(true)));
	}

	/**
	 * create node for {@link SFtpServer}
	 * 
	 * @param ftpServer
	 * @return
	 */
	final GraphNode createNode(SFtpServer ftpServer) {
		return createNode(ftpServer.toString(), ftpServer.getImage(false), ftpServer, ToolTipFactory.createToolTip("SFTP Server", ftpServer.getServer(), ftpServer.getPort(), ftpServer.getImage(true)));
	}

	/**
	 * create node for {@link EjbProvider}
	 * 
	 * @param ejbProvider
	 * @return
	 */
	final GraphNode createNode(EjbProvider ejbProvider) {
		return createNode(ejbProvider.getName(), ejbProvider.getImage(false), ejbProvider, ToolTipFactory.createToolTip("EJB Provider", ejbProvider.getName(), ejbProvider.getImage(true)));
	}

	/**
	 * create node for {@link EjbHome}
	 * 
	 * @param ejbHome
	 * @return
	 */
	final GraphNode createNode(EjbHome ejbHome) {
		return createNode(ejbHome.getName(), ejbHome.getImage(false), ejbHome, ToolTipFactory.createToolTip("EJB Home", ejbHome.getName(), ejbHome.getImage(true)));
	}

	/**
	 * create node for {@link EjbObject}
	 * 
	 * @param ejbObject
	 * @return
	 */
	final GraphNode createNode(EjbObject ejbObject) {
		return createNode(ejbObject.getName(), ejbObject.getImage(false), ejbObject, ToolTipFactory.createToolTip("EJB Object", ejbObject.getName(), ejbObject.getImage(true)));
	}

	/**
	 * create node for {@link EjbMethod}
	 * 
	 * @param ejbMethod
	 * @return
	 */
	final GraphNode createNode(EjbMethod ejbMethod) {
		return createNode(ejbMethod.getName(), ejbMethod.getImage(false), ejbMethod, ToolTipFactory.createToolTip(ejbMethod));
	}

	final GraphNode createNode(String uris, EndpointConfig endpointConfig) {
		return createNode(uris, ImageFactory.UNKNOWN_SERVICE, endpointConfig, ToolTipFactory.createToolTip(endpointConfig));
	}
}
