package com.tomecode.soa.dependency.analyzer.tree;

import java.io.File;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Label provider for {@link ProjectFilesNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProjectFilesLabelProvider extends CellLabelProvider {

	private File root;

	public final void setRoot(File root) {
		this.root = root;
	}

	public final String getText(Object element) {
		if (root == null) {
			return null;
		}
		File file = (File) element;
		return file.getName();
	}

	public final Image getImage(Object element) {
		if (element instanceof File) {
			File file = (File) element;
			return file.isDirectory() ? ImageFactory.FOLDER : ImageFactory.FILE;
		}
		return null;
	}

	@Override
	public final void update(ViewerCell cell) {
		cell.setText(((File) cell.getElement()).getName());
		cell.setImage(getImage(cell.getElement()));
	}

	public final String getToolTipText(Object element) {
		if (element instanceof File) {
			File file = ((File) element);
			return file.getName() + " - " + file.getPath();
		}
		return element.toString();
	}
}
