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
 * DB protocol for {@link EsbSvc}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "Database Adapter")
public final class Database extends EsbAdapter implements ImageFace, Node<Database> {

	@PropertyViewData(title = "UI Connection Name:")
	private String uiConnectionName;

	@PropertyViewData(title = "Managed Connection Factory:")
	private String managedConnectionFactory;

	@PropertyViewData(title = "Driver Class Name:")
	private String driverClassName;

	@PropertyViewData(title = "Platform Class Name:")
	private String platformClassName;

	@PropertyViewData(title = "Connection String:")
	private String connectionString;

	@PropertyViewData(title = "Username:")
	private String userName;

	/**
	 * 
	 * @param locations
	 *            JNDI name
	 */
	public Database(String locations) {
		super(locations);
	}

	@Override
	public final Database getObj() {
		return this;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.DB_SERVER_SMALL;
		}
		return ImageFactory.DB_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "DB Server";
	}

	/**
	 * @return the uiConnectionName
	 */
	public final String getUiConnectionName() {
		return uiConnectionName;
	}

	/**
	 * @param uiConnectionName
	 *            the uiConnectionName to set
	 */
	public final void setUiConnectionName(String uiConnectionName) {
		this.uiConnectionName = uiConnectionName;
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
	 * @return the driverClassName
	 */
	public final String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName
	 *            the driverClassName to set
	 */
	public final void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the platformClassName
	 */
	public final String getPlatformClassName() {
		return platformClassName;
	}

	/**
	 * @param platformClassName
	 *            the platformClassName to set
	 */
	public final void setPlatformClassName(String platformClassName) {
		this.platformClassName = platformClassName;
	}

	/**
	 * @return the connectionString
	 */
	public final String getConnectionString() {
		return connectionString;
	}

	/**
	 * @param connectionString
	 *            the connectionString to set
	 */
	public final void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * @return the userName
	 */
	public final String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public final void setUserName(String userName) {
		this.userName = userName;
	}

}
