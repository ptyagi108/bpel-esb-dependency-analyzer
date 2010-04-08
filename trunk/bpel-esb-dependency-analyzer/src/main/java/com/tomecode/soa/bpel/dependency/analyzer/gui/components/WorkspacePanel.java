package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.ProcessStructureTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.ProjectOperationTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.WorkspaceTree;
import com.tomecode.soa.bpel.model.BpelOperations;
import com.tomecode.soa.bpel.model.BpelProcess;
import com.tomecode.soa.bpel.model.Operation;
import com.tomecode.soa.bpel.model.Workspace;
import com.tomecode.util.gui.PanelFactory;

/**
 * Panel contains list of components whiche display bpel process result
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspacePanel extends JPanel {

	private static final long serialVersionUID = 1530206367436072362L;

	/**
	 * {@link WorkspaceTree}
	 */
	private final WorkspaceTree workspaceTree;
	/**
	 * {@link ProjectOperationTree}
	 */
	private final ProjectOperationTree projectOperationTree;
	/**
	 * {@link ProcessStructureTree}
	 */
	private final ProcessStructureTree processStructureTree;

	/**
	 * path of selected bpel process
	 */
	private final JTextField txtProcessFolder;

	/**
	 * Constructor
	 */
	public WorkspacePanel(Workspace workspace) {
		super(new BorderLayout());
		this.txtProcessFolder = new JTextField();
		txtProcessFolder.setEditable(false);
		this.workspaceTree = new WorkspaceTree(workspace);
		this.projectOperationTree = new ProjectOperationTree();
		this.processStructureTree = new ProcessStructureTree();
		JSplitPane spWorkspace = PanelFactory.createSplitPanel();
		spWorkspace.add(PanelFactory.createBorderLayout("Workspace Dependecies", new JScrollPane(workspaceTree)));
		spWorkspace.setDividerLocation(200);
		JSplitPane spProjectTrees = PanelFactory.createSplitPanel();
		spProjectTrees.add(PanelFactory.createBorderLayout("Project-Operations Dependecies", new JScrollPane(projectOperationTree)));
		spProjectTrees.add(PanelFactory.createBorderLayout("Project Structure", new JScrollPane(processStructureTree)));
		spProjectTrees.setDividerLocation(350);
		JPanel pProject = PanelFactory.createBorderLayout();
		pProject.add(spProjectTrees, BorderLayout.CENTER);

		JPanel pProjectDetails = PanelFactory.createBorderLayout("Project Details");
		JPanel pFileds = PanelFactory.createGridLayout(2, 1);
		pFileds.add(PanelFactory.wrapWithLabelNorm("Process folder", txtProcessFolder, 10));
		pProjectDetails.add(pFileds, BorderLayout.CENTER);
		pProject.add(pProjectDetails, BorderLayout.SOUTH);

		spWorkspace.add(pProject);

		add(spWorkspace, BorderLayout.CENTER);

		this.workspaceTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {

				if (e.getPath().getLastPathComponent() instanceof BpelProcess) {
					BpelProcess bpelProcess = (BpelProcess) e.getPath().getLastPathComponent();
					if (bpelProcess != null) {
						projectOperationTree.addBpelProcessOperations(bpelProcess.getBpelOperations());
						processStructureTree.addBpelProcessStrukture(bpelProcess.getBpelProcessStrukture());
						txtProcessFolder.setText(bpelProcess.getBpelXmlFile().getParent());
					}
				} else {
					processStructureTree.clear();
					projectOperationTree.clear();
					txtProcessFolder.setText("");
				}

			}
		});

		this.projectOperationTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {
				if (e.getPath().getLastPathComponent() instanceof Operation) {
					Operation operation = (Operation) e.getPath().getLastPathComponent();
					displayBpelProcessStructure(operation.getBpelProcess());
				} else if (e.getPath().getLastPathComponent() instanceof BpelOperations) {
					BpelOperations bpelOperations = (BpelOperations) e.getPath().getLastPathComponent();
					displayBpelProcessStructure(bpelOperations.getBpelProcess());
				}
			}
		});

	}

	/**
	 * display bpel process
	 * 
	 * @param bpelProcess
	 */
	private final void displayBpelProcessStructure(BpelProcess bpelProcess) {
		if (bpelProcess == null) {
			processStructureTree.clear();
			txtProcessFolder.setText("");
		} else {
			processStructureTree.addBpelProcessStrukture(bpelProcess.getBpelProcessStrukture());
			txtProcessFolder.setText(bpelProcess.getBpelXmlFile().getParent());
		}

	}
}
