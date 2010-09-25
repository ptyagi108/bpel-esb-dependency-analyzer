package com.tomecode.soa.dependency.analyzer.tree;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tomecode.soa.util.FileRootNode;

/**
 * Tree content provider for {@link ProjectStructureNavigator}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProjectStructureContentProvider implements ITreeContentProvider {

	@Override
	public final Object[] getChildren(Object parent) {
		if (parent instanceof FileRootNode) {
			return ((FileRootNode) parent).getFiles();
		} else if (parent instanceof File) {
			return ((File) parent).listFiles();
		}
		return null;
	}

	@Override
	public final Object getParent(Object paramObject) {
		return null;
	}

	@Override
	public final boolean hasChildren(Object element) {
		if (element instanceof FileRootNode) {
			return !((FileRootNode) element).hasFiles();
		} else if (element instanceof File) {
			File[] files = ((File) element).listFiles();
			return files != null && files.length != 0;
		}
		return false;
	}

	@Override
	public final Object[] getElements(Object element) {
		if (element instanceof FileRootNode) {
			return ((FileRootNode) element).getFiles();
		} else if (element instanceof File) {
			return ((File) element).listFiles();
		}
		return null;
	}

	@Override
	public final void dispose() {

	}

	@Override
	public final void inputChanged(Viewer paramViewer, Object paramObject1, Object paramObject2) {

	}

}
