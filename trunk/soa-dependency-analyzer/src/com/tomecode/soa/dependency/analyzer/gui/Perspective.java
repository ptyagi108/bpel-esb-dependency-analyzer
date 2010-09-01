package com.tomecode.soa.dependency.analyzer.gui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ProjectStructureNavigator;
import com.tomecode.soa.dependency.analyzer.tree.ServiceOperationsDepNavigator;
import com.tomecode.soa.dependency.analyzer.tree.WorkspacesNavigator;
import com.tomecode.soa.dependency.analyzer.view.PropertiesView;
import com.tomecode.soa.dependency.analyzer.view.VisualGraphView;

/**
 * Perspective contains {@link WorkspacesNavigator} and {@link VisualGraphView}
 * and some trees :)
 * 
 * @author Tomas Frastia
 * 
 */
public final class Perspective implements IPerspectiveFactory {

	public final void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		IFolderLayout top = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
		top.addView(WorkspacesNavigator.ID);

		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.75f, "topLeft");
		bottomLeft.addView(ProjectStructureNavigator.ID);

		layout.addView(VisualGraphView.ID, IPageLayout.RIGHT | IPageLayout.TOP, 0.75f, editorArea);

		IFolderLayout operationsFolder = layout.createFolder("operations", IPageLayout.LEFT | IPageLayout.BOTTOM, 0.50f, editorArea);
		operationsFolder.addView(ServiceOperationsDepNavigator.ID);

		IFolderLayout secondFolder = layout.createFolder("properties", IPageLayout.RIGHT | IPageLayout.BOTTOM, 0.50f, editorArea);
		secondFolder.addView(PropertiesView.ID);
		secondFolder.addView(BpelProcessStructureNavigator.ID);
	}
}
