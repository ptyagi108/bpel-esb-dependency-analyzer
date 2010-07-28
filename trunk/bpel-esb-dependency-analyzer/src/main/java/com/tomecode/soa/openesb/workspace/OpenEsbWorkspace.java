package com.tomecode.soa.openesb.workspace;

import java.io.File;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.workspace.Workspace;

/**
 * this object contains all projects(BPEL, ESB) in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbWorkspace extends BasicNode<OpenEsbBpelProject> implements Workspace {

	private static final long serialVersionUID = -1120117507374608673L;

	private String name;

	private File folder;

	public OpenEsbWorkspace(String name, File folder) {
		super();
		this.name = name;
		this.folder = folder;
	}

	@Override
	public final File getFolder() {
		return folder;
	}

	@Override
	public final String getName() {
		return name;
	}

//	@Override
//	public final List<Project> getProjects() {
//		return childs;
//	}

	@Override
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
}
