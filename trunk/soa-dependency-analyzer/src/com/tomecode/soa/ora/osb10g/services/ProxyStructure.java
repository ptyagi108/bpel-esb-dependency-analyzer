package com.tomecode.soa.ora.osb10g.services;

import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * flow structure of {@link Proxy}
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProxyStructure extends OsbActivity {
	private final Proxy proxy;

	public ProxyStructure(Proxy proxy) {
		this.proxy = proxy;
	}

	public final Proxy getProxy() {
		return proxy;
	}

}
