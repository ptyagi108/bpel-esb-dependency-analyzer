package com.tomecode.soa.dependency.analyzer.gui.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.tree.BpelOperationTree;
import com.tomecode.soa.dependency.analyzer.gui.tree.EsbServiceTree;
import com.tomecode.soa.dependency.analyzer.gui.tree.ProcessStructureTree;
import com.tomecode.soa.dependency.analyzer.gui.tree.WorkspaceTree;
import com.tomecode.soa.dependency.analyzer.gui.utils.panels.UtilsPanel;
import com.tomecode.soa.dependency.analyzer.gui.utils.panels.UtilsPanel.UtilsPanelListener;
import com.tomecode.soa.dependency.analyzer.gui.visual.WorkspaceVisualPanel;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.MultiWorkspace;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.UnknownProject;
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

	/**
	 * display basic info about project
	 */
	private static final String P_ROOT_INFO_ABOUT_PROJECT = "p.root.info.about.project";
	/**
	 * display detail info about project
	 */
	private static final String P_ROOT_DETAIL_ABOUT_PROJECT = "p.root.detail.about.project";

	private static final String P_ROOT_UNKNOWN_PROJECT = "p.root.unknown.project";

	private final CardLayout layoutRootWorkspace;

	private final CardLayout layoutActivities;

	private static final String P_ACTIVITIES_BPEL_TREE = "p.activities.bpel.tree";

	private static final String P_ACTIVITIES_ESB_TREE = "p.activities.esb.tree";

	/**
	 * {@link WorkspaceTree}
	 */
	private final WorkspaceTree workspaceTree;
	/**
	 * {@link BpelOperationTree}
	 */
	private final BpelOperationTree projectOperationTree;
	/**
	 * {@link EsbServiceTree}
	 */
	private final EsbServiceTree projectEsbServiceTree;
	/**
	 * {@link ProcessStructureTree}
	 */
	private final ProcessStructureTree processStructureTree;

	private final UnknownPanel unknownPanel;
	/**
	 * Simple panel for display utilities
	 */
	private UtilsPanel utilsPanel;

	private WorkspaceVisualPanel visualPanel;

	private final JTextField unknownProjectName;
	private final JTextField unknownWsdl;
	private final JTextField unknownDependencyProject;

	private final JSplitPane spUtilsAndRoot;

	/**
	 * Constructor
	 * 
	 * @param multiWorkspace
	 */
	public WorkspacePanel(MultiWorkspace multiWorkspace, final WorkspaceChangeListener workspaceChangeListener) {
		super(new BorderLayout());
		this.spUtilsAndRoot = PanelFactory.createSplitPanel();
		this.spUtilsAndRoot.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.spUtilsAndRoot.setDividerLocation(500);
		// this.workspaceChangeListener =workspaceChangeListener;
		this.utilsPanel = new UtilsPanel();
		this.visualPanel = new WorkspaceVisualPanel(utilsPanel);
		this.workspaceTree = new WorkspaceTree(multiWorkspace, utilsPanel);
		this.projectOperationTree = new BpelOperationTree(utilsPanel);
		this.projectEsbServiceTree = new EsbServiceTree(utilsPanel);
		this.processStructureTree = new ProcessStructureTree(utilsPanel);
		final JSplitPane spRootWorkspace = PanelFactory.createSplitPanel();
		add(spRootWorkspace, BorderLayout.CENTER);
		spRootWorkspace.add(PanelFactory.createBorderLayout("Project Dependencies", new JScrollPane(workspaceTree)));
		spRootWorkspace.setDividerLocation(210);

		layoutRootWorkspace = new CardLayout();
		layoutActivities = new CardLayout();
		final JPanel pLayoutRoot = new JPanel(layoutRootWorkspace);
		spRootWorkspace.add(pLayoutRoot);

		// panel cotains basic info about workspace and display when
		// workspaceTree select workspace
		JPanel pAboutWorkspace = PanelFactory.createBorderLayout();
		pLayoutRoot.add(pAboutWorkspace, P_ROOT_INFO_ABOUT_PROJECT);

		// panel contains visualizer and dependency tree about selected project
		JPanel pDetailProject = PanelFactory.createBorderLayout();
		pLayoutRoot.add(pDetailProject, P_ROOT_DETAIL_ABOUT_PROJECT);

		JPanel pUnknownProject = PanelFactory.createBorderLayout("Unknown Project or Service");
		pLayoutRoot.add(pUnknownProject, P_ROOT_UNKNOWN_PROJECT);

		JLabel unknownIcon = new JLabel();
		unknownIcon.setIcon(IconFactory.UNKNOWN_BIG);
		pUnknownProject.add(PanelFactory.wrapByBorderLayout(unknownIcon, BorderLayout.NORTH), BorderLayout.WEST);

		JPanel pUnknownDetails = PanelFactory.createBorderLayout();
		pUnknownProject.add(pUnknownDetails, BorderLayout.CENTER);

		JPanel pUnknownFields = PanelFactory.createGridLayout(3, 0);
		pUnknownDetails.add(pUnknownFields, BorderLayout.NORTH);
		unknownPanel = new UnknownPanel(utilsPanel);
		pUnknownDetails.add(unknownPanel, BorderLayout.CENTER);
		unknownProjectName = new JTextField();
		unknownProjectName.setEditable(false);
		pUnknownFields.add(PanelFactory.wrapWithTile("Project name", unknownProjectName));

		unknownWsdl = new JTextField();
		unknownWsdl.setEditable(false);
		pUnknownFields.add(PanelFactory.wrapWithTile("WSDL", unknownWsdl));

		unknownDependencyProject = new JTextField();
		unknownDependencyProject.setEditable(false);
		pUnknownFields.add(PanelFactory.wrapWithTile("Dependency from project", unknownDependencyProject));

		final JSplitPane spVisualAndBasicGraph = PanelFactory.createSplitPanel();
		spVisualAndBasicGraph.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spVisualAndBasicGraph.setDividerLocation(350);
		pDetailProject.add(spVisualAndBasicGraph, BorderLayout.CENTER);

		JSplitPane spActivitiesTrees = PanelFactory.createSplitPanel();
		spActivitiesTrees.add(PanelFactory.createBorderLayout("Depending on activity-based operations", new JScrollPane(projectOperationTree)));
		spActivitiesTrees.add(PanelFactory.createBorderLayout("Project Structure", new JScrollPane(processStructureTree)));
		spActivitiesTrees.setDividerLocation(350);

		final JPanel pBpelProject = PanelFactory.createBorderLayout();
		pBpelProject.add(spActivitiesTrees, BorderLayout.CENTER);
		// JPanel pUtils = PanelFactory.createBorderLayout();
		// pUtils.add(utilsPanel, BorderLayout.CENTER);

		spVisualAndBasicGraph.add(visualPanel);// (pCardPanel);

		final JPanel pActivities = new JPanel(layoutActivities);
		spVisualAndBasicGraph.add(pActivities);

		pActivities.add(pBpelProject, P_ACTIVITIES_BPEL_TREE);

		// addComponentListener(new ComponentAdapter() {
		//
		// @Override
		// public final void componentResized(ComponentEvent e) {
		// Dimension dimension = e.getComponent().getSize();
		// System.out.println("resise1 height:" +
		// e.getComponent().getSize().height + " wigth:" +
		// e.getComponent().getSize().width);
		// int location = (int) (dimension.height - (dimension.height * 0.20));
		// spUtilsAndRoot.setDividerLocation(location);
		// spVisualAndBasicGraph.setDividerLocation(600);
		// spVisualAndBasicGraph.setPreferredSize(new Dimension(dimension.width,
		// 700));
		// System.out.println("location" + location);
		//				
		// System.out.println("spVisualAndBasicGraph.getDividerLocation()" +
		// spVisualAndBasicGraph.getDividerLocation());
		// spVisualAndBasicGraph.updateUI();
		// }
		//
		// });

		utilsPanel.setListener(new UtilsPanelListener() {

			@Override
			public final void show() {
				if (findUtilsPanelIndex() == -1) {
					remove(spRootWorkspace);
					if (spUtilsAndRoot.getComponentCount() == 1) {
						spUtilsAndRoot.add(spRootWorkspace);
						spUtilsAndRoot.add(utilsPanel);
					} else if (spUtilsAndRoot.getComponentCount() == 2) {
						spUtilsAndRoot.add(spRootWorkspace);
					}
					add(spUtilsAndRoot, BorderLayout.CENTER);
					updateUI();
				}
			}

			@Override
			public final void hide() {
				if (findUtilsPanelIndex() != -1) {
					remove(spUtilsAndRoot);
					add(spRootWorkspace, BorderLayout.CENTER);
					updateUI();
				}
			}
		});

		JPanel pEsbProject = PanelFactory.createBorderLayout();
		pEsbProject.add(PanelFactory.wrapWithTile("Esb Services:", new JScrollPane(projectEsbServiceTree)), BorderLayout.CENTER);

		final JSplitPane spEsbPanel = PanelFactory.createSplitPanel();
		spEsbPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spEsbPanel.setDividerLocation(350);

		pActivities.add(pEsbProject, P_ACTIVITIES_ESB_TREE);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				spVisualAndBasicGraph.setDividerLocation((e.getComponent().getSize().height - 140));
				spVisualAndBasicGraph.updateUI();
			}
		});

		add(spRootWorkspace, BorderLayout.CENTER);

		// select bpel proces
		this.workspaceTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {

				Object selectedComp = e.getPath().getLastPathComponent();
				if (selectedComp instanceof Workspace) {
					layoutRootWorkspace.show(pLayoutRoot, P_ROOT_INFO_ABOUT_PROJECT);
					workspaceChangeListener.hideVisualPanel();
				} else if (selectedComp instanceof BpelProject) {
					BpelProject bpelProject = (BpelProject) selectedComp;
					if (bpelProject != null) {
						projectOperationTree.addBpelProcessOperations(bpelProject.getBpelOperations());
						processStructureTree.addBpelProcessStrukture(bpelProject.getBpelProcessStrukture());
						visualPanel.showGraphBpel(bpelProject, true);
					}
					layoutActivities.show(pActivities, P_ACTIVITIES_BPEL_TREE);
					layoutRootWorkspace.show(pLayoutRoot, P_ROOT_DETAIL_ABOUT_PROJECT);
					workspaceChangeListener.displayVisualPanel();
				} else if (selectedComp instanceof EsbProject) {
					EsbProject esbProject = (EsbProject) selectedComp;
					projectEsbServiceTree.addEsbProject(esbProject);
					visualPanel.showGraphEsb(esbProject, true);
					layoutActivities.show(pActivities, P_ACTIVITIES_ESB_TREE);
					layoutRootWorkspace.show(pLayoutRoot, P_ROOT_DETAIL_ABOUT_PROJECT);
					workspaceChangeListener.displayVisualPanel();
				} else if (selectedComp instanceof UnknownProject) {
					UnknownProject unknownProject = (UnknownProject) selectedComp;
					PartnerLinkBinding partnerLinkBinding = unknownProject.getPartnerLinkBinding();
					unknownProjectName.setText(unknownProject.toString());
					unknownDependencyProject.setText(partnerLinkBinding.getParent().toString());
					unknownWsdl.setText(partnerLinkBinding.getWsdlLocation());
					unknownPanel.showAffectedProcess(partnerLinkBinding);
					layoutRootWorkspace.show(pLayoutRoot, P_ROOT_UNKNOWN_PROJECT);
					workspaceChangeListener.hideVisualPanel();
				} else {
					processStructureTree.clear();
					projectOperationTree.clear();
					visualPanel.clearCells();
					workspaceChangeListener.hideVisualPanel();
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
			processStructureTree.setSelectedActivities(activities);
		}
	}

	public final WorkspaceVisualPanel getVisualPanel() {
		return visualPanel;
	}

	/**
	 * find {@link UtilsPanel} index
	 * 
	 * @return
	 */
	private final int findUtilsPanelIndex() {
		for (int i = 0; i <= getComponentCount() - 1; i++) {
			if (getComponent(i).equals(spUtilsAndRoot)) {
				return i;
			}
			// if (getComponent(i).equals(spUtilsAndRoot) instanceof JSplitPane)
			// {
			// JSplitPane jSplitPane = ((JSplitPane) getComponent(i));
			// if (jSplitPane.getName() != null &&
			// jSplitPane.getName().equals("utils")) {
			// return i;
			// }
			// }
		}
		return -1;
	}

}
