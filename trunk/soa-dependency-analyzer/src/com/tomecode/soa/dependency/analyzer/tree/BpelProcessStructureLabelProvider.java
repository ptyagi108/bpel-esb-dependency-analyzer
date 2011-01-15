package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProcessStrukture;
import com.tomecode.soa.services.BpelProcess;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Label provider for {@link BpelProcessStructureNavigator}
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
final class BpelProcessStructureLabelProvider extends CellLabelProvider {

	public final Image getImage(Object element) {

		if (element instanceof Ora10gBpelProcessStrukture) {
			return ImageFactory.ORACLE_10G_BPEL_PROCESS;
		} else if (element instanceof BpelProcess) {
			return ((BpelProcess) element).getImage(true);
		} else if (element instanceof ImageFace) {
			return ((ImageFace) element).getImage(true);
		}
		return null;
	}

	@Override
	public final void update(ViewerCell cell) {
		cell.setText(cell.getElement().toString());
		cell.setImage(getImage(cell.getElement()));
	}

	public final String getToolTipText(Object element) {
		if (element instanceof ImageFace) {
			return ((ImageFace) element).getToolTip();
		}
		return element.toString();
	}
}
