package com.tomecode.soa.bpel.dependency.analyzer.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.model.BpelProcess;
import com.tomecode.soa.bpel.model.PartnerLinkBinding;
import com.tomecode.soa.bpel.model.Workspace;

/**
 * 
 * Parser for bpel workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceParser {

	private final BpelParser bpelParser;

	public WorkspaceParser() {
		bpelParser = new BpelParser();
	}

	/**
	 * parse workspace file
	 * 
	 * @param workspaceFolder
	 * @return
	 * @throws BpelParserException
	 */
	public final Workspace parse(File workspaceFolder) throws BpelParserException {
		if (workspaceFolder.isFile()) {
			throw new BpelParserException(workspaceFolder + " is file!", true);
		}
		List<File> bpelXmlFiles = new ArrayList<File>();
		loadBpelXmlFiles(workspaceFolder, bpelXmlFiles);
		Workspace workspace = new Workspace(workspaceFolder);
		for (File bpelXml : bpelXmlFiles) {
			workspace.addBpelProcess(bpelParser.parseBpelXml(bpelXml));
		}

		for (BpelProcess bpelProcess : workspace.getBpelProcesses()) {
			for (PartnerLinkBinding partnerLinkBinding : bpelProcess.getPartnerLinkBindings()) {
				if (partnerLinkBinding.getBpelProcess() == null) {
					bpelParser.parseBpelByWsdl(partnerLinkBinding);
				}
			}
		}
		return workspace;
	}

	/**
	 * load all bpel.xml in workspace
	 * 
	 * @param workspace
	 * @param bpelXmlFiles
	 */
	private final void loadBpelXmlFiles(File workspace, List<File> bpelXmlFiles) {
		File[] files = workspace.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				loadBpelXmlFiles(file, bpelXmlFiles);
			}
			if (file.isFile() && file.getName().equals("bpel.xml")) {
				bpelXmlFiles.add(file);
			}
		}
	}

}
