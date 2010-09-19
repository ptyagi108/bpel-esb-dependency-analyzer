package com.tomecode.soa.ora.osb10g.services;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * flow structure of {@link Proxy}
 * 
 * @author Tomas Frastia
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
