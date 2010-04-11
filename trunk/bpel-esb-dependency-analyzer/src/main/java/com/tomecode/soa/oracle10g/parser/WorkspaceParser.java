package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.oracle10g.bpel.Bpel;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.bpel.Workspace;
import com.tomecode.soa.process.Service;
import com.tomecode.soa.process.ServiceType;

/**
 * 
 * Parser for bpel workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceParser {

	/**
	 * bpel parser
	 */
	private final BpelParser bpelParser;
	/**
	 * esb parser
	 */
	private final EsbParser esbParser;

	/**
	 * Constructor
	 */
	public WorkspaceParser() {
		bpelParser = new BpelParser();
		esbParser = new EsbParser();
	}

	/**
	 * parse workspace file
	 * 
	 * @param workspaceFolder
	 * @return
	 * @throws ServiceParserException
	 */
	public final Workspace parse(File workspaceFolder) throws ServiceParserException {
		if (workspaceFolder.isFile()) {
			throw new ServiceParserException(workspaceFolder + " is file!", true);
		}
		try {
			List<File> listOfFiles = new ArrayList<File>();
			findBpelXmlFiles(workspaceFolder, listOfFiles);
			Workspace workspace = new Workspace(workspaceFolder);
			for (File bpelXml : listOfFiles) {
				workspace.addService(bpelParser.parseBpelXml(bpelXml));
			}

			for (Service service : workspace.getServices()) {
				if (service.getType() == ServiceType.ORACLE10G_BPEL) {
					Bpel bpel = (Bpel) service;
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getBpelProcess() == null) {
							bpelParser.parseBpelByWsdl(partnerLinkBinding);
						}
					}
				}
			}

			List<File> esbFiles = new ArrayList<File>();
			findEsbXmlFiles(workspaceFolder, esbFiles);
			for (File esbFile : esbFiles) {
				workspace.addService(esbParser.parse(esbFile));
			}

			return workspace;
		} catch (Exception e) {
			throw new ServiceParserException(e);
		}

	}

	/**
	 * find files in workspace
	 * 
	 * @param workspace
	 * @param findedFiles
	 */
	private final void findBpelXmlFiles(File workspace, List<File> findedFiles) {
		File[] files = workspace.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findBpelXmlFiles(file, findedFiles);
			}
			if (file.isFile() && file.getName().equals("bpel.xml")) {
				findedFiles.add(file);
			}
		}
	}

	private final void findEsbXmlFiles(File workspace, List<File> findedFiles) {
		File[] files = workspace.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findEsbXmlFiles(file, findedFiles);
			}
			if (file.isFile() && file.getName().endsWith(".esb")) {
				findedFiles.add(file);
			}
		}
	}

}
