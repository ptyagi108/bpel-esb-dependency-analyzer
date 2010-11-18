package com.tomecode.soa.dependency.analyzer.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.utils.HideView;
import com.tomecode.soa.dependency.analyzer.gui.utils.WindowChangeListener;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.services.BpelProcess;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Info about selected items in {@link VisualGraphView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class PropertiesView extends ViewPart implements HideView {

	public static final String ID = "view.properties";

	/**
	 * data from which will be shown properties
	 */
	private Object dataForProperties;

	private Composite contentPanel;
	private StackLayout layout;

	private Composite emptyPage;

	private Composite multiWokrspacePage;
	private Text txtMultiWorkspaceName;
	private Text txtMultiWorkspacePath;
	private Text txtMultiWorkspaceType;

	private Composite workspacePage;
	private Text txtWorkspaceParentPath;
	private Text txtWorkspaceParentType;
	private Text txtWorkspaceParent;
	private Text txtWorkspaceName;
	private Text txtWorkspacePath;

	private Composite projectPage;
	private Text txtProjectName;
	private Text txtProjectType;
	private Text txtProjectPath;
	private Text txtProjectWorkspace;
	private Text txtProjectWorkspacePath;
	private Text txtProjectMultiWorkspaceName;
	private Text txtProjectMultiWorkspacePath;

	private Composite servicePage;
	private Text txtServiceName;
	private Text txtServiceType;
	private Text txtServiceFolder;
	private Text txtServicePath;
	private Text txtServiceProjectName;
	private Text txtServiceProjectType;
	private Text txtServiceProjectPath;
	private Text txtServiceProjectWorkspace;
	private Text txtServiceProjectWorkspacePath;
	private Text txtServiceProjectMultiWorkspaceName;
	private Text txtServiceProjectMultiWorkspacePath;

	private Composite serviceDepPage;
	private Text txtServiceDepProjectName;
	private Text txtServiceDepName;
	private Text txtServiceDepOsbActivity;
	private Text txtServiceDepOsbType;

	private Composite processPage;
	private Text txtProcessName;
	private Text txtProcessType;
	private Text txtProcessPath;
	private Text txtProcessProjectName;
	private Text txtProcessProjectType;
	private Text txtProcessProjectPath;
	private Text txtProcessProjectWorkspace;
	private Text txtProcessProjectWorkspacePath;
	private Text txtProcessProjectMultiWorkspaceName;
	private Text txtProcessProjectMultiWorkspacePath;

	public PropertiesView() {
		setTitleImage(ImageFactory.PROPERTIES);
	}

	@Override
	public final void createPartControl(Composite parent) {
		contentPanel = new Composite(parent, SWT.NONE);
		layout = new StackLayout();
		contentPanel.setLayout(layout);

		emptyPage = new Composite(contentPanel, SWT.NONE);
		emptyPage.pack();
		initMultiWorkspacePage(contentPanel);
		initWorkspacePage(contentPanel);
		initProjectPage(contentPanel);
		initServicePage(contentPanel); // initOra10gProcessPage(contentPanel);
		initServiceDepPage(contentPanel);
		initProcessPage(contentPanel);
		layout.topControl = emptyPage;

		contentPanel.layout();

		// getSite().getWorkbenchWindow().addPerspectiveListener(new
		// IPerspectiveListener2() {
		//
		// @Override
		// public void perspectiveChanged(IWorkbenchPage paramIWorkbenchPage,
		// IPerspectiveDescriptor paramIPerspectiveDescriptor, String type) {
		// }
		//
		// @Override
		// public void perspectiveActivated(IWorkbenchPage paramIWorkbenchPage,
		// IPerspectiveDescriptor paramIPerspectiveDescriptor) {
		// }
		//
		// @Override
		// public void perspectiveChanged(IWorkbenchPage paramIWorkbenchPage,
		// IPerspectiveDescriptor paramIPerspectiveDescriptor,
		// IWorkbenchPartReference paramIWorkbenchPartReference, String type) {
		// System.out.println(getClass() + " 2" + type);
		// if ("viewHide".equals(type)) {
		// WindowChangeListener.getInstance().hideFromView(ID);
		// } else if ("viewShow".equals(type)) {
		// setFocus();
		// }
		// }
		// });
	}

	/**
	 * create panel for {@link BpelProcess}
	 * 
	 * @param parent
	 */
	private final void initProcessPage(Composite parent) {
		processPage = new Composite(parent, SWT.NONE);
		processPage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(processPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupProcess = GuiUtils.createGroupWithGrid(composite, "Bpel Process...");
		GuiUtils.createLabelWithGrid(groupProcess, "Type ");
		txtProcessType = GuiUtils.createTextWithGrid(groupProcess);
		GuiUtils.createLabelWithGrid(groupProcess, "Name ");
		txtProcessName = GuiUtils.createTextWithGrid(groupProcess);
		GuiUtils.createLabelWithGrid(groupProcess, "Path ");
		txtProcessPath = GuiUtils.createTextWithGrid(groupProcess);

		Group groupProject = GuiUtils.createGroupWithGrid(composite, "Project...");
		GuiUtils.createLabelWithGrid(groupProject, "Type ");
		txtProcessProjectType = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Name ");
		txtProcessProjectName = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Path:");
		txtProcessProjectPath = GuiUtils.createTextWithGrid(groupProject);

		Group groupWorkspace = GuiUtils.createGroupWithGrid(composite, "Workspace...");
		GuiUtils.createLabelWithGrid(groupWorkspace, "Name ");
		txtProcessProjectWorkspace = GuiUtils.createTextWithGrid(groupWorkspace);
		GuiUtils.createLabelWithGrid(groupWorkspace, "Path ");
		txtProcessProjectWorkspacePath = GuiUtils.createTextWithGrid(groupWorkspace);

		Group groupMultiWorkspace = GuiUtils.createGroupWithGrid(composite, "Multi Workspace...");
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Name ");
		txtProcessProjectMultiWorkspaceName = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Path ");
		txtProcessProjectMultiWorkspacePath = GuiUtils.createTextWithGrid(groupMultiWorkspace);

		finishPage(composite, sc);
	}

	/**
	 * create panel for {@link Service}
	 * 
	 * @param parent
	 */
	private final void initServicePage(Composite parent) {
		servicePage = new Composite(parent, SWT.NONE);
		servicePage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(servicePage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupService = GuiUtils.createGroupWithGrid(composite, "Service...");
		GuiUtils.createLabelWithGrid(groupService, "Type ");
		txtServiceType = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "Name ");
		txtServiceName = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "Folder ");
		txtServiceFolder = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "Path ");
		txtServicePath = GuiUtils.createTextWithGrid(groupService);

		Group groupProject = GuiUtils.createGroupWithGrid(composite, "Project...");
		GuiUtils.createLabelWithGrid(groupProject, "Type ");
		txtServiceProjectType = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Name ");
		txtServiceProjectName = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Path:");
		txtServiceProjectPath = GuiUtils.createTextWithGrid(groupProject);

		Group groupWorkspace = GuiUtils.createGroupWithGrid(composite, "Workspace...");
		GuiUtils.createLabelWithGrid(groupWorkspace, "Name ");
		txtServiceProjectWorkspace = GuiUtils.createTextWithGrid(groupWorkspace);
		GuiUtils.createLabelWithGrid(groupWorkspace, "Path ");
		txtServiceProjectWorkspacePath = GuiUtils.createTextWithGrid(groupWorkspace);

		Group groupMultiWorkspace = GuiUtils.createGroupWithGrid(composite, "Multi Workspace...");
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Name ");
		txtServiceProjectMultiWorkspaceName = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Path ");
		txtServiceProjectMultiWorkspacePath = GuiUtils.createTextWithGrid(groupMultiWorkspace);

		finishPage(composite, sc);

	}

	private final void initServiceDepPage(Composite parent) {
		serviceDepPage = new Composite(parent, SWT.NONE);
		serviceDepPage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(serviceDepPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupService = GuiUtils.createGroupWithGrid(composite, "Unknown Service...");
		GuiUtils.createLabelWithGrid(groupService, "Name ");
		txtServiceDepName = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "Type ");
		txtServiceDepOsbType = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "In Activity ");
		txtServiceDepOsbActivity = GuiUtils.createTextWithGrid(groupService);
		GuiUtils.createLabelWithGrid(groupService, "Project ");
		txtServiceDepProjectName = GuiUtils.createTextWithGrid(groupService);

		finishPage(composite, sc);
	}

	/**
	 * create {@link Composite} for {@link Project}
	 * 
	 * @param parent
	 */
	private final void initProjectPage(Composite parent) {
		projectPage = new Composite(parent, SWT.NONE);
		projectPage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(projectPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupProject = GuiUtils.createGroupWithGrid(composite, "Project...");
		GuiUtils.createLabelWithGrid(groupProject, "Type ");
		txtProjectType = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Name ");
		txtProjectName = GuiUtils.createTextWithGrid(groupProject);
		GuiUtils.createLabelWithGrid(groupProject, "Path:");
		txtProjectPath = GuiUtils.createTextWithGrid(groupProject);

		Group groupWorkspace = GuiUtils.createGroupWithGrid(composite, "Workspace...");
		GuiUtils.createLabelWithGrid(groupWorkspace, "Name ");
		txtProjectWorkspace = GuiUtils.createTextWithGrid(groupWorkspace);
		GuiUtils.createLabelWithGrid(groupWorkspace, "Path ");
		txtProjectWorkspacePath = GuiUtils.createTextWithGrid(groupWorkspace);

		Group groupMultiWorkspace = GuiUtils.createGroupWithGrid(composite, "Multi Workspace...");
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Name ");
		txtProjectMultiWorkspaceName = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Path ");
		txtProjectMultiWorkspacePath = GuiUtils.createTextWithGrid(groupMultiWorkspace);

		finishPage(composite, sc);
	}

	private void finishPage(Composite composite, ScrolledComposite sc) {
		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
	}

	/**
	 * create {@link Composite} for {@link Workspace}
	 * 
	 * @param parent
	 */
	private final void initWorkspacePage(Composite parent) {
		workspacePage = new Composite(parent, SWT.NONE);
		workspacePage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(workspacePage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupWorkspace = GuiUtils.createGroupWithGrid(composite, "Workspace...");
		GuiUtils.createLabelWithGrid(groupWorkspace, "Type ");
		txtWorkspaceParentType = GuiUtils.createTextWithGrid(groupWorkspace);
		GuiUtils.createLabelWithGrid(groupWorkspace, "Name ");
		txtWorkspaceName = GuiUtils.createTextWithGrid(groupWorkspace);
		GuiUtils.createLabelWithGrid(groupWorkspace, "Path ");
		txtWorkspacePath = GuiUtils.createTextWithGrid(groupWorkspace);

		Group groupMultiWorkspace = GuiUtils.createGroupWithGrid(composite, "Multi Workspace...");
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Name ");
		txtWorkspaceParent = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Path ");
		txtWorkspaceParentPath = GuiUtils.createTextWithGrid(groupMultiWorkspace);

		finishPage(composite, sc);
	}

	/**
	 * create {@link Composite} for {@link MultiWorkspace}
	 * 
	 * @param parent
	 */
	private final void initMultiWorkspacePage(Composite parent) {
		multiWokrspacePage = new Composite(parent, SWT.NONE);
		multiWokrspacePage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(multiWokrspacePage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = GuiUtils.createCompositeWithGrid(sc);

		Group groupMultiWorkspace = GuiUtils.createGroupWithGrid(composite, "Multi Workspace...");
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Type ");
		txtMultiWorkspaceType = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Name ");
		txtMultiWorkspaceName = GuiUtils.createTextWithGrid(groupMultiWorkspace);
		GuiUtils.createLabelWithGrid(groupMultiWorkspace, "Path ");
		txtMultiWorkspacePath = GuiUtils.createTextWithGrid(groupMultiWorkspace);

		finishPage(composite, sc);
	}

	@Override
	public final void setFocus() {

	}

	public void dispose() {
		WindowChangeListener.getInstance().hideFromView(ID);
		super.dispose();
	}

	/**
	 * hide view
	 */
	public final void hideMe() {
		getSite().getPage().hideView(this);
	}

	/**
	 * show properties from object
	 * 
	 * @param data
	 */
	public final void show(Object data) {
		dataForProperties = data;

		if (dataForProperties instanceof MultiWorkspace) {
			MultiWorkspace multiWorkspace = (MultiWorkspace) dataForProperties;
			txtMultiWorkspaceType.setText(multiWorkspace.getType().toString());
			txtMultiWorkspaceName.setText(multiWorkspace.getName());
			txtMultiWorkspacePath.setText(multiWorkspace.getFile().toString());
			showContent(multiWokrspacePage);
		} else if (dataForProperties instanceof Workspace) {
			Workspace workspace = (Workspace) dataForProperties;
			txtWorkspaceParentType.setText(workspace.getMultiWorkspace().getType().toString());
			txtWorkspaceParent.setText(workspace.getMultiWorkspace().getName());
			txtWorkspaceParentPath.setText(workspace.getMultiWorkspace().getFile().toString());
			txtWorkspaceName.setText(workspace.getName());
			txtWorkspacePath.setText(workspace.getFile().toString());
			showContent(workspacePage);
		} else if (dataForProperties instanceof Project) {
			Project project = (Project) dataForProperties;
			txtProjectName.setText(project.getName());
			txtProjectType.setText(project.getType().toString());
			txtProjectPath.setText(project.getFile() == null ? "" : project.getFile().toString());

			if (project.getWorkpsace() == null) {
				txtProjectWorkspace.setText("");
				txtProjectWorkspacePath.setText("");
				txtProjectMultiWorkspacePath.setText("");
				txtProjectMultiWorkspaceName.setText("");
			} else {
				txtProjectWorkspace.setText(project.getWorkpsace().getName());
				txtProjectWorkspacePath.setText(project.getWorkpsace().getFile().toString());
				txtProjectMultiWorkspacePath.setText(project.getWorkpsace().getMultiWorkspace().getFile().toString());
				txtProjectMultiWorkspaceName.setText(project.getWorkpsace().getMultiWorkspace().getName());
			}
			showContent(projectPage);
		} else if (dataForProperties instanceof Service) {
			Service service = (Service) dataForProperties;

			txtServiceName.setText(service.getName());
			txtServiceFolder.setText(service.getFolder() == null ? "" : service.getFolder().toString());
			txtServicePath.setText(service.getFile() == null ? "" : service.getFile().toString());
			txtServiceType.setText(service.getType().toString());
			Project project = (Project) service.getProject();
			txtServiceProjectName.setText(project.getName());
			txtServiceProjectType.setText(project.getType().toString());
			txtServiceProjectPath.setText(project.getFile().toString());
			txtServiceProjectWorkspace.setText(project.getWorkpsace().getName());
			txtServiceProjectWorkspacePath.setText(project.getWorkpsace().getFile().toString());
			txtServiceProjectMultiWorkspacePath.setText(project.getWorkpsace().getMultiWorkspace().getFile().toString());
			txtServiceProjectMultiWorkspaceName.setText(project.getWorkpsace().getMultiWorkspace().getName());
			showContent(servicePage);
		} else if (dataForProperties instanceof ServiceDependency) {
			ServiceDependency dependency = (ServiceDependency) dataForProperties;
			txtServiceDepName.setText(dependency.getRefPath());
			txtServiceDepOsbType.setText(dependency.getType().toString());
			String name = dependency.getServiceName();
			txtServiceDepOsbActivity.setText(name == null ? "" : name);
			txtServiceDepOsbActivity.setText(dependency.getActivity().toString());
			txtServiceDepProjectName.setText(dependency.getProjectNameFromRefPath());
			showContent(serviceDepPage);
		} else if (dataForProperties instanceof BpelProcess) {
			BpelProcess bpelProcess = (BpelProcess) dataForProperties;
			txtProcessName.setText(bpelProcess.getName());
			txtProcessPath.setText(bpelProcess.getFile() == null ? "" : bpelProcess.getFile().toString());
			txtProcessType.setText(bpelProcess.getProject().getType().toString());
			Project project = (Project) bpelProcess.getProject();
			txtProcessProjectName.setText(project.getName());
			txtProcessProjectType.setText(project.getType().toString());
			txtProcessProjectPath.setText(project.getFile().toString());
			txtProcessProjectWorkspace.setText(project.getWorkpsace().getName());
			txtProcessProjectWorkspacePath.setText(project.getWorkpsace().getFile().toString());
			txtProcessProjectMultiWorkspacePath.setText(project.getWorkpsace().getMultiWorkspace().getFile().toString());
			txtProcessProjectMultiWorkspaceName.setText(project.getWorkpsace().getMultiWorkspace().getName());
			showContent(processPage);
		} else {
			showContent(emptyPage);
		}
	}

	private final void showContent(Composite composite) {
		layout.topControl = composite;
		contentPanel.layout();
	}

	public final void removeMultiWorkspace(MultiWorkspace multiWorkspace) {
		Object multiWorkspaceInTree = findMultiWorkspaceInTree();
		if (multiWorkspace.equals(multiWorkspaceInTree)) {
			showContent(emptyPage);
		}
	}

	private final Object findMultiWorkspaceInTree() {
		if (dataForProperties == null) {
			return null;
		} else if (dataForProperties instanceof MultiWorkspace) {
			return dataForProperties;
		} else if (dataForProperties instanceof Workspace) {
			return ((Workspace) dataForProperties).getMultiWorkspace();
		} else if (dataForProperties instanceof Project) {
			return ((Project) dataForProperties).getWorkpsace().getMultiWorkspace();
		}
		return null;
	}

	private final Workspace findWorkspaceInTree() {
		if (dataForProperties == null) {
			return null;
		} else if (dataForProperties instanceof Workspace) {
			return ((Workspace) dataForProperties);
		} else if (dataForProperties instanceof Project) {
			return ((Project) dataForProperties).getWorkpsace();
		}

		return null;
	}

	public final void removeWorkspace(Workspace workspace) {
		Workspace workspaceInTree = findWorkspaceInTree();
		if (workspace.equals(workspaceInTree)) {
			showContent(emptyPage);
		}
	}
}
