package com.tomecode.soa.dependency.analyzer.view.graph;

import java.util.List;

import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.protocols.jms.JMSQueue;
import com.tomecode.soa.protocols.jms.JMSServer;

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

	private HighlightFactory() {

	}

	// TODO: highlight connections
	static final void highlightObjectDependencies(GraphNode lastSelectedNode, List<GraphNode> graphNodes, List<GraphConnection> graphConnections) {
		for (GraphConnection connection : graphConnections) {
			if (connection.getSource() != null && connection.getSource().equals(lastSelectedNode)) {
				connection.highlight();
				highlightJMS(connection.getSource(), graphNodes, graphConnections);
			} else if (connection.getDestination() != null && connection.getDestination().equals(lastSelectedNode)) {
				connection.highlight();
				highlightJMS(connection.getDestination(), graphNodes, graphConnections);
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
	static final void clearAllHighlight(GraphNode lastSelectedNode, List<GraphNode> graphNodes, List<GraphConnection> graphConnections) {
		for (GraphConnection graphConnection : graphConnections) {
			try {
				graphConnection.unhighlight();
			} catch (IndexOutOfBoundsException e) {
			}
		}

		for (GraphNode graphNode : graphNodes) {
			if (!graphNode.equals(lastSelectedNode)) {
				try {
					graphNode.unhighlight();
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
	}

	/**
	 * highlight for {@link JMSServer}, {@link JMSConnectionFactory},
	 * {@link JMSQueue}
	 * 
	 * @param source
	 * @param highlight
	 * @param graphNodes
	 */
	private static final void highlightJMS(GraphNode sourceGraphNode, List<GraphNode> graphNodes, List<GraphConnection> graphConnections) {

		if (sourceGraphNode.getData() instanceof Service) {
			Service service = (Service) sourceGraphNode.getData();
			if (service.getEndpointConfig() != null && service.getEndpointConfig().getProtocol() == ProviderProtocol.JMS) {

				EndpointJms endpointJms = (EndpointJms) service.getEndpointConfig();
				// request JMS
				highlightJMSResources(endpointJms.getJmsServers(), graphNodes, graphConnections);

				// response JMS
				ProviderSpecificJms providerSpecificJms = endpointJms.getProviderSpecificJms();
				if (providerSpecificJms != null) {
					highlightJMSResources(providerSpecificJms.getJmsServers(), graphNodes, graphConnections);
				}
			}
		}
	}

	/**
	 * 
	 * highlight {@link JMSServer}, {@link JMSConnectionFactory} and
	 * {@link JMSQueue}
	 * 
	 * @param jmsServers
	 * @param graphNodes
	 * @param graphConnections
	 */
	private static final void highlightJMSResources(List<JMSServer> jmsServers, List<GraphNode> graphNodes, List<GraphConnection> graphConnections) {
		for (JMSServer jmsServer : jmsServers) {
			GraphNode jmsNode = findTextDataInNodes(jmsServer.toString(), JMSServer.class, graphNodes);
			if (jmsNode != null) {
				jmsNode.highlight();
				// connections
				for (JMSConnectionFactory connectionFactory : jmsServer.getConnectionFactories()) {
					GraphNode cfNode = findTextDataInNodes(connectionFactory.toString(), JMSConnectionFactory.class, graphNodes);
					if (cfNode != null) {
						cfNode.highlight();
						highlightConnections(jmsNode, cfNode, graphConnections);
					}
					// queues
					for (JMSQueue jmsQueue : connectionFactory.getJmsQueues()) {
						GraphNode queueNode = findTextDataInNodes(jmsQueue.toString(), JMSQueue.class, graphNodes);
						if (queueNode != null) {
							queueNode.highlight();
							highlightConnections(cfNode, queueNode, graphConnections);
						}
					}
				}
			}
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
	private static final void highlightConnections(GraphNode source, GraphNode target, List<GraphConnection> graphConnections) {
		for (GraphConnection connection : graphConnections) {
			if (source.equals(connection.getSource()) && target.equals(connection.getDestination())) {
				connection.highlight();
			} else if (source.equals(connection.getDestination()) && target.equals(connection.getSource())) {
				connection.highlight();
			}
		}
	}

	/**
	 * find {@link GraphNode} in graph by text
	 * 
	 * @param data
	 * @param class1
	 * @return
	 */
	static final GraphNode findTextDataInNodes(String data, Class<?> class1, List<GraphNode> graphNodes) {
		if (data == null) {
			return null;
		}
		for (GraphNode graphNode : graphNodes) {
			if (graphNode.getData() != null) {
				if (graphNode.getData().getClass().equals(class1) && graphNode.getText().equals(data)) {
					return graphNode;
				}
			}
		}
		return null;
	}
}
