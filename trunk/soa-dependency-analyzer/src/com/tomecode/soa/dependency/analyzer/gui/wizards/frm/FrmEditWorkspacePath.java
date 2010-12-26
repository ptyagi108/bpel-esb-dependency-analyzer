package com.tomecode.soa.dependency.analyzer.gui.wizards.frm;

import java.io.File;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Edit workspace path
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class FrmEditWorkspacePath extends Dialog {

	private Shell frmShell;
	private Button buttonOk;
	private String path;
	private Text textPath;
	private final List<String> paths;

	/**
	 * Constructor
	 * 
	 * @param parentShell
	 */
	public FrmEditWorkspacePath(Shell parentShell, List<String> paths) {
		super(parentShell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.paths = paths;
	}

	/**
	 * Open frm
	 * 
	 * @param exitsPath
	 * @return
	 */
	public final String open(String exitsPath) {
		createContent(exitsPath);
		frmShell.layout();
		frmShell.open();
		Display display = getParent().getDisplay();
		while (!frmShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return path;
	}

	private final void createContent(String existsPath) {
		frmShell = new Shell(getParent(), getStyle());
		frmShell.setSize(532, 116);
		frmShell.setLayout(new FillLayout(SWT.VERTICAL));
		frmShell.setText(getText());

		Composite composite = new Composite(frmShell, SWT.NONE);
		composite.setBounds(0, 0, 532, 92);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		Composite compositeText = new Composite(composite, SWT.NONE);

		Composite compositeButtons = new Composite(composite, SWT.NONE);
		compositeText.setLayout(new GridLayout(3, false));

		Label lblPath = new Label(compositeText, SWT.NONE);
		lblPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPath.setText("Path");

		textPath = new Text(compositeText, SWT.BORDER);
		textPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textPath.setText((existsPath != null ? existsPath : ""));
		textPath.addKeyListener(new KeyAdapter() {
			@Override
			public final void keyReleased(KeyEvent e) {
				if (textPath.getText().trim().length() != 0) {
					File f = new File(textPath.getText());
					buttonOk.setEnabled((f.exists() && f.isDirectory()) && !contains(textPath.getText().trim()));
				} else {
					buttonOk.setEnabled(false);
				}

			}
		});

		if (textPath == null) {
			setText("Add a new path for the Workspace");
		} else {
			setText("Edit path for the Workspace");
		}

		Button buttonDialog = new Button(compositeText, SWT.NONE);
		buttonDialog.setText("...");
		buttonDialog.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(frmShell);
				dialog.setFilterPath(textPath.getText());
				dialog.setMessage("Select the Workspace directory...");
				dialog.setText("Select the Workspace directory...");

				String dir = dialog.open();
				if (dir != null) {
					textPath.setText(dir);
					path = dir;
				}

				buttonOk.setEnabled(textPath.getText().trim().length() != 0);
			}
		});
		compositeButtons.setLayout(new RowLayout(SWT.HORIZONTAL));

		buttonOk = new Button(compositeButtons, SWT.NONE);
		buttonOk.setText("OK");
		buttonOk.setEnabled(false);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(SelectionEvent e) {
				path = textPath.getText().trim();
				frmShell.close();
			}
		});

		Button buttonCancel = new Button(compositeButtons, SWT.NONE);
		buttonCancel.setText("Chancel");
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(SelectionEvent e) {
				path = null;
				frmShell.close();
			}
		});
	}

	private final boolean contains(String newPath) {
		for (String path : paths) {
			if (path.equalsIgnoreCase(newPath)) {
				return true;
			}
		}
		return false;
	}

}
