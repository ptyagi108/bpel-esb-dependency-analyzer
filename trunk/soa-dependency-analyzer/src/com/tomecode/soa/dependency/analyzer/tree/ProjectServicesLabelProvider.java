package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.ora.osb10g.services.Service;

/**
 * 
 * Show all services in project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProjectServicesLabelProvider extends LabelProvider {

	public ProjectServicesLabelProvider() {
	}

	public final Image getImage(Object element) {
		if (element instanceof Service) {
			return ((Service) element).getImage();
		}
		return null;
	}
}
