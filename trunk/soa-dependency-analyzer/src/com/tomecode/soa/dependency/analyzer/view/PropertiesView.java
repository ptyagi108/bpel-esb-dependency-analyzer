package com.tomecode.soa.dependency.analyzer.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Info about selected items in {@link VisualGraphView}
 * 
 * @author Tomas Frastia
 * 
 */
public final class PropertiesView extends ViewPart {

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

	public PropertiesView() {

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
		layout.topControl = emptyPage;

		contentPanel.layout();
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
		Composite composite = createComposite(sc);

		Group groupService = createGroup(composite, "Service...");
		createLabel(groupService, "Type: ");
		txtServiceType = createText(groupService);
		createLabel(groupService, "Name: ");
		txtServiceName = createText(groupService);
		createLabel(groupService, "Folder: ");
		txtServiceFolder = createText(groupService);
		createLabel(groupService, "Path: ");
		txtServicePath = createText(groupService);

		Group groupProject = createGroup(composite, "Project...");
		createLabel(groupProject, "Type: ");
		txtServiceProjectType = createText(groupProject);
		createLabel(groupProject, "Name: ");
		txtServiceProjectName = createText(groupProject);
		createLabel(groupProject, "Path:");
		txtServiceProjectPath = createText(groupProject);

		Group groupWorkspace = createGroup(composite, "Workspace...");
		createLabel(groupWorkspace, "Name: ");
		txtServiceProjectWorkspace = createText(groupWorkspace);
		createLabel(groupWorkspace, "Path: ");
		txtServiceProjectWorkspacePath = createText(groupWorkspace);

		Group groupMultiWorkspace = createGroup(composite, "Multi Workspace...");
		createLabel(groupMultiWorkspace, "Name: ");
		txtServiceProjectMultiWorkspaceName = createText(groupMultiWorkspace);
		createLabel(groupMultiWorkspace, "Path: ");
		txtServiceProjectMultiWorkspacePath = createText(groupMultiWorkspace);

		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);

	}

	private final void initServiceDepPage(Composite parent) {
		serviceDepPage = new Composite(parent, SWT.NONE);
		serviceDepPage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(serviceDepPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = createComposite(sc);

		Group groupService = createGroup(composite, "Unknown Service...");
		createLabel(groupService, "Name: ");
		txtServiceDepName = createText(groupService);
		createLabel(groupService, "Type: ");
		txtServiceDepOsbType = createText(groupService);
		createLabel(groupService, "In Activity: ");
		txtServiceDepOsbActivity = createText(groupService);
		createLabel(groupService, "Project: ");
		txtServiceDepProjectName = createText(groupService);

		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
	}

	private final Composite createComposite(ScrolledComposite sc) {
		Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
		return composite;
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
		Composite composite = createComposite(sc);

		Group groupProject = createGroup(composite, "Project...");
		createLabel(groupProject, "Type: ");
		txtProjectType = createText(groupProject);
		createLabel(groupProject, "Name: ");
		txtProjectName = createText(groupProject);
		createLabel(groupProject, "Path:");
		txtProjectPath = createText(groupProject);

		Group groupWorkspace = createGroup(composite, "Workspace...");
		createLabel(groupWorkspace, "Name: ");
		txtProjectWorkspace = createText(groupWorkspace);
		createLabel(groupWorkspace, "Path: ");
		txtProjectWorkspacePath = createText(groupWorkspace);

		Group groupMultiWorkspace = createGroup(composite, "Multi Workspace...");
		createLabel(groupMultiWorkspace, "Name: ");
		txtProjectMultiWorkspaceName = createText(groupMultiWorkspace);
		createLabel(groupMultiWorkspace, "Path: ");
		txtProjectMultiWorkspacePath = createText(groupMultiWorkspace);

		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);

	}

	private final Group createGroup(Composite composite, String title) {
		Group group = new Group(composite, SWT.SHADOW_IN);
		group.setText(title);
		group.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
		group.setLayout(new GridLayout(2, false));
		return group;
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
		Composite composite = createComposite(sc);

		Group groupWorkspace = createGroup(composite, "Workspace...");
		createLabel(groupWorkspace, "Type: ");
		txtWorkspaceParentType = createText(groupWorkspace);
		createLabel(groupWorkspace, "Name: ");
		txtWorkspaceName = createText(groupWorkspace);
		createLabel(groupWorkspace, "Path: ");
		txtWorkspacePath = createText(groupWorkspace);

		Group groupMultiWorkspace = createGroup(composite, "Multi Workspace...");
		createLabel(groupMultiWorkspace, "Name: ");
		txtWorkspaceParent = createText(groupMultiWorkspace);
		createLabel(groupMultiWorkspace, "Path: ");
		txtWorkspaceParentPath = createText(groupMultiWorkspace);

		composite.pack();
		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
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
		Composite composite = createComposite(sc);

		Group groupMultiWorkspace = createGroup(composite, "Multi Workspace...");
		createLabel(groupMultiWorkspace, "Type: ");
		txtMultiWorkspaceType = createText(groupMultiWorkspace);
		createLabel(groupMultiWorkspace, "Name: ");
		txtMultiWorkspaceName = createText(groupMultiWorkspace);
		createLabel(groupMultiWorkspace, "Path: ");
		txtMultiWorkspacePath = createText(groupMultiWorkspace);
		composite.pack();

		sc.setMinSize(composite.getSize());
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setContent(composite);
	}

	private final Label createLabel(Composite parent, String title) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData());
		label.setText(title);
		return label;
	}

	private final Text createText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText("");
		return text;
	}

	@Override
	public final void setFocus() {

	}

	/**
	 * show properties from object
	 * 
	 * @param data
	 */
	public final void showProperties(Object data) {
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
			txtProjectPath.setText(project.getFile().toString());
			txtProjectWorkspace.setText(project.getWorkpsace().getName());
			txtProjectWorkspacePath.setText(project.getWorkpsace().getFile().toString());
			txtProjectMultiWorkspacePath.setText(project.getWorkpsace().getMultiWorkspace().getFile().toString());
			txtProjectMultiWorkspaceName.setText(project.getWorkpsace().getMultiWorkspace().getName());
			showContent(projectPage);
		} else if (dataForProperties instanceof Service) {
			Service service = (Service) dataForProperties;

			txtServiceName.setText(service.getName());
			txtServiceFolder.setText(service.getFolder() == null ? "" : service.getFolder().toString());
			txtServicePath.setText(service.getFile().toString());
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
			txtServiceDepProjectName.setText(dependency.getProjectName());
			showContent(serviceDepPage);
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
