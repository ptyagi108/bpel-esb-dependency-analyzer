package com.tomecode.soa.dependency.analyzer.gui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.tomecode.soa.dependency.analyzer.gui.actions.OpenWorkspaceAction;

/**
 * 
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	
	private OpenWorkspaceAction openWorkspaceAction;
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		openWorkspaceAction = new OpenWorkspaceAction(window);
		
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		fileMenu.add(openWorkspaceAction);
		
		
		menuBar.add(fileMenu);
	}

}
