package com.tomecode.soa.dependency.analyzer.gui.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Wizard to open a new {@link Project}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class AddNewProjectToWorkspaceWizard extends Wizard {

	private final static String TITLE = "Add new Project to Workspace";

	/**
	 * The resulting configuration
	 */
	private final AddNewProjectToWorkspaceConfig config;

	/**
	 * Constructor
	 */
	public AddNewProjectToWorkspaceWizard() {
		super();
		setWindowTitle(TITLE);
		config = new AddNewProjectToWorkspaceConfig();
		addPage(new SelectWorkspacePage());
		addPage(new OSBPage());
		addPage(new SOASuite10gPage());
	}

	@Override
	public final boolean performFinish() {
		return true;
	}

	public final boolean canFinish() {
		IWizardPage iWizardPage = getContainer().getCurrentPage();
		if (iWizardPage.getName().equals("OSB10g")) {
			return iWizardPage.isPageComplete();
		} else if (iWizardPage.getName().equals("ORACLE10g")) {
			return iWizardPage.isPageComplete();
		}
		return false;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Select workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SelectWorkspacePage extends WizardPage {

		private Combo comboMultiWorkspace;
		private Combo comboWorkspace;

		private List<MultiWorkspace> multiWorkspaces;

		private String[] multiWorkspacesItems = new String[] {};

		/**
		 * Constructor
		 */
		private SelectWorkspacePage() {
			super("Add new Project", TITLE, ImageFactory.openBig);
			setTitle("Select workspace...");

			multiWorkspaces = ApplicationManager.getInstance().getMultiWorkspaces();
			multiWorkspacesItems = new String[multiWorkspaces.size()];
			for (int i = 0; i <= multiWorkspaces.size() - 1; i++) {
				multiWorkspacesItems[i] = multiWorkspaces.get(i).getName();
			}
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			Group group = new Group(composite, SWT.SHADOW_IN);
			group.setText("Add new project to:");
			group.setLayout(new GridLayout(1, false));
			group.setLayoutData(createGridData());

			Composite compositeGroupWorkspaces = new Composite(group, SWT.NULL);
			compositeGroupWorkspaces.setLayout(new GridLayout(2, false));
			compositeGroupWorkspaces.setLayoutData(createGridData());

			GuiUtils.createLabelWithGrid(compositeGroupWorkspaces, "Multi-Workspace");
			comboMultiWorkspace = new Combo(compositeGroupWorkspaces, SWT.READ_ONLY);
			comboMultiWorkspace.setLayoutData(createGridData());
			comboMultiWorkspace.setItems(multiWorkspacesItems);
			comboMultiWorkspace.addSelectionListener(new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					if (comboMultiWorkspace.getSelectionIndex() == -1) {
						comboWorkspace.setItems(new String[] {});
						config.setSelectedMultiWorkspace(null);
						config.setSelectedWorkspace(null);
						setPageComplete(false);
					} else {
						setPageComplete(false);
						for (MultiWorkspace multiWorkspace : multiWorkspaces) {
							if (multiWorkspace.getName().equals(comboMultiWorkspace.getItem(comboMultiWorkspace.getSelectionIndex()))) {
								config.setSelectedMultiWorkspace(multiWorkspace);
								config.setSelectedWorkspace(null);
								List<Workspace> workspaces = config.getSelectedMultiWorkspace().getWorkspaces();
								String[] workspaceItems = new String[workspaces.size()];
								for (int i = 0; i <= workspaces.size() - 1; i++) {
									workspaceItems[i] = workspaces.get(i).getName();
								}

								comboWorkspace.setItems(workspaceItems);
								break;
							}
						}
					}
				}
			});

			GuiUtils.createLabelWithGrid(compositeGroupWorkspaces, "Workspace");
			comboWorkspace = new Combo(compositeGroupWorkspaces, SWT.READ_ONLY);
			comboWorkspace.setLayoutData(createGridData());
			comboWorkspace.addSelectionListener(new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					if (comboWorkspace.getSelectionIndex() == -1) {
						config.setSelectedWorkspace(null);
						setPageComplete(false);
					} else {
						config.setSelectedWorkspace(config.getSelectedMultiWorkspace().getWorkspaces().get(comboWorkspace.getSelectionIndex()));
						setPageComplete(true);
					}
				}
			});

			setControl(composite);
			setPageComplete(false);
		}

		public final IWizardPage getNextPage() {
			if (config.getSelectedMultiWorkspace() != null && config.getSelectedWorkspace() != null) {
				if (config.getSelectedMultiWorkspace().getType() == WorkspaceType.ORACLE_SERVICE_BUS_10G) {
					return getPage("OSB10g");
				} else if (config.getSelectedMultiWorkspace().getType() == WorkspaceType.ORACLE_1OG) {
					return getPage("ORACLE10g");
				}
			}

			return null;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * OSB Page
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class OSBPage extends WizardPage implements Listener {

		private Button buttonAsProjectFolder;
		private Button buttonAsProjectJar;

		private Text textFileAsJar;
		private Text textFileAsFolder;

		private Button bDirdialogJar;
		private Button bDirdialogAsFolder;

		private OSBPage() {
			super("OSB10g", TITLE, ImageFactory.openBig);
			setTitle("Add new OSB Project...");

		}

		public final IWizardPage getNextPage() {
			return null;
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			Group groupSwithProject = createGroupDataEnter(composite, "Add new OSB project:");
			Composite compositeSwithProject = createOneColumnGrid(groupSwithProject);
			buttonAsProjectFolder = new Button(compositeSwithProject, SWT.RADIO);
			createRadioButton2(buttonAsProjectFolder, "OSB folder/project", this);
			Composite compositeAsJarFolder = createTwoColumnGrid(compositeSwithProject);
			textFileAsFolder = createText(compositeAsJarFolder, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogAsFolder = createDialogButton(compositeAsJarFolder, new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = createDirectoryDialog(getShell(), textFileAsFolder.getText(), "Select OSB Project directory...", "Select OSB Project directory...");

					String dir = dialog.open();
					if (dir != null) {
						textFileAsFolder.setText(dir);
					}
					validateData();
				}
			});

			// *******************************
			buttonAsProjectJar = new Button(compositeSwithProject, SWT.RADIO);// createRadioButton(compositeSwithProject,
																				// "as OSB JAR",
																				// this);
			createRadioButton2(buttonAsProjectJar, "OSB jar", this);
			Composite compositeAsJarText = createTwoColumnGrid(compositeSwithProject);
			textFileAsJar = createText(compositeAsJarText, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogJar = createDialogButton(compositeAsJarText, new SelectionAdapter() {

				@Override
				public final void widgetSelected(SelectionEvent event) {
					FileDialog dialog = createFileDialog(getShell(), textFileAsJar.getText(), "Select OSB Project as JAR...", "*.jar");
					String jar = dialog.open();
					if (jar != null) {
						textFileAsJar.setText(jar);
					}
					validateData();
				}

			});

			handleEvent(new Event());
			setControl(composite);
			setPageComplete(false);
		}

		@Override
		public final void handleEvent(Event event) {
			if (buttonAsProjectFolder.getSelection()) {
				textFileAsFolder.setEnabled(true);
				bDirdialogAsFolder.setEnabled(true);

				textFileAsJar.setEnabled(false);
				bDirdialogJar.setEnabled(false);
			} else if (buttonAsProjectJar.getSelection()) {
				textFileAsFolder.setEnabled(false);
				bDirdialogAsFolder.setEnabled(false);

				textFileAsJar.setEnabled(true);
				bDirdialogJar.setEnabled(true);
			}
		}

		/**
		 * validate data in components
		 */
		private final void validateData() {
			setPageComplete(false);
			if (config.getSelectedMultiWorkspace() != null && config.getSelectedWorkspace() != null) {

				if (buttonAsProjectFolder.getSelection()) {
					config.setAsFolder(true);
					String folder = textFileAsFolder.getText().trim();
					if (folder.length() != 0) {
						File f = new File(folder);
						if (f.exists() && f.isDirectory()) {
							config.setPath(f);
							config.setProjectType(ProjectType.ORACLE_SERVICE_BUS_1OG);
							setPageComplete(true);
						}
					}
				} else if (buttonAsProjectJar.getSelection()) {
					config.setAsFolder(false);
					String jar = textFileAsJar.getText().trim();
					if (jar.length() != 0) {
						File f = new File(jar);
						if (f.exists() && f.isFile() && f.getName().toLowerCase().endsWith(".jar")) {
							config.setPath(f);
							config.setProjectType(ProjectType.ORACLE_SERVICE_BUS_1OG);
							setPageComplete(true);
						}
					}
				}

			}
		}

	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * SOA Suite 10g Page
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SOASuite10gPage extends WizardPage implements Listener {

		private Button buttonBPEL10gProject;
		private Text textFileBPEL10gProject;
		private Button bDirdialogBPEL10gProject;

		private Button buttonBPEL10gJar;
		private Text textFileBPEL10gJar;
		private Button bDirdialogBPEL10gJar;

		private Button buttonESB10gProject;
		private Text textFileESB10gProject;
		private Button bDirdialogESB10gProject;

		private Button buttonESB10gZip;
		private Text textFileESB10gZip;
		private Button bDirdialogESB10gZip;

		/**
		 * Constructor
		 */
		private SOASuite10gPage() {
			super("ORACLE10g", TITLE, ImageFactory.openBig);
			setTitle("Add new SOA Suite 10g Project...");
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			Group groupSwithProject = createGroupDataEnter(composite, "Add new SOA Suite 10g project:");
			Composite compositeSwithProject = createOneColumnGrid(groupSwithProject);
			buttonBPEL10gProject = new Button(compositeSwithProject, SWT.RADIO);
			createRadioButton2(buttonBPEL10gProject, "as BPEL 10g folder/project", this);
			Composite cBPEL10gProject = createTwoColumnGrid(compositeSwithProject);
			textFileBPEL10gProject = createText(cBPEL10gProject, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogBPEL10gProject = createDialogButton(cBPEL10gProject, new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = createDirectoryDialog(getShell(), textFileBPEL10gProject.getText(), "Select BPEL Project directory...", "Select BPEL Project directory...");
					String dir = dialog.open();
					if (dir != null) {
						textFileBPEL10gProject.setText(dir);
					}
					validateData();
				}
			});

			buttonBPEL10gJar = new Button(compositeSwithProject, SWT.RADIO);
			createRadioButton2(buttonBPEL10gJar, "as BPEL 10g JAR", this);
			Composite cBPEL10gJar = createTwoColumnGrid(compositeSwithProject);
			textFileBPEL10gJar = createText(cBPEL10gJar, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogBPEL10gJar = createDialogButton(cBPEL10gJar, new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					FileDialog dialog = createFileDialog(getShell(), textFileBPEL10gJar.getText(), "Select BPEL Project as JAR...", "*.jar");
					String jar = dialog.open();
					if (jar != null) {
						textFileBPEL10gJar.setText(jar);
					}
					validateData();
				}
			});
			buttonESB10gProject = new Button(compositeSwithProject, SWT.RADIO);
			createRadioButton2(buttonESB10gProject, "as ESB 10g folder/project", this);
			Composite cESB10gProject = createTwoColumnGrid(compositeSwithProject);
			textFileESB10gProject = createText(cESB10gProject, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogESB10gProject = createDialogButton(cESB10gProject, new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = createDirectoryDialog(getShell(), textFileESB10gProject.getText(), "Select ESB Project directory...", "Select ESB Project directory...");
					String dir = dialog.open();
					if (dir != null) {
						textFileESB10gProject.setText(dir);
					}
					validateData();
				}
			});
			buttonESB10gZip = new Button(compositeSwithProject, SWT.RADIO);
			createRadioButton2(buttonESB10gZip, "as ESB 10g Zip", this);
			Composite cESB10gZip = createTwoColumnGrid(compositeSwithProject);
			textFileESB10gZip = createText(cESB10gZip, new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogESB10gZip = createDialogButton(cESB10gZip, new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent event) {
					FileDialog dialog = createFileDialog(getShell(), textFileESB10gZip.getText(), "Select ESB Project as Zip...", "*.zip");
					String jar = dialog.open();
					if (jar != null) {
						textFileESB10gZip.setText(jar);
					}
					validateData();
				}
			});

			handleEvent(new Event());

			setControl(composite);
			setPageComplete(false);
		}

		public final IWizardPage getNextPage() {
			return null;
		}

		private final void validateData() {

			setPageComplete(false);
			if (buttonBPEL10gProject.getSelection()) {
				File f = new File(textFileBPEL10gProject.getText());
				if (f.exists() && f.isDirectory()) {
					config.setPath(f);
					config.setProjectType(ProjectType.ORACLE10G_BPEL);
					config.setAsFolder(true);
					setPageComplete(true);
				}
			} else if (buttonBPEL10gJar.getSelection()) {
				File f = new File(textFileBPEL10gJar.getText());
				if (f.exists() && f.isFile()) {
					config.setPath(f);
					config.setProjectType(ProjectType.ORACLE10G_BPEL);
					config.setAsFolder(false);
					setPageComplete(true);
				}
			} else if (buttonESB10gProject.getSelection()) {
				File f = new File(textFileESB10gProject.getText());
				if (f.exists() && f.isDirectory()) {
					config.setPath(f);
					config.setProjectType(ProjectType.ORACLE10G_ESB);
					config.setAsFolder(true);
					setPageComplete(true);
				}
			} else if (buttonESB10gZip.getSelection()) {
				File f = new File(textFileESB10gZip.getText());
				if (f.exists() && f.isFile()) {
					config.setPath(f);
					config.setProjectType(ProjectType.ORACLE10G_ESB);
					config.setAsFolder(false);
					setPageComplete(true);
				}
			}
		}

		@Override
		public final void handleEvent(Event event) {
			if (buttonBPEL10gProject.getSelection()) {
				textFileBPEL10gProject.setEnabled(true);
				bDirdialogBPEL10gProject.setEnabled(true);
				textFileBPEL10gJar.setEnabled(false);
				bDirdialogBPEL10gJar.setEnabled(false);
				textFileESB10gProject.setEnabled(false);
				bDirdialogESB10gProject.setEnabled(false);
				textFileESB10gZip.setEnabled(false);
				bDirdialogESB10gZip.setEnabled(false);
			} else if (buttonBPEL10gJar.getSelection()) {
				textFileBPEL10gJar.setEnabled(true);
				bDirdialogBPEL10gJar.setEnabled(true);
				textFileBPEL10gProject.setEnabled(false);
				bDirdialogBPEL10gProject.setEnabled(false);
				textFileESB10gProject.setEnabled(false);
				bDirdialogESB10gProject.setEnabled(false);
				textFileESB10gZip.setEnabled(false);
				bDirdialogESB10gZip.setEnabled(false);
			} else if (buttonESB10gProject.getSelection()) {
				textFileESB10gProject.setEnabled(true);
				bDirdialogESB10gProject.setEnabled(true);
				textFileBPEL10gProject.setEnabled(false);
				bDirdialogBPEL10gProject.setEnabled(false);
				textFileBPEL10gJar.setEnabled(false);
				bDirdialogBPEL10gJar.setEnabled(false);
				textFileESB10gZip.setEnabled(false);
				bDirdialogESB10gZip.setEnabled(false);
			} else if (buttonESB10gZip.getSelection()) {
				textFileESB10gZip.setEnabled(true);
				bDirdialogESB10gZip.setEnabled(true);
				textFileBPEL10gProject.setEnabled(false);
				bDirdialogBPEL10gProject.setEnabled(false);
				textFileBPEL10gJar.setEnabled(false);
				bDirdialogBPEL10gJar.setEnabled(false);
				textFileESB10gProject.setEnabled(false);
				bDirdialogESB10gProject.setEnabled(false);
			} else {
				textFileBPEL10gProject.setEnabled(false);
				bDirdialogBPEL10gProject.setEnabled(false);
				textFileBPEL10gJar.setEnabled(false);
				bDirdialogBPEL10gJar.setEnabled(false);
				textFileESB10gProject.setEnabled(false);
				bDirdialogESB10gProject.setEnabled(false);
				textFileESB10gZip.setEnabled(false);
				bDirdialogESB10gZip.setEnabled(false);
			}
		}

	}

	/**
	 * the resulting configuration
	 * 
	 * @return
	 */
	public AddNewProjectToWorkspaceConfig getConfig() {
		return config;
	}

	/**
	 * create {@link GridData}
	 * 
	 * @return
	 */
	private static final GridData createGridData() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 20;
		gridData.grabExcessHorizontalSpace = true;
		return gridData;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * The resulting configuration
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public static final class AddNewProjectToWorkspaceConfig {

		/**
		 * selected {@link MultiWorkspace}
		 */
		private MultiWorkspace selectedMultiWorkspace;
		/**
		 * selected {@link Workspace}
		 */
		private Workspace selectedWorkspace;
		/**
		 * if is true then "as folder" if false then "as JAR"
		 */
		private boolean asFolder;

		private ProjectType projectType;

		private File path;

		/**
		 * @return the selectedMultiWorkspace
		 */
		public final MultiWorkspace getSelectedMultiWorkspace() {
			return selectedMultiWorkspace;
		}

		/**
		 * @return the selectedWorkspace
		 */
		public final Workspace getSelectedWorkspace() {
			return selectedWorkspace;
		}

		/**
		 * @return the asFolder
		 */
		public final boolean isAsFolder() {
			return asFolder;
		}

		/**
		 * @return the path
		 */
		public final File getPath() {
			return path;
		}

		/**
		 * @param selectedMultiWorkspace
		 *            the selectedMultiWorkspace to set
		 */
		private final void setSelectedMultiWorkspace(MultiWorkspace selectedMultiWorkspace) {
			this.selectedMultiWorkspace = selectedMultiWorkspace;
		}

		/**
		 * @param selectedWorkspace
		 *            the selectedWorkspace to set
		 */
		private final void setSelectedWorkspace(Workspace selectedWorkspace) {
			this.selectedWorkspace = selectedWorkspace;
		}

		/**
		 * @param asFolder
		 *            the asFolder to set
		 */
		private final void setAsFolder(boolean asFolder) {
			this.asFolder = asFolder;
		}

		/**
		 * @param path
		 *            the path to set
		 */
		private final void setPath(File path) {
			this.path = path;
		}

		/**
		 * @return the projectType
		 */
		public final ProjectType getProjectType() {
			return projectType;
		}

		/**
		 * @param projectType
		 *            the projectType to set
		 */
		public final void setProjectType(ProjectType projectType) {
			this.projectType = projectType;
		}

	}

	private static final Composite createOneColumnGrid(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(createGridData());
		return composite;
	}

	private static final Composite createTwoColumnGrid(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(createGridData());
		return composite;
	}

	private static final Group createGroupDataEnter(Composite parent, String title) {
		Group group = new Group(parent, SWT.SHADOW_IN);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(createGridData());
		group.setText(title);
		return group;
	}

	private static final Button createRadioButton2(Button button, String title, Listener listener) {
		button.setText(title);
		button.setSelection(true);
		button.addListener(SWT.Selection, listener);
		return button;
	}

	private static final Text createText(Composite parent, ModifyListener modifyListener) {
		Text text = new Text(parent, SWT.BORDER);
		text.setEnabled(true);
		text.addModifyListener(modifyListener);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(gridData);
		return text;
	}

	private static final Button createDialogButton(Composite parent, SelectionListener listener) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText("...");
		button.setEnabled(false);
		button.setSelection(false);
		button.setLayoutData(new GridData(SWT.WRAP));
		button.addSelectionListener(listener);
		return button;
	}

	private static FileDialog createFileDialog(Shell parent, String inputText, String title, String extensions) {
		FileDialog dialog = new FileDialog(parent);
		dialog.setFilterPath(inputText);
		dialog.setText(title);
		dialog.setFilterExtensions(new String[] { extensions });
		return dialog;
	}

	private static final DirectoryDialog createDirectoryDialog(Shell parent, String inputText, String message, String txt) {
		DirectoryDialog dialog = new DirectoryDialog(parent);
		dialog.setFilterPath(inputText);
		dialog.setMessage(message);
		dialog.setText(txt);
		return dialog;
	}
}
