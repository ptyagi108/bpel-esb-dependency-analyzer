package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

/**
 * Proxy file
 * 
 * @author Tomas Frastia
 * 
 */
public final class Proxy implements Service {

	private final File file;

	private boolean isEnabled;

	private boolean isAutoPublish;

	private String description;

	private Binding binding;

	private ProxyStructure proxyStructure;

	public Proxy(File file) {
		this.file = file;
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

}
