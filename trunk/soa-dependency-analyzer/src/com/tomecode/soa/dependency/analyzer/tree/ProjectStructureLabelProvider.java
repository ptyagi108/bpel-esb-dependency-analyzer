package com.tomecode.soa.dependency.analyzer.tree;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Label provider for {@link ProjectStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProjectStructureLabelProvider extends LabelProvider {

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

	public Image getImage(Object element) {
		if (element instanceof File) {
			File file = (File) element;
			return file.isDirectory() ? ImageFactory.FOLDER : ImageFactory.FILE;
		}
		return null;
	}
}
