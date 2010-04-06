package com.tomecode.soa.bpel.dependency.analyzer.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.model.Workspace;

/**
 * Parser for {@link Workspace}
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkSpaceParser {

	private final BpelParser bpelParser;
	

	public static final void main(String[] arg) throws BpelParserException {
		WorkSpaceParser parser = new WorkSpaceParser();
		parser.parse(new File("C:/ORACLE/projects/BPEL/samples/"));
	}

	public WorkSpaceParser() {
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
