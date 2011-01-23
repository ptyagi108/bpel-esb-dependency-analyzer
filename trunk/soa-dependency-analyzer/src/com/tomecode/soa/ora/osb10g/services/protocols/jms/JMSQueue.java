package com.tomecode.soa.ora.osb10g.services.protocols.jms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * JMS Queue
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */

public final class JMSQueue implements ImageFace, Node<JMSQueue> {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * parent {@link JMSConnectionFactory}
	 */
	private JMSConnectionFactory parentJmsConnectionFactory;

	/**
	 * queue name
	 */
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            JMS queue name
	 * @param jmsConnectionFactory
	 *            parent {@link JMSConnectionFactory}
	 */
	public JMSQueue(String name, JMSConnectionFactory jmsConnectionFactory) {
		this.name = name;
		this.parentJmsConnectionFactory = jmsConnectionFactory;
	}

	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JMS_QUEUE_SMALL;
		}
		return ImageFactory.JMS_QUEUE;
	}

	@Override
	public final String getToolTip() {
		return "Type: JMS Queue\nName: " + name;
	}

	@Override
	public final Object getParent() {
		return this.parentJmsConnectionFactory;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final JMSQueue getObj() {
		return this;
	}

}
