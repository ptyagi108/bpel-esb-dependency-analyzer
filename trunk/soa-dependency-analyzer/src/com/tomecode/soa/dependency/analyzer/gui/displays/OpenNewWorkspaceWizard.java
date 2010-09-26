package com.tomecode.soa.dependency.analyzer.gui.displays;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tomecode.soa.dependency.analyzer.core.ApplicationManager;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * Wizard for open new workspace
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
		addPage(new SelectWorkspacePage());
		addPage(new SelectMultiWorkspacePage());
		addPage(new WorkspacePath());
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
	 * Page for switch workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SelectWorkspacePage extends WizardPage implements Listener {

		private Button bOracle10g;
		private Button bOracle11g;
		private Button bOracleSB10g;
		private Button bOpenEsbBpel;

		public SelectWorkspacePage() {
			super("Switch workspace", TITLE, ImageFactory.openBig);
			setTitle("Select workspace tipe...");
		}

		@Override
		public final void createControl(Composite parent) {

			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			bOracle10g = new Button(composite, SWT.RADIO | SWT.RIGHT);
			bOracle10g.setText(WorkspaceType.ORACLE_1OG.toString());
			bOracle10g.setFocus();
			bOracle10g.addListener(SWT.Selection, this);
			bOracle11g = new Button(composite, SWT.RADIO);
			bOracle11g.setText(WorkspaceType.ORACLE_11G.toString());
			bOracle11g.addListener(SWT.Selection, this);
			bOracleSB10g = new Button(composite, SWT.RADIO);
			bOracleSB10g.setText(WorkspaceType.ORACLE_SERVICE_BUS_10G.toString());
			bOracleSB10g.addListener(SWT.Selection, this);
			bOpenEsbBpel = new Button(composite, SWT.RADIO);
			bOpenEsbBpel.setText(WorkspaceType.OPEN_ESB.toString());
			bOpenEsbBpel.addListener(SWT.Selection, this);
			setControl(composite);
			setPageComplete(false);

		}

		public final void handleEvent(Event event) {
			if (bOracle10g.getSelection()) {
				config.setWorkspaceType(WorkspaceType.ORACLE_1OG);
				setPageComplete(true);
			} else if (bOracle11g.getSelection()) {
				config.setWorkspaceType(WorkspaceType.ORACLE_11G);
				setPageComplete(true);
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

		public IWizardPage getNextPage() {
			SelectMultiWorkspacePage multiWorkspacePage = (SelectMultiWorkspacePage) super.getNextPage();
			multiWorkspacePage.loadNamesForSelectedWorkspace();
			return multiWorkspacePage;
		}

	}

	/**
	 * select add workspace to exist or create new multiple workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class SelectMultiWorkspacePage extends WizardPage implements Listener {

		private Text txtMWorkspaceName;

		private Button bSelectExistsMultipleWorkspace;

		private Button bNewMultipleWorkspace;

		private Combo comboMWorkspaces;

		private String[] mwNames;

		public SelectMultiWorkspacePage() {
			super("Select Multiple Workspace", TITLE, ImageFactory.openBig);
			setTitle("Selected Mutli Workspace...");
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
			final Composite composite = new Composite(parent, SWT.NONE);
			GridLayout gridLayout = new GridLayout(1, false);
			composite.setLayout(gridLayout);

			bNewMultipleWorkspace = new Button(composite, SWT.RADIO);
			bNewMultipleWorkspace.setText("new multi-worksapce");
			bNewMultipleWorkspace.setSelection(true);
			bNewMultipleWorkspace.addListener(SWT.Selection, this);

			txtMWorkspaceName = new Text(composite, SWT.SINGLE | SWT.BORDER);

			GridData txtMworkspaceNameGridData = new GridData();
			txtMworkspaceNameGridData.horizontalAlignment = SWT.FILL;
			txtMworkspaceNameGridData.horizontalSpan = 20;
			txtMworkspaceNameGridData.grabExcessHorizontalSpace = true;

			txtMWorkspaceName.setLayoutData(txtMworkspaceNameGridData);
			txtMWorkspaceName.addModifyListener(new ModifyListener() {

				@Override
				public final void modifyText(ModifyEvent event) {
					validateText();
				}

			});

			bSelectExistsMultipleWorkspace = new Button(composite, SWT.RADIO);
			bSelectExistsMultipleWorkspace.setText("select from exists multi-worksapce");
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
			setControl(composite);
			setPageComplete(false);
		}

		private final void validateCombo() {
			if (comboMWorkspaces.getSelectionIndex() == -1) {
				setPageComplete(false);
			} else {
				setPageComplete(true);

				config.setMultiWorkspace(comboMWorkspaces.getItem(comboMWorkspaces.getSelectionIndex()));
				config.setIsNewMultiWorkspace(false);
			}
		}

		private final void validateText() {
			String str = txtMWorkspaceName.getText().trim();
			if (str.length() != 0) {
				setPageComplete(!contains(mwNames, str));
				config.setMultiWorkspace(str);
				config.setIsNewMultiWorkspace(true);
			} else {
				setPageComplete(false);
			}
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
				txtMWorkspaceName.setEditable(true);
				txtMWorkspaceName.setEnabled(true);
				comboMWorkspaces.setEnabled(false);
				validateText();
			} else {
				txtMWorkspaceName.setEditable(false);
				txtMWorkspaceName.setEnabled(false);
				// scrolledComposite.setEnabled(true);
				comboMWorkspaces.setEnabled(true);
				validateCombo();
			}

		}
	}

	/**
	 * add workspace paths
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class WorkspacePath extends WizardPage {

		protected WorkspacePath() {
			super("Workspace path", TITLE, ImageFactory.openBig);
			setTitle("Workspce path");
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(2, false));

			final Text textFile = new Text(composite, SWT.BORDER);
			GridData gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			textFile.setLayoutData(gridData);

			textFile.addModifyListener(new ModifyListener() {

				@Override
				public final void modifyText(ModifyEvent event) {
					String txt = textFile.getText().trim();
					if (txt.isEmpty()) {
						setPageComplete(false);
					} else {
						File f = new File(txt);
						if (f.exists() && f.isDirectory()) {
							config.setWorkspaceDir(f);
							setPageComplete(true);
						} else {
							setPageComplete(false);
						}
					}
				}
			});
			Button bDirdialog = new Button(composite, SWT.PUSH);
			bDirdialog.setText("...");
			bDirdialog.setLayoutData(new GridData(SWT.WRAP));
			bDirdialog.addSelectionListener(new SelectionAdapter() {

				@Override
				public final void widgetSelected(SelectionEvent event) {
					DirectoryDialog dialog = new DirectoryDialog(getShell());
					dialog.setFilterPath(textFile.getText());
					dialog.setMessage("Select workspace directory...");
					dialog.setText("Select workspace directory...");

					String dir = dialog.open();
					if (dialog != null) {
						textFile.setText(dir);
						config.setWorkspaceDir(new File(dir));
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}

			});
			setControl(composite);
			setPageComplete(false);
		}

		public final IWizardPage getNextPage() {
			Summary summary = (Summary) super.getNextPage();
			summary.fillData();
			return summary;
		}
	}

	/**
	 * Summary page
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class Summary extends WizardPage {

		private Label labelWoskapceTypeName;

		private Text labelMWoskapceName;

		private Text labelWorkspaceDirName;

		protected Summary() {
			super("Summary", TITLE, ImageFactory.openBig);
			setTitle("Summary");
		}

		public final void fillData() {
			labelWoskapceTypeName.setText(config.getWorkspaceType().toString());

			labelMWoskapceName.setText(config.getMultiWorkspaceName());

			if (config.getWorkspaceDir() != null) {
				labelWorkspaceDirName.setText(config.getWorkspaceDir().toString());
			}
			// super.getControl().redraw();
		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);

			composite.setLayout(new GridLayout(2, false));
			composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

			Label labelWoskapceType = new Label(composite, SWT.NONE);
			labelWoskapceType.setLayoutData(new GridData());
			labelWoskapceType.setText("Workspace Type:");

			labelWoskapceTypeName = new Label(composite, SWT.BORDER | SWT.READ_ONLY);
			labelWoskapceTypeName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			labelWoskapceTypeName.setText("");

			Label labelMWoskapce = new Label(composite, SWT.NONE);
			labelMWoskapce.setLayoutData(new GridData());
			labelMWoskapce.setText("Multi Workspace Name:");

			labelMWoskapceName = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			labelMWoskapceName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			labelMWoskapceName.setText("");

			Label labelWorkspaceDir = new Label(composite, SWT.NONE);
			labelWorkspaceDir.setLayoutData(new GridData());
			labelWorkspaceDir.setText("Workpsace Folder:");

			labelWorkspaceDirName = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			labelWorkspaceDirName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			labelWorkspaceDirName.setText("");

			setControl(composite);
			setPageComplete(true);
		}
	}

	/**
	 * Simple class that contains the configuration information - for a new
	 * workspace
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	public final class WorkspaceConfig {
		private WorkspaceType workspaceType;

		private final List<File> workspacePaths;

		private String multiWorkspaceName;

		private boolean isNewMultWorkspace;

		private File workspaceDir;

		public WorkspaceConfig() {
			workspacePaths = new ArrayList<File>();
			isNewMultWorkspace = true;

		}

		public final File getWorkspaceDir() {
			return workspaceDir;
		}

		public final void setWorkspaceDir(File workspaceDir) {
			this.workspaceDir = workspaceDir;
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

		public final void setMultiWorkspace(String path) {
			this.multiWorkspaceName = path;
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
		public final List<File> getWorkspacePaths() {
			return workspacePaths;
		}

		public final void addWorkspacePath(File path) {
			workspacePaths.add(path);
		}

	}
}
