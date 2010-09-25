package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.dependencies.BpelActivityDependency;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.dependnecies.OsbActivityDependency;
import com.tomecode.soa.ora.suite10g.project.BpelOperations;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.Operation;
import com.tomecode.soa.project.UnknownProject;

/**
 * Label provider for {@link ServiceOperationsDepNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
		} else if (element instanceof Service) {
			return ((Service) element).getImage();
		} else if (element instanceof OsbActivityDependency) {
			return ((OsbActivityDependency) element).getImage();
		} else if (element instanceof OpenEsbBpelProcess) {
			return ((OpenEsbBpelProcess) element).getImage();
		} else if (element instanceof BpelActivityDependency) {
			return ((BpelActivityDependency) element).getImage();
		} else if (element instanceof UnknownProject) {
			return ((UnknownProject) element).getImage();
		}
		return null;
	}

}
