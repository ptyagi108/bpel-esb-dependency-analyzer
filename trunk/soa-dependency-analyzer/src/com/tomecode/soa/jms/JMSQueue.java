package com.tomecode.soa.jms;

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

	private JMSConnectionFactory parentJmsConnectionFactory;

	private String name;

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
	public final Image getImage() {
		return ImageFactory.JMS_QUEUE;
	}

	@Override
	public String getToolTip() {
		return null;
	}

}
