package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Business service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BusinessService extends Service {

	private EndpointConfig endpointConfig;

	public BusinessService(File file) {
		super(file, ServiceDependencyType.BUSINESS_SERVICE);
		String tmpName = file.getName().toLowerCase();
		int index = tmpName.indexOf(".biz");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			index = tmpName.indexOf(".businessservice");
			if (index != -1) {
				name = file.getName().substring(0, index);
			} else {
				name = file.getName();
			}
		}
		orginalName = name;
		name = name.replace("_", " ");
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BUSINESS_SERVICE;
	}

	/**
	 * @return the endpointConfig
	 */
	public final EndpointConfig getEndpointConfig() {
		return endpointConfig;
	}

	public final void setEndpointConfig(EndpointConfig endpointConfig) {
		this.endpointConfig = endpointConfig;
	}

	@Override
	public String getToolTip() {
		return null;
	}

}
