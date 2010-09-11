package com.tomecode.soa.dependency.analyzer.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

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

	// private Composite ora10gProcessPage;
	// private Text ora10gProcessName;
	// private Text ora10gProcessType;
	// private Text ora10gProcessWsdl;
	// private Text ora10gProjectName;
	// private Text ora10gProjectPath;
	// private Text ora10gProcessWorkspaceName;
	// private Text ora10gProcessWorkspacePath;
	// private Text ora10gProcessMultiWorkspaceName;
	// private Text ora10gProcessMultiWorkspacePath;

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
		// initOra10gProcessPage(contentPanel);
		layout.topControl = emptyPage;

		contentPanel.layout();
	}

	// /**
	// * create Oracle 10g process page
	// *
	// * @param parent
	// */
	// private final void initOra10gProcessPage(Composite parent) {
	// ora10gProcessPage = new Composite(parent, SWT.NONE);
	// ora10gProcessPage.setLayout(new FillLayout());
	//
	// ScrolledComposite sc = new ScrolledComposite(ora10gProcessPage,
	// SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);
	//
	// Composite composite = new Composite(sc, SWT.NONE);
	// composite.setLayout(new GridLayout(2, false));
	// composite.setLayoutData(new GridData(GridData.FILL_BOTH |
	// GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
	//
	// createLabel(composite, "Process Type: ");
	// ora10gProcessType = createText(composite);
	// createLabel(composite, "Process Name: ");
	// ora10gProcessName = createText(composite);
	// createLabel(composite, "WSDL: ");
	// ora10gProcessWsdl = createText(composite);
	// createLabel(composite, "Project Name: ");
	// ora10gProjectName = createText(composite);
	// createLabel(composite, "Project Path: ");
	// ora10gProjectPath = createText(composite);
	// createLabel(composite, "Workspace Name: ");
	// ora10gProcessWorkspaceName = createText(composite);
	// createLabel(composite, "Workspace Path: ");
	// ora10gProcessWorkspacePath = createText(composite);
	// createLabel(composite, "Multi Workspace Name: ");
	// ora10gProcessMultiWorkspaceName = createText(composite);
	// createLabel(composite, "Multi Workspace Path: ");
	// ora10gProcessMultiWorkspacePath = createText(composite);
	// composite.pack();
	// sc.setMinSize(composite.getSize());
	// sc.setExpandHorizontal(true);
	// sc.setExpandVertical(true);
	// sc.setContent(composite);
	// }

	/**
	 * create {@link Composite} for {@link Project}
	 * 
	 * @param parent
	 */
	private final void initProjectPage(Composite parent) {
		projectPage = new Composite(parent, SWT.NONE);
		projectPage.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(projectPage, SWT.SCROLL_PAGE | SWT.H_SCROLL | SWT.V_SCROLL);

		Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

		createLabel(composite, "Project Type: ");
		txtProjectType = createText(composite);

		createLabel(composite, "Project Name: ");
		txtProjectName = createText(composite);

		createLabel(composite, "Project Path:");
		txtProjectPath = createText(composite);

		createLabel(composite, "Workspace Name: ");
		txtProjectWorkspace = createText(composite);
		createLabel(composite, "Workspace Path: ");
		txtProjectWorkspacePath = createText(composite);

		createLabel(composite, "MultiWorkspace Name: ");
		txtProjectMultiWorkspaceName = createText(composite);
		createLabel(composite, "MultiWokspace Path: ");
		txtProjectMultiWorkspacePath = createText(composite);

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
		Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

		createLabel(composite, "Workspace Type: ");
		txtWorkspaceParentType = createText(composite);
		createLabel(composite, "Workspace Name: ");
		txtWorkspaceName = createText(composite);
		createLabel(composite, "Workspace Path: ");
		txtWorkspacePath = createText(composite);

		createLabel(composite, "Multi Workspace Name: ");
		txtWorkspaceParent = createText(composite);

		createLabel(composite, "Mutli Workspace Path: ");
		txtWorkspaceParentPath = createText(composite);

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
		Composite composite = new Composite(sc, SWT.NONE);

		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
		createLabel(composite, "Type: ");
		txtMultiWorkspaceType = createText(composite);
		createLabel(composite, "Name: ");
		txtMultiWorkspaceName = createText(composite);
		createLabel(composite, "Path: ");
		txtMultiWorkspacePath = createText(composite);
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
			// } else if (data instanceof BpelProject) {
			// BpelProject bpelProject = (BpelProject) data;
			// ora10gProcessName.setText(bpelProject.toString());
			// ora10gProcessType.setText(bpelProject.getType().toString());
			// ora10gProcessWsdl.setText(bpelProject.getWsdl().getFile().toString());
			// showContent(ora10gProcessPage);
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
