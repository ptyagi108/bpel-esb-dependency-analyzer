package com.tomecode.soa.dependency.analyzer.gui.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Text;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

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
		addPage(new AddProjectPage());
	}

	@Override
	public final boolean performFinish() {
		return true;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * 
	 * Project page
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class AddProjectPage extends WizardPage implements Listener {

		private Combo comboMultiWorkspace;
		private Combo comboWorkspace;

		private Button buttonAsProjectFolder;
		private Button buttonAsProjectJar;

		private Text textFileAsJar;
		private Text textFileAsFolder;

		private Button bDirdialogJar;
		private Button bDirdialogAsFolder;

		private List<MultiWorkspace> multiWorkspaces;
		private MultiWorkspace selectedMultiWorkspace;
		private Workspace selectedWorkspace;

		private String[] multiWorkspacesItems = new String[] {};

		private AddProjectPage() {
			super("Add new Project", TITLE, ImageFactory.openBig);
			setTitle("Add new Project...");

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
			group.setText("Add project to:");
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
						selectedMultiWorkspace = null;
					} else {
						for (MultiWorkspace multiWorkspace : multiWorkspaces) {
							if (multiWorkspace.getName().equals(comboMultiWorkspace.getItem(comboMultiWorkspace.getSelectionIndex()))) {
								selectedMultiWorkspace = multiWorkspace;

								List<Workspace> workspaces = selectedMultiWorkspace.getWorkspaces();
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
						selectedWorkspace = null;
					} else {
						selectedWorkspace = selectedMultiWorkspace.getWorkspaces().get(comboWorkspace.getSelectionIndex());
					}
				}
			});

			Group groupSwithProject = new Group(composite, SWT.SHADOW_IN);
			groupSwithProject.setLayout(new GridLayout(1, false));
			groupSwithProject.setLayoutData(createGridData());
			groupSwithProject.setText("Add new project:");

			Composite compositeSwithProject = new Composite(groupSwithProject, SWT.NONE);
			compositeSwithProject.setLayout(new GridLayout(1, false));
			compositeSwithProject.setLayoutData(createGridData());
			buttonAsProjectFolder = new Button(compositeSwithProject, SWT.RADIO);
			buttonAsProjectFolder.setText("as folder");
			buttonAsProjectFolder.setSelection(true);
			buttonAsProjectFolder.addListener(SWT.Selection, this);

			Composite compositeAsJarFolder = new Composite(compositeSwithProject, SWT.NULL);
			compositeAsJarFolder.setLayout(new GridLayout(2, false));
			compositeAsJarFolder.setLayoutData(createGridData());

			textFileAsFolder = new Text(compositeAsJarFolder, SWT.BORDER);
			GridData gridDataAsFolder = new GridData();
			gridDataAsFolder.horizontalAlignment = SWT.FILL;
			gridDataAsFolder.grabExcessHorizontalSpace = true;
			textFileAsFolder.setLayoutData(gridDataAsFolder);
			textFileAsFolder.setEnabled(true);
			textFileAsFolder.addModifyListener(new ModifyListener() {

				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogAsFolder = new Button(compositeAsJarFolder, SWT.PUSH);
			bDirdialogAsFolder.setText("...");
			bDirdialogAsFolder.setEnabled(true);
			bDirdialogAsFolder.setLayoutData(new GridData(SWT.WRAP));
			bDirdialogAsFolder.addSelectionListener(new SelectionAdapter() {

				@Override
				public final void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = new DirectoryDialog(getShell());
					dialog.setFilterPath(textFileAsFolder.getText());
					dialog.setMessage("Select project directory...");
					dialog.setText("Select project directory...");

					String dir = dialog.open();
					if (dir != null) {
						textFileAsFolder.setText(dir);
					}
					validateData();
				}
			});

			// *******************************
			buttonAsProjectJar = new Button(compositeSwithProject, SWT.RADIO);
			buttonAsProjectJar.setText("as JAR");
			buttonAsProjectJar.setSelection(false);
			buttonAsProjectJar.addListener(SWT.Selection, this);

			Composite compositeAsJarText = new Composite(compositeSwithProject, SWT.NULL);
			compositeAsJarText.setLayout(new GridLayout(2, false));
			compositeAsJarText.setLayoutData(createGridData());

			textFileAsJar = new Text(compositeAsJarText, SWT.BORDER);
			GridData gridDataAsJar = new GridData();
			gridDataAsJar.horizontalAlignment = SWT.FILL;
			gridDataAsJar.grabExcessHorizontalSpace = true;
			textFileAsJar.setLayoutData(gridDataAsJar);
			textFileAsJar.setEnabled(false);
			textFileAsJar.addModifyListener(new ModifyListener() {

				@Override
				public final void modifyText(ModifyEvent event) {
					validateData();
				}
			});
			bDirdialogJar = new Button(compositeAsJarText, SWT.PUSH);
			bDirdialogJar.setText("...");
			bDirdialogJar.setEnabled(false);
			bDirdialogJar.setLayoutData(new GridData(SWT.WRAP));
			bDirdialogJar.addSelectionListener(new SelectionAdapter() {

				@Override
				public final void widgetSelected(SelectionEvent event) {
					FileDialog dialog = new FileDialog(getShell());
					dialog.setFilterPath(textFileAsJar.getText());
					dialog.setText("Select Project as JAR...");
					dialog.setFilterExtensions(new String[] { "jar" });
					String jar = dialog.open();
					if (jar != null) {
						textFileAsJar.setText(jar);
					}
					validateData();
				}

			});

			setControl(composite);
			setPageComplete(false);
		}

		/**
		 * validate data in components
		 */
		private final void validateData() {
			setPageComplete(false);
			if (selectedMultiWorkspace != null && selectedWorkspace != null) {
				config.setSelectedMultiWorkspace(selectedMultiWorkspace);
				config.setSelectedWorkspace(selectedWorkspace);

				if (buttonAsProjectFolder.getSelection()) {
					config.setAsFolder(true);
					String folder = textFileAsFolder.getText().trim();
					if (folder.length() != 0) {
						File f = new File(folder);
						if (f.exists() && f.isDirectory()) {
							config.setPath(f);
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
							setPageComplete(true);
						}
					}
				}

			}
		}

		/**
		 * handle event from {@link #bDirdialogAsFolder} and
		 * {@link #bDirdialogJar}
		 */
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
			validateData();
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

	}
}
