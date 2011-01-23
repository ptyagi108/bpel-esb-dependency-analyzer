package com.tomecode.soa.ora.osb10g.services.protocols.jms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * JMS Server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class JMSServer implements ImageFace, Node<JMSServer> {

	/**
	 * JMS server name (host)
	 */
	private String name;
	/**
	 * JMS server port
	 */
	private String port;

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * list of {@link JMSConnectionFactory}
	 */
	private final List<JMSConnectionFactory> connectionFactories;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JMS name
	 * @param port
	 *            JMS port
	 */
	public JMSServer(String name, String port) {
		this();
		this.name = name;
		this.port = port;
	}

	/**
	 * Constructor
	 * 
	 * @param server
	 */
	public JMSServer(String server) {
		this();
		int index = server.indexOf(":");
		if (index == -1) {
			name = server;
		} else {
			name = server.substring(0, index);
			port = server.substring(index + 1);
		}
	}

	private JMSServer() {
		this.connectionFactories = new ArrayList<JMSConnectionFactory>();
	}

	public final void addJMSConnectionFactory(JMSConnectionFactory connectionFactory) {
		if (!existJMSConnectionFactory(connectionFactory)) {
			connectionFactory.addJmsServer(this);
			connectionFactories.add(connectionFactory);
		}
	}

	private final boolean existJMSConnectionFactory(JMSConnectionFactory connectionFactory) {
		for (JMSConnectionFactory factory : connectionFactories) {
			if (factory.getName().equals(connectionFactory.getName())) {
				return true;
			}
		}
		return false;
	}

	public final String getName() {
		return name;
	}

	public final String getPort() {
		return port;
	}

	public final String toString() {
		if (port != null) {
			return name + ":" + port;
		}
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JMS_SERVER_SMALL;
		}
		return ImageFactory.JMS_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "Type: JMS Server" + "\nName: " + name + (port != null ? "\nPort: " + port : "");
	}

	public final void setParentService(Service parentService) {
		this.parentService = parentService;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<JMSConnectionFactory> getChilds() {
		return connectionFactories;
	}

	@Override
	public final JMSServer getObj() {
		return this;
	}

}
