package com.tomecode.soa.protocols.jms;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * JMS Queue
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */

public final class JMSQueue implements ImageFace {

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

	public final JMSConnectionFactory getJmsConnectionFactory() {
		return parentJmsConnectionFactory;
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
	public String getToolTip() {
		return "JMS Queue";
	}

}
