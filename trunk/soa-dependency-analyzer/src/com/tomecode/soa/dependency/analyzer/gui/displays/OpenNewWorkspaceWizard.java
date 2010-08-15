package com.tomecode.soa.dependency.analyzer.gui.displays;

import java.io.File;
import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard for open new workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenNewWorkspaceWizard extends Wizard {

	public static final String WOKRSPACE_CONFIG = "new.workspace.config";

	private static final String ORACLE_10G = "oracle10g";
	private static final String ORACLE_11G = "oracle11g";
	private static final String OPEN_ESB_BPEL = "openEsbBpel";

	private final WorkspaceConfig config;

	/**
	 * Constructor
	 */
	public OpenNewWorkspaceWizard() {
		setWindowTitle("Open new workspace");

		config = new WorkspaceConfig();
		addPage(new SelectWorkspacePage());
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
	 * 
	 */
	final class SelectWorkspacePage extends WizardPage implements Listener {

		private Button bOracle10g;
		private Button bOracle11g;
		private Button bOpenEsbBpel;

		public SelectWorkspacePage() {
			super("Switch workspace", "Open new workspace", null);

		}

		@Override
		public final void createControl(Composite parent) {

			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			bOracle10g = new Button(composite, SWT.RADIO | SWT.RIGHT);
			bOracle10g.setText("Oracle SOA Suite 10g");
			bOracle10g.addListener(SWT.Selection, this);
			bOracle11g = new Button(composite, SWT.RADIO);
			bOracle11g.setText("Oracle SOA Suite 11g");
			bOracle11g.addListener(SWT.Selection, this);
			bOpenEsbBpel = new Button(composite, SWT.RADIO);
			bOpenEsbBpel.setText("Open ESB - BPEL wokrspace");
			bOpenEsbBpel.addListener(SWT.Selection, this);
			setControl(composite);
			setPageComplete(false);

		}

		public final void handleEvent(Event event) {
			if (bOracle10g.getSelection()) {
				config.setWorkspaceType(ORACLE_10G);
				setPageComplete(true);
			} else if (bOracle11g.getSelection()) {
				config.setWorkspaceType(ORACLE_11G);
				setPageComplete(true);
			} else if (bOpenEsbBpel.getSelection()) {
				config.setWorkspaceType(OPEN_ESB_BPEL);
				setPageComplete(true);
			} else {
				config.setWorkspaceType(null);
				setPageComplete(false);
			}
		}
	}

	/**
	 * add workspace paths
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	final class WorkspacePath extends WizardPage {

		protected WorkspacePath() {
			super("Workspace path", "Workspace path", null);

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
						getDialogSettings().put("workspace.dir", dir);
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}

			});
			setControl(composite);
			setPageComplete(false);
		}
	}

	/**
	 * Summary page
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	final class Summary extends WizardPage {

		protected Summary() {
			super("Summary", "Summary...", null);

		}

		@Override
		public final void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(1, true));
			Label label = new Label(composite, SWT.BOLD);
			label.setText("Danke"); // label.setText(getDialogSettings().get(WOKRSPACE_TYPE));
			setControl(composite);
			setPageComplete(true);
		}

	}

	/**
	 * Simple class that contains the configuration information - for a new
	 * workspace
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public final class WorkspaceConfig {
		private String workspaceType;

		private final List<File> workspacePaths;

		public WorkspaceConfig() {
			workspacePaths = new ArrayList<File>();
		}

		/**
		 * @return the workspaceType
		 */
		public final String getWorkspaceType() {
			return workspaceType;
		}

		/**
		 * @param workspaceType
		 *            the workspaceType to set
		 */
		public final void setWorkspaceType(String workspaceType) {
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
