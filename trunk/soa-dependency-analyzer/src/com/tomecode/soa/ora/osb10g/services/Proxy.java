package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.project.Project;

/**
 * Proxy file
 * 
 * @author Tomas Frastia
 * 
 */
public final class Proxy implements Service {

	/**
	 * proxy service name
	 */
	private final String name;
	/**
	 * proxy service file
	 */
	private final File file;

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
	private ProxyStructure proxyStructure;

	/**
	 * parent project
	 */
	private Project project;

	/**
	 * endpoint config
	 */
	private EndpointConfig endpointConfig;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            proxy service file
	 */
	public Proxy(File file) {
		this.file = file;
		int index = file.getName().indexOf(".proxy");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			name = file.getName();
		}
	}

	public final File getFile() {
		return file;
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

	public final ProxyStructure getProxyStructure() {
		return proxyStructure;
	}

	public final void setProxyStructure(ProxyStructure proxyStructure) {
		this.proxyStructure = proxyStructure;
	}

	@Override
	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	public final Image getImage() {
		return ImageFactory.OSB_10G_SERVICE;
	}

	@Override
	public final Project getProject() {
		return project;
	}

	@Override
	public final void setProject(Project project) {
		this.project = project;
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
