package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.Operation;

/**
 * Label provider for {@link ServiceOperationsDepNavigator}
 * 
 * @author Tomas Frastia
 * 
 */
final class ServiceOperationsDepLabelProvider extends LabelProvider {

	public final Image getImage(Object element) {

		if (element instanceof BpelOperations) {
			return ImageFactory.ORACLE_10G_BPEL_PROCESS;
		} else if (element instanceof Operation) {
			Operation operation = (Operation) element;
			return operation.getActivtyType().getImage();
		} else if (element instanceof BpelProject) {
			return ((BpelProject) element).getImage();
		}
		return null;
	}

}
