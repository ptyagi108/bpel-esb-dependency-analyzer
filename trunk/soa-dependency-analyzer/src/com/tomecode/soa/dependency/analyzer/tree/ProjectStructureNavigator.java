package com.tomecode.soa.dependency.analyzer.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.SplitJoin;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.services.BpelProcess;
import com.tomecode.soa.util.FileRootNode;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Show all files and folders for selected project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProjectStructureNavigator extends ViewPart implements HideView {

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
		tree = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
	}

	@Override
	public final void setFocus() {

	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	public final void showProjectFiles(Object source) {
		if (source instanceof UnknownProject) {
			clearTree();
		} else if (source instanceof Project) {
			setProject((Project) source);
		} else if (source instanceof Service) {
			setProject(((Service) source).getProject());
		} else if (source instanceof SplitJoin) {
			setProject(((SplitJoin) source).getProject());
		} else if (source instanceof BpelProcess) {
			setProject(((BpelProcess) source).getProject());
		} else if (source instanceof BpelProject) {
			setProject(((BpelProject) source));
		} else {
			clearTree();
		}
	}

	private final void setProject(Project project) {
		labelProvider.setRoot(project.getFile());
		rootNode.setProject(project);
		tree.setInput(rootNode);
		tree.expandAll();
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

	@Override
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}
}
