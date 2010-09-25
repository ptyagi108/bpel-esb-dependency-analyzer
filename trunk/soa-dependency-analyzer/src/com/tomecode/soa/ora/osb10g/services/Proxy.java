package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * PROXY service file
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Proxy extends Service {

	private boolean isEnabled;

	private boolean isAutoPublish;

	/**
	 * description
	 */
	private String description;

	private Binding binding;

	/**
	 * structure - contains all activities from this proxy service
	 */
	private ProxyStructure structure;

	/**
	 * endpoint config
	 */
	private EndpointConfig endpointConfig;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            PROXY service file
	 */
	public Proxy(File file) {
		super(file, ServiceDependencyType.PROXY_SERVICE);
		this.file = file;

		int index = file.getName().indexOf(".proxy");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			name = file.getName();
		}

		structure = new ProxyStructure(this);
	}

	public final boolean isEnabled() {
		return isEnabled;
	}

	public final void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public final boolean isAutoPublish() {
		return isAutoPublish;
	}

	public final void setIsAutoPublish(boolean isAutoPublish) {
		this.isAutoPublish = isAutoPublish;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final Binding getBinding() {
		return binding;
	}

	public final void setBinding(Binding binding) {
		this.binding = binding;
	}

	public final ProxyStructure getStructure() {
		return structure;
	}

	public final Image getImage() {
		return ImageFactory.OSB_10G_PROXY_SERVICE;
	}

	/**
	 * @return the endpointConfig
	 */
	public final EndpointConfig getEndpointConfig() {
		return endpointConfig;
	}

	/**
	 * @param endpointConfig
	 *            the endpointConfig to set
	 */
	public final void setEndpointConfig(EndpointConfig endpointConfig) {
		this.endpointConfig = endpointConfig;
	}

}
