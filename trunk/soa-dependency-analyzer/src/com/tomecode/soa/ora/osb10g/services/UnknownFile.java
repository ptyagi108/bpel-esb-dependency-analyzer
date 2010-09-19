package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownFile extends Service {

	public UnknownFile(File file) {
		super(file, ServiceDependencyType.UNKNOWN);
		this.name = file.getName();
	}

	@Override
	public final Image getImage() {
		return ImageFactory.UNKNOWN;
	}

}
