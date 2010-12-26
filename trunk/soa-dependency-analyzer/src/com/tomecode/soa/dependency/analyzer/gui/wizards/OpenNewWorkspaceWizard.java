package com.tomecode.soa.dependency.analyzer.gui.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.wizards.frm.FrmEditWorkspacePath;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Wizard to open a new workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OpenNewWorkspaceWizard extends Wizard {

	private final static String TITLE = "Open new workspace";

	private final WorkspaceConfig config;

	/**
	 * Constructor
	 */
	public OpenNewWorkspaceWizard() {
		super();
		setWindowTitle(TITLE);

		config = new WorkspaceConfig();
		addPage(new SelectWorkspaceTypePage());
		addPage(new SelectMultiWorkspacePage());
		addPage(new WorkspacePathPage());
		addPage(new Summary());
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	public final WorkspaceConfig getConfig() {
		return config;
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Page for switch workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SelectWorkspaceTypePage extends WizardPage implements Listener {

		private Button bOracle10g;
		// private Button bOracle11g;
		private Button bOracleSB10g;
		private Button bOpenEsbBpel;

		public SelectWorkspaceTypePage() {
			super("Switch workspace", TITLE, ImageFactory.openBig);
			setTitle("Select the Workspace type...");
		}

		@Override
		public final void createControl(Composite parent) {

			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			bOracle10g = new Button(composite, SWT.RADIO | SWT.RIGHT);
			bOracle10g.setText(WorkspaceType.ORACLE_1OG.toString() + " (only from Workspace)");
			bOracle10g.setFocus();
			bOracle10g.addListener(SWT.Selection, this);
			// bOracle11g = new Button(composite, SWT.RADIO);
			// bOracle11g.setText(WorkspaceType.ORACLE_11G.toString());
			// bOracle11g.addListener(SWT.Selection, this);
			bOracleSB10g = new Button(composite, SWT.RADIO);
			bOracleSB10g.setText(WorkspaceType.ORACLE_SERVICE_BUS_10G.toString());
			bOracleSB10g.addListener(SWT.Selection, this);
			bOpenEsbBpel = new Button(composite, SWT.RADIO);
			bOpenEsbBpel.setText(WorkspaceType.OPEN_ESB.toString() + " (Demo)");
			bOpenEsbBpel.addListener(SWT.Selection, this);
			setControl(composite);
			setPageComplete(false);

		}

		public final void handleEvent(Event event) {
			if (bOracle10g.getSelection()) {
				config.setWorkspaceType(WorkspaceType.ORACLE_1OG);
				setPageComplete(true);
				// } else if (bOracle11g.getSelection()) {
				// config.setWorkspaceType(WorkspaceType.ORACLE_11G);
				// setPageComplete(true);
			} else if (bOpenEsbBpel.getSelection()) {
				config.setWorkspaceType(WorkspaceType.OPEN_ESB);
				setPageComplete(true);
			} else if (bOracleSB10g.getSelection()) {
				config.setWorkspaceType(WorkspaceType.ORACLE_SERVICE_BUS_10G);
				setPageComplete(true);
			} else {
				config.setWorkspaceType(null);
				setPageComplete(false);
			}
		}

		public final IWizardPage getNextPage() {
			SelectMultiWorkspacePage multiWorkspacePage = (SelectMultiWorkspacePage) super.getNextPage();
			multiWorkspacePage.loadNamesForSelectedWorkspace();
			return multiWorkspacePage;
		}

	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * select add workspace to exist or create new multiple workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SelectMultiWorkspacePage extends WizardPage implements Listener {

		/**
		 * {@link MultiWorkspace} name
		 */
		private Text txtMWorkspaceName;

		/**
		 * {@link MultiWorkspace} path
		 */
		private Text txtMWorkspacePath;

		private Button bSelectExistsMultipleWorkspace;

		private Button bNewMultipleWorkspace;

		private Text labeMWorkspacePathExists;

		private Combo comboMWorkspaces;

		private String[] mwNames;

		/**
		 * Constructor
		 */
		public SelectMultiWorkspacePage() {
			super("Select Multi-Workspace", TITLE, ImageFactory.openBig);
			setTitle("Select Mutli-Workspace...");
			mwNames = new String[] {};
		}

		/**
		 * add all name of workspaces to {@link #mwNames} by workspace type
		 */
		public final void loadNamesForSelectedWorkspace() {

			List<MultiWorkspace> multiWorkspaces = ApplicationManager.getInstance().getMultiWorkspaces(config.getWorkspaceType());
			mwNames = new String[multiWorkspaces.size()];
			for (int i = 0; i <= multiWorkspaces.size() - 1; i++) {
				mwNames[i] = multiWorkspaces.get(i).getName();
			}
			comboMWorkspaces.setItems(mwNames);

			setPageComplete(false);
		}

		@Override
		public final void createControl(Composite parent) {
			// Composite composite = new Composite(parent, SWT.NONE);
			// GridLayout gridLayout = new GridLayout(1, false);
			// composite.setLayout(gridLayout);

			Composite composite = GuiUtils.createCompositeWithGrid(parent, 1);
			bNewMultipleWorkspace = new Button(composite, SWT.RADIO);
			bNewMultipleWorkspace.setText("Name of new Multi-Worksapce");
			bNewMultipleWorkspace.setSelection(true);
			bNewMultipleWorkspace.addListener(SWT.Selection, this);

			// txtMWorkspaceName = new Text(composite, SWT.SINGLE | SWT.BORDER);

			// GridData txtMworkspaceNameGridData = new GridData();
			// txtMworkspaceNameGridData.horizontalAlignment = SWT.FILL;
			// txtMworkspaceNameGridData.horizontalSpan = 20;
			// txtMworkspaceNameGridData.grabExcessHorizontalSpace = true;

			// txtMWorkspaceName.setLayoutData(txtMworkspaceNameGridData);
			Composite cData = GuiUtils.createCompositeWithGrid(composite, 2);
			GuiUtils.createLabelWithGrid(cData, "Name: ");
			txtMWorkspaceName = GuiUtils.createTextWithGrid(cData, "");
			txtMWorkspaceName.addModifyListener(new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateText();
				}
			});

			GuiUtils.createLabelWithGrid(cData, "Path: ");
			txtMWorkspacePath = GuiUtils.createTextWithGrid(cData, "");
			txtMWorkspacePath.addModifyListener(new ModifyListener() {
				@Override
				public final void modifyText(ModifyEvent event) {
					validateText();
				}
			});

			// txtMWorkspacePath = new Text(composite, SWT.SINGLE | SWT.BORDER);
			// txtMWorkspacePath.setLayoutData(txtMworkspaceNameGridData);

			bSelectExistsMultipleWorkspace = new Button(composite, SWT.RADIO);
			bSelectExistsMultipleWorkspace.setText("Select from an existing Multi-Workspace");
			bSelectExistsMultipleWorkspace.addListener(SWT.Selection, this);

			comboMWorkspaces = new Combo(composite, SWT.READ_ONLY);
			comboMWorkspaces.setItems(mwNames);
			comboMWorkspaces.setEnabled(false);

			GridData comboMWorkspacesGridData = new GridData();
			comboMWorkspacesGridData.horizontalAlignment = SWT.FILL;
			comboMWorkspacesGridData.horizontalSpan = 20;
			comboMWorkspacesGridData.grabExcessHorizontalSpace = true;
			comboMWorkspaces.setLayoutData(comboMWorkspacesGridData);
			comboMWorkspaces.addSelectionListener(new SelectionAdapter() {
				public final void widgetSelected(SelectionEvent e) {
					validateCombo();
				}
			});

			Composite cDataExits = GuiUtils.createCompositeWithGrid(composite, 2);
			GuiUtils.createLabelWithGrid(cDataExits, "Path: ");
			labeMWorkspacePathExists = GuiUtils.createTextReadOnlyWithGrid(cDataExits);

			setControl(composite);
			setPageComplete(false);
		}

		private final void validateCombo() {
			if (comboMWorkspaces.getSelectionIndex() == -1) {
				setPageComplete(false);
			} else {
				setPageComplete(true);

				MultiWorkspace mw = ApplicationManager.getInstance().getMultiWorkspaces(config.getWorkspaceType()).get(comboMWorkspaces.getSelectionIndex());
				config.setExitsMultiWorkspace(mw);
				config.getWorkspacePaths().clear();
				for (Workspace workspace : mw.getWorkspaces()) {
					config.addWorkspacePath(workspace.getFile().getParentFile().getPath());
				}

				labeMWorkspacePathExists.setText(mw.getPath().getPath());
				config.setIsNewMultiWorkspace(false);
			}
		}

		private final void validateText() {
			String name = txtMWorkspaceName.getText().trim();
			String path = txtMWorkspacePath.getText().trim();
			if (name.length() != 0 && path.length() != 0 && isPathOk(path)) {
				setPageComplete(!contains(mwNames, name));
				config.setMultiWorkspaceName(name);
				config.setMultiWorkspacePath(path);
				config.setIsNewMultiWorkspace(true);
			} else {
				setPageComplete(false);
			}
		}

		/**
		 * validate path from {@link #txtMWorkspacePath}
		 * 
		 * @param path
		 * @return
		 */
		private final boolean isPathOk(String path) {
			File p = new File(path);
			return (p.exists() && p.isDirectory());
		}

		private final boolean contains(String[] mwNames, String str) {
			for (String name : mwNames) {
				if (name.equalsIgnoreCase(str)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public final void handleEvent(Event event) {
			if (bNewMultipleWorkspace.getSelection()) {
				config.setIsNewMultiWorkspace(true);
				txtMWorkspaceName.setEditable(true);
				txtMWorkspaceName.setEnabled(true);
				txtMWorkspacePath.setEditable(true);
				txtMWorkspacePath.setEnabled(true);
				comboMWorkspaces.setEnabled(false);
				validateText();
			} else {
				config.setIsNewMultiWorkspace(false);
				txtMWorkspaceName.setEditable(false);
				txtMWorkspaceName.setEnabled(false);
				txtMWorkspacePath.setEditable(false);
				txtMWorkspacePath.setEnabled(false);
				comboMWorkspaces.setEnabled(true);
				validateCombo();
			}

		}

		public final IWizardPage getNextPage() {
			WorkspacePathPage workspacePath = (WorkspacePathPage) super.getNextPage();
			workspacePath.fillData();
			return workspacePath;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * 
	 * add workspace paths
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class WorkspacePathPage extends WizardPage {

		private org.eclipse.swt.widgets.List listWorkspaces;

		/**
		 * Constructor
		 */
		protected WorkspacePathPage() {
			super("Workspace path", TITLE, ImageFactory.openBig);
			setTitle("Workspce path");
		}

		public final void fillData() {
			listWorkspaces.removeAll();
			for (String path : config.getWorkspacePaths()) {
				listWorkspaces.add(path);
			}

			setPageComplete(!config.getWorkspacePaths().isEmpty());
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			FillLayout fillLayout = new FillLayout();
			fillLayout.type = SWT.VERTICAL;
			composite.setLayout(fillLayout);
			Group group = new Group(composite, SWT.NULL);
			group.setLayout(fillLayout);
			group.setText("Workspaces");

			Composite cButt = new Composite(composite, SWT.PUSH);
			RowLayout rowLayout = new RowLayout();
			rowLayout.pack = false;
			rowLayout.justify = false;
			rowLayout.type = SWT.HORIZONTAL;
			rowLayout.marginLeft = 5;
			rowLayout.marginTop = 5;
			rowLayout.marginRight = 5;
			rowLayout.marginBottom = 5;
			rowLayout.spacing = 0;
			cButt.setLayout(rowLayout);
			final Button buttonAdd = new Button(cButt, SWT.PUSH);
			buttonAdd.setText("Add");
			buttonAdd.addSelectionListener(new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent e) {
					FrmEditWorkspacePath frm = new FrmEditWorkspacePath(getShell(), config.getWorkspacePaths());
					String path = frm.open(null);
					if (path != null) {
						listWorkspaces.add(path);
						config.addWorkspacePath(path);
					}

					validateListData();
				}
			});
			final Button buttonEdit = new Button(cButt, SWT.PUSH);
			buttonEdit.setText("Edit");
			buttonEdit.setEnabled(false);
			buttonEdit.addSelectionListener(new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent e) {
					int index = listWorkspaces.getSelectionIndex();
					if (index != -1) {
						FrmEditWorkspacePath frm = new FrmEditWorkspacePath(getShell(), config.getWorkspacePaths());
						String path = frm.open(listWorkspaces.getItem(index));
						if (path != null) {
							listWorkspaces.setItem(index, path);
							config.getWorkspacePaths().set(index, path);
						}

					}
					buttonEdit.setEnabled(false);

					validateListData();
				}
			});
			final Button buttonRemove = new Button(cButt, SWT.PUSH);
			buttonRemove.setText("Remove");
			buttonRemove.addSelectionListener(new SelectionAdapter() {
				@Override
				public final void widgetSelected(SelectionEvent e) {
					int index = listWorkspaces.getSelectionIndex();
					if (index != -1) {
						config.getWorkspacePaths().remove(index);
						listWorkspaces.remove(index);
					}
					buttonRemove.setEnabled(false);

					validateListData();
				}
			});
			buttonRemove.setEnabled(false);

			listWorkspaces = new org.eclipse.swt.widgets.List(group, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
			listWorkspaces.addListener(SWT.Selection, new Listener() {

				@Override
				public final void handleEvent(Event event) {
					if (listWorkspaces.getSelectionIndex() != -1) {
						buttonEdit.setEnabled(true);
						buttonRemove.setEnabled(true);
					} else {
						buttonEdit.setEnabled(false);
						buttonRemove.setEnabled(false);
					}
				}
			});
			//
			// final Text textFile = new Text(composite, SWT.BORDER);
			// GridData gridData = new GridData();
			// gridData.horizontalAlignment = SWT.FILL;
			// gridData.grabExcessHorizontalSpace = true;
			// textFile.setLayoutData(gridData);
			//
			// textFile.addModifyListener(new ModifyListener() {
			//
			// @Override
			// public final void modifyText(ModifyEvent event) {
			// String txt = textFile.getText().trim();
			// if (txt.isEmpty()) {
			// setPageComplete(false);
			// } else {
			// File f = new File(txt);
			// if (f.exists() && f.isDirectory()) {
			// config.setWorkspaceDir(f);
			// setPageComplete(true);
			// } else {
			// setPageComplete(false);
			// }
			// }
			// }
			// });
			// Button bDirdialog = new Button(composite, SWT.PUSH);
			// bDirdialog.setText("...");
			// bDirdialog.setLayoutData(new GridData(SWT.WRAP));
			// bDirdialog.addSelectionListener(new SelectionAdapter() {
			//
			// @Override
			// public final void widgetSelected(SelectionEvent event) {
			// DirectoryDialog dialog = new DirectoryDialog(getShell());
			// dialog.setFilterPath(textFile.getText());
			// dialog.setMessage("Select the Workspace directory...");
			// dialog.setText("Select the Workspace directory...");
			//
			// String dir = dialog.open();
			// if (dir != null) {
			// textFile.setText(dir);
			// config.setWorkspaceDir(new File(dir));
			// setPageComplete(true);
			// } else {
			// setPageComplete(false);
			// }
			// }
			//
			// });
			setControl(composite);
			setPageComplete(false);
		}

		private final void validateListData() {
			setPageComplete(!config.getWorkspacePaths().isEmpty());
		}

		public final IWizardPage getNextPage() {
			Summary summary = (Summary) super.getNextPage();
			summary.fillData();
			return summary;
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Summary page
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class Summary extends WizardPage {

		private Label labelWorkspaceTypeName;

		private Text txtMWorkspaceName;
		private Text txtMWorkspacePath;
		private org.eclipse.swt.widgets.List listWorkspaceDirs;

		protected Summary() {
			super("Summary", TITLE, ImageFactory.openBig);
			setTitle("Summary");
		}

		public final void fillData() {
			labelWorkspaceTypeName.setText(config.getWorkspaceType().toString());

			if (config.isNewMultiWorkspace()) {
				txtMWorkspaceName.setText(config.getMultiWorkspaceName());
				txtMWorkspacePath.setText(config.getMultiWorkspacePath());
			} else {
				txtMWorkspaceName.setText(config.getExitsMultiWorkspace().getName());
				txtMWorkspacePath.setText(config.getExitsMultiWorkspace().getPath().getPath());
			}

			listWorkspaceDirs.removeAll();
			for (String path : config.getWorkspacePaths()) {
				listWorkspaceDirs.add(path);
			}

		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);

			composite.setLayout(new GridLayout(2, false));
			composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

			Label labelWoskapceType = new Label(composite, SWT.NONE);
			labelWoskapceType.setLayoutData(new GridData());
			labelWoskapceType.setText("Workspace Type:");

			labelWorkspaceTypeName = new Label(composite, SWT.BORDER | SWT.READ_ONLY);
			labelWorkspaceTypeName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			labelWorkspaceTypeName.setText("");

			Label labelMWoskapce = new Label(composite, SWT.NONE);
			labelMWoskapce.setLayoutData(new GridData());
			labelMWoskapce.setText("Multi-Workspace Name:");

			txtMWorkspaceName = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			txtMWorkspaceName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			txtMWorkspaceName.setText("");
			Label labelMWorkspacePath = new Label(composite, SWT.NONE);
			labelMWorkspacePath.setLayoutData(new GridData());
			labelMWorkspacePath.setText("Multi-Workspace Path:");

			txtMWorkspacePath = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			txtMWorkspacePath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			txtMWorkspacePath.setText("");

			Label labelWorkspaceDir = new Label(composite, SWT.NONE);
			labelWorkspaceDir.setLayoutData(new GridData());
			labelWorkspaceDir.setText("Workpsace Folders:");

			listWorkspaceDirs = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
			listWorkspaceDirs.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			setControl(composite);
			setPageComplete(true);
		}
	}

	/**
	 * (c) Copyright Tomecode.com, 2010. All rights reserved.
	 * 
	 * Simple class that contains the configuration information - for a new
	 * workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final class WorkspaceConfig {
		private WorkspaceType workspaceType;

		private final List<String> workspacePaths;

		private String multiWorkspaceName;
		private String multiWorkspacePath = "";

		private MultiWorkspace exitsMultiWorkspace;
		private boolean isNewMultWorkspace;

		/**
		 * Configuration
		 */
		public WorkspaceConfig() {
			workspacePaths = new ArrayList<String>();
			isNewMultWorkspace = true;

		}

		/**
		 * @return the exitsMultiWorkspace
		 */
		public final MultiWorkspace getExitsMultiWorkspace() {
			return exitsMultiWorkspace;
		}

		/**
		 * @param exitsMultiWorkspace
		 *            the exitsMultiWorkspace to set
		 */
		public final void setExitsMultiWorkspace(MultiWorkspace exitsMultiWorkspace) {
			this.exitsMultiWorkspace = exitsMultiWorkspace;
		}

		public final String getMultiWorkspacePath() {
			return multiWorkspacePath;
		}

		public final void setMultiWorkspacePath(String multiWorkspacePath) {
			this.multiWorkspacePath = multiWorkspacePath;
		}

		public final void setIsNewMultiWorkspace(boolean state) {
			this.isNewMultWorkspace = state;
		}

		public final boolean isNewMultiWorkspace() {
			return isNewMultWorkspace;
		}

		public final String getMultiWorkspaceName() {
			return multiWorkspaceName;
		}

		public final void setMultiWorkspaceName(String multiWorkspaceName) {
			this.multiWorkspaceName = multiWorkspaceName;
		}

		/**
		 * @return the workspaceType
		 */
		public final WorkspaceType getWorkspaceType() {
			return workspaceType;
		}

		/**
		 * @param workspaceType
		 *            the workspaceType to set
		 */
		public final void setWorkspaceType(WorkspaceType workspaceType) {
			this.workspaceType = workspaceType;
		}

		/**
		 * @return the workspacePaths
		 */
		public final List<String> getWorkspacePaths() {
			return workspacePaths;
		}

		public final void addWorkspacePath(String path) {
			workspacePaths.add(path);
		}

	}
}
