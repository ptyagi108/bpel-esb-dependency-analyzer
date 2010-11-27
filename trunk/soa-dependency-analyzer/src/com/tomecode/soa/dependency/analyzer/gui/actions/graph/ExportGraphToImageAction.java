package com.tomecode.soa.dependency.analyzer.gui.actions.graph;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.zest.core.widgets.Graph;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.gui.wizards.ExportGraphToImageWizard;
import com.tomecode.soa.dependency.analyzer.icons.ImageExporter;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.VisualGraphView;

/**
 * export graph to image
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class ExportGraphToImageAction extends Action {

	private Graph graph;

	/**
	 * Constructor
	 * 
	 * @param graph
	 */
	public ExportGraphToImageAction(Graph graph) {
		this();
		this.graph = graph;
	}

	/**
	 * 
	 */
	public ExportGraphToImageAction() {
		super();
		setText("Export to image");
		setToolTipText("Export to image");
		setImageDescriptor(ImageFactory.export);
	}

	public final void run() {
		if (graph == null && GuiUtils.getActiveVisualGraphView() == null) {
			MessageDialog.openInformation(null, "Export Graph to Image", "Please select graph, from which you want to export to file");
		} else {
			try {

				ExportGraphToImageWizard wizard = new ExportGraphToImageWizard();
				WizardDialog dialog = new WizardDialog(null, wizard);

				dialog.setBlockOnOpen(true);
				dialog.create();

				if (WizardDialog.OK == dialog.open()) {
					try {
						if (graph != null) {
							ImageExporter.export(graph, wizard.getFile(), wizard.getFormat());
						} else {
							VisualGraphView graph = GuiUtils.getActiveVisualGraphView();
							if (graph != null) {
								ImageExporter.export(graph.getGraph(), wizard.getFile(), wizard.getFormat());
							}
						}
					} catch (Exception e) {
						MessageDialog.openError(null, "Error", "Opps...Error exporting image:" + e.getMessage());
					} finally {
						wizard.dispose();
					}
				}

			} catch (Exception e) {
				MessageDialog.openError(null, "Error", "Opps...Error exporting image:" + e.getMessage());
			}
		}

	}
}
