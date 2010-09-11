package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.project.Project;
import com.tomecode.soa.util.FileRootNode;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Show all files and folders for selected project
 * 
 * @author Tomas Frastia
 * 
 */
public final class ProjectStructureNavigator extends ViewPart {

	public static final String ID = "view.projectStructureNavigator";
	/**
	 * Tree
	 */
	private TreeViewer tree;

	private ProjectStructureContentProvider contentProvider;

	private ProjectStructureLabelProvider labelProvider;

	private FileRootNode rootNode;

	public ProjectStructureNavigator() {
		super();
		rootNode = new FileRootNode();
		contentProvider = new ProjectStructureContentProvider();
		labelProvider = new ProjectStructureLabelProvider();
		setTitleToolTip("Project files");
	}

	@Override
	public final void createPartControl(Composite parent) {
		tree = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
	}

	@Override
	public final void setFocus() {

	}

	public final void showProjectFiles(Object firstElement) {
		if (firstElement instanceof Project) {
			Project project = (Project) firstElement;
			labelProvider.setRoot(project.getFile());
			rootNode.setProject(project);
			tree.setInput(rootNode);
			tree.expandAll();
		} else {
			clearTree();
		}
	}

	/**
	 * clear content of tree
	 */
	private final void clearTree() {
		rootNode.setProject(null);
		labelProvider.setRoot(null);
		tree.setInput(null);
	}

	/**
	 * 
	 * @param multiWorkspace
	 */
	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		MultiWorkspace multiWorkspaceInTree = findMultiWorkspaceForStructure();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			clearTree();
		}
	}

	private final MultiWorkspace findMultiWorkspaceForStructure() {
		if (rootNode.hasFiles()) {
			return rootNode.getProject().getWorkpsace().getMultiWorkspace();
		}
		return null;
	}

	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceForStructure();
		if (workspace.equals(workspaceInTree)) {
			clearTree();
		}
	}

	private final Workspace findWorkspaceForStructure() {
		if (rootNode.hasFiles()) {
			return rootNode.getProject().getWorkpsace();
		}
		return null;
	}
}
