package com.tomecode.soa.dependency.analyzer.gui.displays;

import java.io.File;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * 
 * Wizard for exporting {@link VisualGraphView} to image
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ExportGraphToImageWizard extends Wizard {

	private static final String TITLE = "Export graph to image...";
	private File imageFile;
	private int imageFormat;

	public ExportGraphToImageWizard() {
		super();
		setWindowTitle(TITLE);
		addPage(new FormatTypePage());
		addPage(new FilePathPage());
	}

	@Override
	public final boolean performFinish() {
		return true;
	}

	public final int getFormat() {
		return imageFormat;
	}

	public final File getFile() {
		return imageFile;
	}

	/**
	 * page contains list of radio buttons - export format type
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 */
	final class FormatTypePage extends WizardPage implements Listener {

		private Button bJpeg;
		private Button bPng;
		private Button bBmp;
		private Button bGif;

		protected FormatTypePage() {
			super("", TITLE, ImageFactory.exportBig);
			setTitle("Export image format...");
		}

		@Override
		public final void handleEvent(Event event) {
			if (bJpeg.getSelection()) {
				imageFormat = SWT.IMAGE_JPEG;
				setPageComplete(true);
			} else if (bPng.getSelection()) {
				imageFormat = SWT.IMAGE_PNG;
				setPageComplete(true);
			} else if (bBmp.getSelection()) {
				imageFormat = SWT.IMAGE_BMP;
				setPageComplete(true);
			} else if (bGif.getSelection()) {
				imageFormat = SWT.IMAGE_GIF;
				setPageComplete(true);
			}
		}

		@Override
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE | SWT.RIGHT);
			composite.setLayout(new GridLayout(1, true));

			bJpeg = createRadioButton(composite, "JPEG");
			bPng = createRadioButton(composite, "PNG");
			bBmp = createRadioButton(composite, "BMP");
			bGif = createRadioButton(composite, "GIF");

			setControl(composite);
			setPageComplete(false);
		}

		/**
		 * create radio button
		 * 
		 * @param parent
		 * @param text
		 */
		private final Button createRadioButton(Composite parent, String text) {
			Button button = new Button(parent, SWT.RADIO | SWT.RIGHT);
			button.addListener(SWT.Selection, this);
			button.setText(text);
			return button;
		}
	}

	/**
	 * 
	 * export image file
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
	 * 
	 */
	final class FilePathPage extends WizardPage implements Listener {

		private FilePathPage() {
			super("Export path...", TITLE, ImageFactory.exportBig);
			setTitle("Export file...");
		}

		@Override
		public final void handleEvent(Event event) {

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
						imageFile = new File(txt);
						setPageComplete(true);
					}
				}
			});
			Button bDirdialog = new Button(composite, SWT.PUSH);
			bDirdialog.setText("...");
			bDirdialog.setLayoutData(new GridData(SWT.WRAP));
			bDirdialog.addSelectionListener(new SelectionAdapter() {

				@Override
				public final void widgetSelected(SelectionEvent event) {
					FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
					dialog.setFilterPath(textFile.getText());
					dialog.setText("Save to file...");

					String file = dialog.open();

					if (file == null) {
						imageFile = null;
						setPageComplete(false);
					} else {
						if (dialog != null) {
							textFile.setText(file);
							imageFile = new File(file);
							setPageComplete(true);
						} else {
							setPageComplete(false);
						}
					}

				}

			});
			setControl(composite);
			setPageComplete(false);
		}

	}

}
