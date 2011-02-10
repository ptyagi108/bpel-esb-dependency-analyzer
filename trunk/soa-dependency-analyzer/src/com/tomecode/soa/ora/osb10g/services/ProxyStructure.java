package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * flow structure of {@link Proxy}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProxyStructure extends OsbActivity {

	private final Proxy proxy;

	/**
	 * Constructor
	 * 
	 * @param proxy
	 */
	public ProxyStructure(Proxy proxy) {
		this.proxy = proxy;
		name = proxy.getName();
	}

	public final Proxy getProxy() {
		return proxy;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PROXY_SERVICE;
	}

}
