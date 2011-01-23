package com.tomecode.soa.dependency.analyzer.view.graph;

import java.util.List;

import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

import com.tomecode.soa.dependency.analyzer.view.graph.model.CollectionGraphNode;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSQueue;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * highlight in {@link VisualGraphView} - helper class
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
final class HighlightFactory {

	private VisualGraphObjectFactory objectFactory;

	/**
	 * Constructor
	 * 
	 * @param objectFactory
	 */
	HighlightFactory(VisualGraphObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	final void highlightWithDependencies(GraphNode selectedNode) {
		if (selectedNode instanceof CollectionGraphNode) {
			reverzHighlightDataInCollection((CollectionGraphNode) selectedNode);
		} else {
			highlightObjectDependencies(selectedNode);
		}
	}

	/**
	 * highlight data in {@link CollectionGraphNode}
	 * 
	 * @param selectedNode
	 */
	private final void reverzHighlightDataInCollection(CollectionGraphNode selectedNode) {
		selectedNode.highlight();
		highlightNodeMiddle(selectedNode);
	}

	private final void highlightNodeMiddle(CollectionGraphNode selectedNode) {
		for (Object data : selectedNode.getDatas()) {
			Node<?> node = (Node<?>) data;
			highlightParentNode(node, selectedNode);

			for (Object childNodeObj : node.getChilds()) {
				highlightNodeData(childNodeObj, selectedNode);
			}
		}
	}

	private final void highlightParentNode(Node<?> node, GraphNode currentNode) {
		CollectionGraphNode nodeInGraph = null;
		if (node.getParent() instanceof Node<?>) {
			nodeInGraph = objectFactory.findCollectionNodeTitleAndData(node.getParent());
			nodeInGraph.highlight();
			highlightConnections(nodeInGraph, currentNode);
			highlightParentNode((Node<?>) node.getParent(), nodeInGraph);
		} else if (node.getParent() instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Node<?>> nodes = (List<Node<?>>) node.getParent();
			for (Node<?> node2 : nodes) {
				nodeInGraph = objectFactory.findCollectionNodeTitleAndData(node2);
				if (nodeInGraph != null) {
					nodeInGraph.highlight();
					highlightConnections(nodeInGraph, currentNode);
					highlightParentNode(node2, nodeInGraph);
				}

			}
		} else {
			GraphNode nodeInGraph1 = objectFactory.findDataInNodes(node.getParent());
			if (nodeInGraph1 != null) {
				nodeInGraph1.highlight();
				highlightConnections(nodeInGraph1, currentNode);
			}
		}
	}

	/**
	 * 
	 * @param selectedNode
	 */
	private final void highlightObjectDependencies(GraphNode selectedNode) {
		for (GraphConnection connection : objectFactory.getGraphConnections()) {
			if (connection.getSource() != null && connection.getSource().equals(selectedNode)) {
				connection.highlight();
				highlightServiceTypes(connection.getSource());
			} else if (connection.getDestination() != null && connection.getDestination().equals(selectedNode)) {
				connection.highlight();
				highlightServiceTypes(connection.getDestination());
			}
		}
	}

	/**
	 * unhighlight on each nodes and connections
	 * 
	 * @param lastSelectedNode
	 * @param graphNodes
	 * @param graphConnections
	 */
	final void clearAllHighlight() {
		for (GraphConnection graphConnection : objectFactory.getGraphConnections()) {
			try {
				graphConnection.unhighlight();
			} catch (IndexOutOfBoundsException e) {
			}
		}

		for (GraphNode graphNode : objectFactory.getGraphNodes()) {
			if (!graphNode.equals(objectFactory.getLastSelectedNode())) {
				try {
					graphNode.unhighlight();
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		if (objectFactory.getLastSelectedNode() != null) {
			try {
				objectFactory.getLastSelectedNode().unhighlight();
			} catch (java.lang.IndexOutOfBoundsException e) {

			}
		}
		objectFactory.setLastSelectedNode(null);

	}

	/**
	 * highlight for {@link JMSServer}, {@link JMSConnectionFactory},
	 * {@link JMSQueue}
	 * 
	 * @param source
	 * @param highlight
	 * @param graphNodes
	 */
	private final void highlightServiceTypes(GraphNode selectedNode) {
		if (selectedNode.getData() instanceof Service) {
			Service service = (Service) selectedNode.getData();
			if (service.getEndpointConfig() != null) {
				EndpointConfig<?> endpointConfig = service.getEndpointConfig();
				if (endpointConfig.getProtocol() == ProviderProtocol.JMS) {
					// request JMS
					EndpointJms endpointJms = (EndpointJms) endpointConfig;
					// highlightJMSResources(endpointJms.getNodes());
					highlightNode(endpointJms.getNodes(), selectedNode);
					// response JMS
					ProviderSpecificJms providerSpecificJms = endpointJms.getProviderSpecificJms();
					if (providerSpecificJms != null) {
						highlightNode(providerSpecificJms.getJmsServers(), selectedNode);
					}
				} else if (endpointConfig.getProtocol() == ProviderProtocol.UNKNOWN) {
					highlightEndpointUnknown(endpointConfig);
				} else {
					highlightNode(endpointConfig.getNodes(), selectedNode);
				}

			}
		}
	}

	private final void highlightNode(List<?> nodes, GraphNode selectedNode) {
		for (Object nodeObj : nodes) {
			Node<?> node = (Node<?>) nodeObj;
			CollectionGraphNode nodeInGraph = objectFactory.findCollectionNodeTitleAndData(node.getObj());
			if (nodeInGraph != null) {
				nodeInGraph.highlight();
				highlightConnections(selectedNode, nodeInGraph);

				for (Object childNodeObj : node.getChilds()) {
					highlightNodeData(childNodeObj, nodeInGraph);
				}
			}
		}
	}

	private final void highlightNodeData(Object nodeObj, CollectionGraphNode parentNode) {
		if (nodeObj instanceof Node<?>) {
			Node<?> node = ((Node<?>) nodeObj);
			CollectionGraphNode nodeInGraph = objectFactory.findCollectionNodeTitleAndData(node.getObj());
			if (nodeInGraph != null) {
				nodeInGraph.highlight();
				highlightConnections(parentNode, nodeInGraph);
			}

			for (Object nodeChild : node.getChilds()) {
				highlightNodeData(nodeChild, nodeInGraph);
			}
		}
	}

	/**
	 * highlight for {@link EndpointConfig} = {@link ProviderProtocol#LOCAL}
	 * 
	 * @param endpointConfig
	 */
	private final void highlightEndpointUnknown(EndpointConfig<?> endpointConfig) {
		CollectionGraphNode localNode = objectFactory.findCollectionNodeTitleAndData(endpointConfig);
		if (localNode != null) {
			localNode.highlight();
		}
	}

	/**
	 * 'highlight' connections
	 * 
	 * 
	 * @param source
	 * @param target
	 * @param graphConnections
	 */
	private final void highlightConnections(GraphNode source, GraphNode target) {
		for (GraphConnection connection : objectFactory.getGraphConnections()) {
			if (source.equals(connection.getSource()) && target.equals(connection.getDestination())) {
				connection.highlight();
			} else if (source.equals(connection.getDestination()) && target.equals(connection.getSource())) {
				connection.highlight();
			}
		}
	}

	/**
	 * highlight selected node
	 * 
	 * @param source
	 */
	final GraphNode highlightSelectedNode(Object source, boolean setLastSelectedNode) {
		if (source != null) {
			for (GraphNode graphNode : objectFactory.getGraphNodes()) {
				if (source.equals(graphNode.getData())) {
					graphNode.highlight();
					if (setLastSelectedNode) {
						objectFactory.setLastSelectedNode(graphNode);
					}
					return graphNode;
				}
			}
		}
		return null;
	}
}
