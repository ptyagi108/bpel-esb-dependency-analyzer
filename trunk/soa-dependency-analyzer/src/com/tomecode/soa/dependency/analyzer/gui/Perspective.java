package com.tomecode.soa.dependency.analyzer.gui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.tomecode.soa.dependency.analyzer.tree.BpelProcessStructureNavigator;
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

		// Workspace navigator
		layout.addStandaloneView(WorkspacesNavigator.ID, true, IPageLayout.LEFT, 0.25f, editorArea);

		IFolderLayout folder = layout.createFolder("visualizers", IPageLayout.TOP, 0.75f, editorArea);
		folder.addPlaceholder(VisualGraphView.ID + ":*");
		folder.addView(VisualGraphView.ID);

		layout.addView(ServiceOperationsDepNavigator.ID, IPageLayout.LEFT | IPageLayout.BOTTOM, 0.50f, editorArea);
		// layout.addView(BpelProcessStructureNavigator.ID, IPageLayout.RIGHT |
		// IPageLayout.BOTTOM, 0.0f, editorArea);
		// layout.addView(ProjectPropertiesView.ID, IPageLayout.BOTTOM, 0f,
		// editorArea);

		IFolderLayout details = layout.createFolder("properties", IPageLayout.RIGHT | IPageLayout.BOTTOM, 0.50f, editorArea);
		details.addView(PropertiesView.ID);
		details.addView(BpelProcessStructureNavigator.ID);


	}
}
