package com.tomecode.soa.dependency.analyzer.gui;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.tomecode.soa.dependency.analyzer.gui.actions.OpenWorkspaceAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.OpenNewVisualViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowBpelProcessStructureViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowProjectFilesViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowPropertiesViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowServiceBusStructureViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowServiceOperationDepViewAction;
import com.tomecode.soa.dependency.analyzer.gui.actions.view.ShowWorkspaceNavigatorAction;

/**
 * 
 * 
 * configures the action bars of the workbench window.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer
 * 
 */
public final class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private OpenNewVisualViewAction newVisualViewAction;
	private ShowWorkspaceNavigatorAction showWorkspaceNavigatorAction;
	private OpenWorkspaceAction openWorkspaceAction;
	private ShowPropertiesViewAction openPropertiesViewAction;
	private ShowProjectFilesViewAction showProjectFilesViewAction;
	private ShowBpelProcessStructureViewAction showBpelProcessStructureViewAction;
	private ShowServiceOperationDepViewAction showServiceOperationDepViewAction;
	private ShowServiceBusStructureViewAction serviceBusStructureViewAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		openWorkspaceAction = new OpenWorkspaceAction(window);
		newVisualViewAction = new OpenNewVisualViewAction();
		showWorkspaceNavigatorAction = new ShowWorkspaceNavigatorAction();
		showProjectFilesViewAction = new ShowProjectFilesViewAction();
		openPropertiesViewAction = new ShowPropertiesViewAction();
		showBpelProcessStructureViewAction = new ShowBpelProcessStructureViewAction();
		showServiceOperationDepViewAction = new ShowServiceOperationDepViewAction();
		serviceBusStructureViewAction = new ShowServiceBusStructureViewAction();
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		fileMenu.add(openWorkspaceAction);
		menuBar.add(fileMenu);
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		MenuManager visualMenu = new MenuManager("&Visual", IWorkbenchActionConstants.M_EDIT);
		menuBar.add(visualMenu);

		MenuManager viewMenu = new MenuManager("&View", "View");
		menuBar.add(viewMenu);
		viewMenu.add(newVisualViewAction);
		viewMenu.add(new Separator());
		viewMenu.add(showWorkspaceNavigatorAction);
		viewMenu.add(openPropertiesViewAction);
		viewMenu.add(showProjectFilesViewAction);
		viewMenu.add(showBpelProcessStructureViewAction);
		viewMenu.add(showServiceOperationDepViewAction);
		viewMenu.add(serviceBusStructureViewAction);
	}
}
