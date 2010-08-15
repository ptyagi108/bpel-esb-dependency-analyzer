package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.oracle10g.project.Ora10gBpelProcessStrukture;

/**
 * 
 * Label provider for {@link BpelProcessStructureNavigator}
 * 
 * 
 * @author Tomas Frastia
 * 
 */
final class BpelProcessStructureLabelProvider extends LabelProvider {

	public final Image getImage(Object element) {
		if (element instanceof Ora10gBpelProcessStrukture) {
			return ImageFactory.ORACLE_10G_BPEL_PROCESS;
		} else if (element instanceof OpenEsbBpelProcess) {
			return ImageFactory.OPEN_ESB_BPEL_PROCESS;
		} else if (element instanceof Activity) {
			Activity activity = ((Activity) element);
			if (activity.getActivtyType() != null) {
				return activity.getActivtyType().getImageIcon();
			}
		}
		return null;
	}
}
