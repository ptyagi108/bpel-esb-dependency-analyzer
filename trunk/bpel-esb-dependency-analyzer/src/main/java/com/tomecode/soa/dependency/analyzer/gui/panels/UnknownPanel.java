package com.tomecode.soa.dependency.analyzer.gui.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.gui.tree.BpelUnknownOperationTree;
import com.tomecode.soa.dependency.analyzer.gui.tree.ProcessStructureTree;
import com.tomecode.soa.dependency.analyzer.gui.utils.panels.UtilsPanel;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.util.gui.PanelFactory;

/**
 * 
 * View a list of activities which use {@link UnknownProject} and affected bpel
 * process actvities tree
 * 
 * @author Tomas Frastia
 * 
 */
public final class UnknownPanel extends JPanel {

	private static final long serialVersionUID = -8244283422604951813L;

	private final BpelUnknownOperationTree bpelUnknownOperationTree;

	/**
	 * proces structure tree
	 */
	private final ProcessStructureTree processStructureTree;

	/**
	 * Constructor
	 */
	public UnknownPanel(UtilsPanel workspaceUtilsPanel) {
		super(new BorderLayout());
		setBorder(new TitledBorder(""));
		processStructureTree = new ProcessStructureTree(workspaceUtilsPanel);
		bpelUnknownOperationTree = new BpelUnknownOperationTree();
		bpelUnknownOperationTree.setRootVisible(false);

		JSplitPane splitPane = PanelFactory.createSplitPanel();
		splitPane.setDividerLocation(310);
		splitPane.add(PanelFactory.wrapWithTile("Affected activities", new JScrollPane(bpelUnknownOperationTree)));
		splitPane.add(PanelFactory.wrapWithTile("Project Structure", new JScrollPane(processStructureTree)));

		add(splitPane, BorderLayout.CENTER);

		bpelUnknownOperationTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public final void valueChanged(TreeSelectionEvent e) {
				if (e.getPath().getLastPathComponent() instanceof Operation) {
					Operation operation = (Operation) e.getPath().getLastPathComponent();
					displayBpelProcessStructure(operation.getActivities(), operation.getOwnerBpelProject());
				}
			}
		});
	}

	/**
	 * display affected process structure and list of activities which use
	 * unknown project or service
	 * 
	 * @param partnerLinkBinding
	 */
	public final void showAffectedProcess(PartnerLinkBinding partnerLinkBinding) {
		processStructureTree.clear();
		processStructureTree.clearSelectedOperationsTreePath();
		processStructureTree.addBpelProcessStrukture(partnerLinkBinding.getParent().getBpelProcessStrukture());
		BpelOperations bpelOperations = partnerLinkBinding.getParent().getBpelOperations();

		UnknonwNode unknonwNode = new UnknonwNode();
		for (int i = 0; i <= bpelOperations.getChildCount() - 1; i++) {
			Operation operation = (Operation) bpelOperations.getChildAt(i);

			try {
				if (operation.getPartnerLinkBinding() != null) {
					if (operation.getPartnerLinkBpelProcess() == null) {
						// add only operations/activity which use unknown
						// project
						if (operation.getPartnerLinkBinding().equals(partnerLinkBinding)) {
							unknonwNode.addOperation(operation);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		bpelUnknownOperationTree.setUnknownNode(unknonwNode);
		processStructureTree.setSelectedActivities(new ArrayList<Activity>());
	}

	/**
	 * display bpel process structure
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

	/**
	 * simple helper node for display affected operations/activities which use
	 * {@link UnknonwNode}
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public static final class UnknonwNode implements TreeNode {

		private final List<Operation> operations = new ArrayList<Operation>();

		@Override
		public Enumeration<?> children() {
			return null;
		}

		public void addOperation(Operation operation) {
			Operation newOperation = operation.clone();
			newOperation.setVisiblePartnerLinks(false);
			operations.add(newOperation);
		}

		@Override
		public boolean getAllowsChildren() {
			return !operations.isEmpty();
		}

		@Override
		public TreeNode getChildAt(int childIndex) {
			return operations.get(childIndex);
		}

		@Override
		public int getChildCount() {
			return operations.size();
		}

		@Override
		public int getIndex(TreeNode node) {
			return operations.indexOf(node);
		}

		@Override
		public TreeNode getParent() {
			return null;
		}

		@Override
		public boolean isLeaf() {
			return operations.isEmpty();
		}

	}
}
