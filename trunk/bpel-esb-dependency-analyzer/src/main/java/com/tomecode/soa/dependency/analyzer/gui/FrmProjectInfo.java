package com.tomecode.soa.dependency.analyzer.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.wsdl.PortType;
import com.tomecode.util.gui.Dialog;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * Info about project
 * 
 * @author Tomas Frastia
 * 
 */
public final class FrmProjectInfo extends Dialog {

	private static final long serialVersionUID = 5487492405891417081L;

	/**
	 * Constructor
	 * 
	 * @param owner
	 * @param project
	 */
	private FrmProjectInfo(Frame owner, Project project) {
		super(owner, "About Project", 560, 370, false, true, true, true);
		setIconImage(IconFactory.BDA_SMALL.getImage());
		buttonCancel.setText("Ok");

		JLabel infoIcon = new JLabel(IconFactory.INFO);

		JPanel pRoot = PanelFactory.createBorderLayout(15, 15, 0, 15);
		panelRoot.add(pRoot, BorderLayout.CENTER);
		pRoot.add(PanelFactory.wrapByBorderLayout(infoIcon, BorderLayout.NORTH), BorderLayout.WEST);

		JPanel pData = PanelFactory.createBorderLayout();

		JPanel pFields = PanelFactory.createGridLayout(4, 0);
		pFields.add(PanelFactory.wrapWithTile("Name", newTxt(project.toString())));
		pFields.add(PanelFactory.wrapWithTile("Type", newTxt(project.getType().getTitle())));
		if (project.getType() == ProjectType.ORACLE10G_BPEL) {
			BpelProject bpelProject = (BpelProject) project;
			pFields.add(PanelFactory.wrapWithTile("Path", newTxt(bpelProject.getBpelXmlFile().toString())));
			if (bpelProject.getWsdl() != null || bpelProject.getWsdl().getFile() != null) {
				pFields.add(PanelFactory.wrapWithTile("WSDL", newTxt(bpelProject.getWsdl().getFile().toString())));
				if (bpelProject.getWsdl().getPortType() != null) {
					PortType portType = bpelProject.getWsdl().getPortType();
					JList jList = new JList(portType.getWsdlOperations().toArray());
					pData.add(PanelFactory.wrapWithTile("Operations", new JScrollPane(jList)), BorderLayout.CENTER);
				}
			}

		} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
			EsbProject esbProject = (EsbProject) project;
			pFields.add(PanelFactory.wrapWithTile("Path", newTxt(esbProject.getFolder().toString())));
			pFields.add(new JLabel(""));
		}

		pData.add(pFields, BorderLayout.NORTH);
		pRoot.add(pData, BorderLayout.CENTER);

	}

	/**
	 * create new text
	 * 
	 * @param value
	 * @return
	 */
	private final JTextField newTxt(String value) {
		JTextField txt = new JTextField(value);
		txt.setEditable(false);
		return txt;
	}

	/**
	 * show detailed info about project
	 * 
	 * @param project
	 */
	public final static void showMe(final Project project) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new FrmProjectInfo(Desktop.getFrame(), project).setVisible(true);
			}
		});
	}

}
