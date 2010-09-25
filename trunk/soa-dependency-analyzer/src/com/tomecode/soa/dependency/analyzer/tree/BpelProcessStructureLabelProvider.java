package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProcessStrukture;
import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * Label provider for {@link BpelProcessStructureNavigator}
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class BpelProcessStructureLabelProvider extends LabelProvider {

	public final Image getImage(Object element) {
		if (element instanceof Ora10gBpelProcessStrukture) {
			return ImageFactory.ORACLE_10G_BPEL_PROCESS;
		} else if (element instanceof BpelProcess) {
			return ((BpelProcess) element).getImage();
		} else if (element instanceof BpelProject) {
			return ((BpelProject) element).getImage();
		} else if (element instanceof Activity) {
			Activity activity = ((Activity) element);
			if (activity.getActivtyType() != null) {
				return activity.getActivtyType().getImage();
			}
		}
		return null;
	}
}
