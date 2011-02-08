package com.tomecode.soa.ora.suite10g.esb.protocols.db;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.protocols.EsbAdapter;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * JMS adapter from {@link EsbSvc}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "JMS Adapter")
public final class JMSAdapter extends EsbAdapter implements Node<JMSAdapter>, ImageFace {

	@PropertyViewData(title = "Managed Connection Factory:")
	private String managedConnectionFactory;

	@PropertyViewData(title = "Connection Factory Location:")
	private String connectionFactoryLocation;

	@PropertyViewData(title = "Factory Properties:")
	private String factoryProperties;

	@PropertyViewData(title = "Is Topic:")
	private String isTopic;

	@PropertyViewData(title = "Is Transacted:")
	private String isTransacted;

	@PropertyViewData(title = "Username:")
	private String Username;

	public JMSAdapter(String locations) {
		super(locations);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.JMS_SERVER_SMALL;
		}
		return ImageFactory.JMS_SERVER;
	}

	@Override
	public String getToolTip() {
		return "JMS Server";
	}

	@Override
	public final JMSAdapter getObj() {
		return this;
	}

	/**
	 * @return the managedConnectionFactory
	 */
	public final String getManagedConnectionFactory() {
		return managedConnectionFactory;
	}

	/**
	 * @param managedConnectionFactory
	 *            the managedConnectionFactory to set
	 */
	public final void setManagedConnectionFactory(String managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * @return the connectionFactoryLocation
	 */
	public final String getConnectionFactoryLocation() {
		return connectionFactoryLocation;
	}

	/**
	 * @param connectionFactoryLocation
	 *            the connectionFactoryLocation to set
	 */
	public final void setConnectionFactoryLocation(String connectionFactoryLocation) {
		this.connectionFactoryLocation = connectionFactoryLocation;
	}

	/**
	 * @return the factoryProperties
	 */
	public final String getFactoryProperties() {
		return factoryProperties;
	}

	/**
	 * @param factoryProperties
	 *            the factoryProperties to set
	 */
	public final void setFactoryProperties(String factoryProperties) {
		this.factoryProperties = factoryProperties;
	}

	/**
	 * @return the isTopic
	 */
	public final String getIsTopic() {
		return isTopic;
	}

	/**
	 * @param isTopic
	 *            the isTopic to set
	 */
	public final void setIsTopic(String isTopic) {
		this.isTopic = isTopic;
	}

	/**
	 * @return the isTransacted
	 */
	public final String getIsTransacted() {
		return isTransacted;
	}

	/**
	 * @param isTransacted
	 *            the isTransacted to set
	 */
	public final void setIsTransacted(String isTransacted) {
		this.isTransacted = isTransacted;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return Username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public final void setUsername(String username) {
		Username = username;
	}

}
