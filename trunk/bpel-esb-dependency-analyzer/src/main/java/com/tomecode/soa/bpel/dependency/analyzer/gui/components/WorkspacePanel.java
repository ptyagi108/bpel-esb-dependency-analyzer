package com.tomecode.soa.bpel.dependency.analyzer.gui.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.tomecode.soa.bpel.dependency.analyzer.gui.panels.WorkspaceUtilsPanel;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.ProcessStructureTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.ProjectEsbServiceTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.ProjectOperationTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.WorkspaceTree;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.ErrorNode;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.bpel.activity.Activity;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.util.gui.PanelFactory;

/**
 * The panel contains list of components wich display dependency for bpel
 * processes
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspacePanel extends JPanel {

	private static final long serialVersionUID = 1530206367436072362L;
	private static final String P_WORKSPACE_EMPTY = "p.workspace.empty";

	private static final String P_WORKSPACE_DETAIL = "p.workspace.detail";

	private static final String P_BPEL_TREE = "p.bpel.tree";

	private static final String P_ESB_TREE = "p.esb.tree";

	private static final String P_ERROR = "p.error";
	/**
	 * {@link WorkspaceTree}
	 */
	private final WorkspaceTree workspaceTree;
	/**
	 * {@link ProjectOperationTree}
	 */
	private final ProjectOperationTree projectOperationTree;
	/**
	 * {@link ProjectEsbServiceTree}
	 */
	private final ProjectEsbServiceTree projectEsbServiceTree;
	/**
	 * {@link ProcessStructureTree}
	 */
	private final ProcessStructureTree processStructureTree;

	private final JTextField txtError;

	private final JTextField txtErrorWsdl;

	private final JTextField txtProjectPath;

	/**
	 * Simple panel for display utilities
	 */
	private WorkspaceUtilsPanel workspaceUtilsPanel;

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspacePanel(MultiWorkspace multiWorkspace) {
		super(new BorderLayout());
		this.workspaceUtilsPanel = new WorkspaceUtilsPanel();
		this.workspaceTree = new WorkspaceTree(multiWorkspace, workspaceUtilsPanel);
		this.projectOperationTree = new ProjectOperationTree();
		this.projectEsbServiceTree = new ProjectEsbServiceTree();
		this.processStructureTree = new ProcessStructureTree(workspaceUtilsPanel);
		final JSplitPane spWorkspace = PanelFactory.createSplitPanel();
		spWorkspace.add(PanelFactory.createBorderLayout("Project Dependencies", new JScrollPane(workspaceTree)));
		spWorkspace.setDividerLocation(200);

		JSplitPane spProjectTrees = PanelFactory.createSplitPanel();
		spProjectTrees.add(PanelFactory.createBorderLayout("Project dependencies by Operations", new JScrollPane(projectOperationTree)));
		spProjectTrees.add(PanelFactory.createBorderLayout("Project Structure", new JScrollPane(processStructureTree)));
		spProjectTrees.setDividerLocation(350);

		//
		final JSplitPane spPanels = PanelFactory.createSplitPanel();
		spPanels.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spPanels.setDividerLocation(350);

		JPanel pBpelProject = PanelFactory.createBorderLayout();
		pBpelProject.add(spProjectTrees, BorderLayout.CENTER);
		JPanel pProjectDetail = PanelFactory.createBorderLayout();
		pProjectDetail.add(workspaceUtilsPanel, BorderLayout.CENTER);

		final JPanel pCardPanel = new JPanel(new CardLayout());
		spPanels.add(pCardPanel);

		// frist panel is empty panel
		pCardPanel.add(PanelFactory.createBorderLayout(), P_WORKSPACE_EMPTY);

		pCardPanel.add(pBpelProject, P_BPEL_TREE);

		JPanel pError = PanelFactory.createBorderLayout("Parse Error");
		txtError = new JTextField();
		txtError.setEditable(false);

		txtErrorWsdl = new JTextField();
		txtErrorWsdl.setEditable(false);

		JPanel pErrorRow = PanelFactory.createGridLayout(2, 1);
		pErrorRow.add(PanelFactory.wrapWithLabelNorm("Error:", txtError, 16), BorderLayout.NORTH);
		pErrorRow.add(PanelFactory.wrapWithLabelNorm("WSDL:", txtErrorWsdl, 10), BorderLayout.NORTH);
		pError.add(pErrorRow, BorderLayout.NORTH);
		pCardPanel.add(pError, P_ERROR);

		JPanel pEsbProject = PanelFactory.createBorderLayout();
		pEsbProject.add(PanelFactory.wrapWithTile("Esb Services:", new JScrollPane(projectEsbServiceTree)), BorderLayout.CENTER);

		final JSplitPane spEsbPanel = PanelFactory.createSplitPanel();
		spEsbPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spEsbPanel.setDividerLocation(350);

		pCardPanel.add(pEsbProject, P_ESB_TREE);

		JPanel pWorkspace = PanelFactory.createBorderLayout();

		txtProjectPath = new JTextField();
		txtProjectPath.setEditable(false);
		pWorkspace.add(PanelFactory.wrapWithTile("Project path", txtProjectPath), BorderLayout.NORTH);
		pCardPanel.add(pWorkspace, P_WORKSPACE_DETAIL);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				spPanels.setDividerLocation((e.getComponent().getSize().height - 140));
				spPanels.updateUI();
			}
		});

		spPanels.add(pProjectDetail);

		// spWorkspace.add(pCardPanel);
		spWorkspace.add(spPanels);
		add(spWorkspace, BorderLayout.CENTER);

		// select bpel proces
		this.workspaceTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {
				CardLayout cardLayout = (CardLayout) pCardPanel.getLayout();
				processStructureTree.clearSelectedOperationsTreePath();
				if (e.getPath().getLastPathComponent() instanceof Workspace) {
					Workspace workspace = (Workspace) e.getPath().getLastPathComponent();
					txtProjectPath.setText(workspace.getFile().toString());
					cardLayout.show(pCardPanel, P_WORKSPACE_DETAIL);
				} else if (e.getPath().getLastPathComponent() instanceof BpelProject) {
					BpelProject bpelProject = (BpelProject) e.getPath().getLastPathComponent();
					if (bpelProject != null) {
						projectOperationTree.addBpelProcessOperations(bpelProject.getBpelOperations());
						processStructureTree.addBpelProcessStrukture(bpelProject.getBpelProcessStrukture());
						// txtProcessFolder.setText(bpelProcess.getBpelXmlFile().getParent());
					}
					cardLayout.show(pCardPanel, P_BPEL_TREE);
				} else if (e.getPath().getLastPathComponent() instanceof ErrorNode) {
					processStructureTree.clear();
					projectOperationTree.clear();

					ErrorNode errorNode = (ErrorNode) e.getPath().getLastPathComponent();
					txtError.setText(errorNode.getErrorText());
					txtErrorWsdl.setText(errorNode.getWsdl());
					cardLayout.show(pCardPanel, P_ERROR);
				} else if (e.getPath().getLastPathComponent() instanceof EsbProject) {
					EsbProject esbProject = (EsbProject) e.getPath().getLastPathComponent();

					projectEsbServiceTree.addEsbProject(esbProject);
					cardLayout.show(pCardPanel, P_ESB_TREE);
				} else {
					processStructureTree.clear();
					projectOperationTree.clear();
					cardLayout.show(pCardPanel, P_BPEL_TREE);
				}

			}

		});

		// select operation in selected bpel proces in workspaceTree
		this.projectOperationTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {
				if (e.getPath().getLastPathComponent() instanceof Operation) {
					Operation operation = (Operation) e.getPath().getLastPathComponent();
					displayBpelProcessStructure(operation.getActivities(), operation.getOwnerBpelProject());
				} else if (e.getPath().getLastPathComponent() instanceof BpelOperations) {
					BpelOperations bpelOperations = (BpelOperations) e.getPath().getLastPathComponent();
					displayBpelProcessStructure(null, bpelOperations.getBpelProcess());
				} else if (e.getPath().getLastPathComponent() instanceof EsbProject) {
					processStructureTree.clear();
				}
			}
		});

	}

	/**
	 * display bpel process
	 * 
	 * @param bpelProcess
	 */
	private final void displayBpelProcessStructure(List<Activity> activities, BpelProject bpelProcess) {
		if (bpelProcess == null) {
			processStructureTree.clear();
			// txtProcessFolder.setText("");
		} else {
			processStructureTree.addBpelProcessStrukture(bpelProcess.getBpelProcessStrukture());
			// txtProcessFolder.setText(bpelProcess.getBpelXmlFile().getParent());
		}
		if (activities == null) {
			processStructureTree.clearSelectedOperationsTreePath();
		} else {
			processStructureTree.addSelectedActivities(activities);
		}

	}

}
