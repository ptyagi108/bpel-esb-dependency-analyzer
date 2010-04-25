package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

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
				workspace.addProject(bpelParser.parseBpelXml(bpelXml));
			}

			for (Project service : workspace.getServices()) {
				if (service.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpel = (BpelProject) service;
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getBpelProcess() == null) {
							bpelParser.parseBpelByWsdl(partnerLinkBinding);
						}
					}
				}
			}

			List<File> esbProjectFolders = new ArrayList<File>();
			findAllEsbProjectFolders(workspaceFolder, esbProjectFolders);
			for (File esbProjectFolder : esbProjectFolders) {
				workspace.addProject(esbParser.parse(esbProjectFolder));
			}

			return workspace;
		} catch (Exception e) {
			e.printStackTrace();
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

	private final void findAllEsbProjectFolders(File workspace, List<File> findedFolders) {
		File[] files = workspace.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findAllEsbProjectFolders(file, findedFolders);
			}
			if (file.isFile() && (file.getName().endsWith(".esb"))) {

				if (!containsFile(file.getParentFile(), findedFolders)) {
					findedFolders.add(file.getParentFile());
				}
			}
		}
	}

	private final boolean containsFile(File targetFile, List<File> files) {
		for (File file : files) {
			if (file.toString().equals(targetFile.toString())) {
				return true;
			}
		}
		return false;
	}

}
