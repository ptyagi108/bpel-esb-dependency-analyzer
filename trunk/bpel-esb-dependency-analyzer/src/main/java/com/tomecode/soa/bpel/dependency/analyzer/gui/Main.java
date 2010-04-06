package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.tomecode.soa.bpel.dependency.analyzer.parser.BpelParser;
import com.tomecode.soa.bpel.dependency.analyzer.parser.BpelParserException;
import com.tomecode.soa.bpel.model.BpelProcess;

public class Main {

	public static final void main(String[] arg) throws BpelParserException {
		JFrame frame = new JFrame();
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		BpelParser bpelParser = new BpelParser();
		BpelProcess bpelProcess = bpelParser.parseBpelXml(new File("C:/ORACLE/projects/BPEL/samples/Client/bpel"));

//		WorkSpaceParser workSpaceParser = new WorkSpaceParser();
//		Workspace workspace = workSpaceParser.parse(new File("C:/ORACLE/projects/BPEL/samples"));

		JTree jTree = new JTree();
		DefaultTreeModel defaultTreeModel = new DefaultTreeModel(bpelProcess);
		jTree.setModel(defaultTreeModel);

		defaultTreeModel.setRoot(bpelProcess);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(jTree), BorderLayout.CENTER);
		container.add(panel, BorderLayout.CENTER);

		frame.setSize(300, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
