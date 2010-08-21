package com.tomecode.soa.ora.suite10g.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.suite10g.esb.EsbOperation;
import com.tomecode.soa.ora.suite10g.esb.EsbProject;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.project.BpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * 
 * Parser for file with extension .jws
 * 
 * @author Tomas Frastia
 * 
 */
public final class Ora10gMWorkspaceParser extends AbstractParser {

	/**
	 * BPEL parser
	 */
	private final Ora10gBpelParser bpelParser;

	/**
	 * ESB parser
	 */
	private final EsbParser esbParser;

	/**
	 * Constructor
	 */
	public Ora10gMWorkspaceParser() {
		bpelParser = new Ora10gBpelParser();
		esbParser = new EsbParser();
	}

	/**
	 * parse {@link Ora10gMultiWorkspace}
	 * 
	 * @param workspaceFolder
	 * @return
	 * @throws ServiceParserException
	 */
	public final Ora10gMultiWorkspace parse(File workspaceFolder) throws ServiceParserException {
		List<File> jwsFiles = new ArrayList<File>();
		findAllJws(workspaceFolder, jwsFiles);

		Ora10gMultiWorkspace multiWorkspace = new Ora10gMultiWorkspace("empty", workspaceFolder);

		for (File jwsFile : jwsFiles) {

			// create new workspace
			Ora10gWorkspace workspace = new Ora10gWorkspace(getNameWithouExtension(jwsFile.getName()), jwsFile);
			multiWorkspace.addWorkspace(workspace);
			try {
				// load list of projects from jws
				List<File> jwsProjectFiles = parseListOfProjectsFromJWS(parseXml(jwsFile), jwsFile.getParentFile());

				// parse all projects from jws and return list this process
				List<File> listOfParsedBpelEsb = parseBpelEsbProjects(jwsProjectFiles, workspace, true);

				// parse all projects from file system wiche not are in jws
				List<File> fsProjects = new ArrayList<File>();
				findBpelEsbProjectFromFS(listOfParsedBpelEsb, fsProjects, jwsFile.getParentFile());
				parseBpelEsbProjects(fsProjects, workspace, false);
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}

		}

		analysesBpelDependencies(multiWorkspace);
		analysesEsbDependencies(multiWorkspace);
		analysesDepBetweenBpelEsb(multiWorkspace);

		analysesBpelProjectDependencies(multiWorkspace);
		return multiWorkspace;
	}

	/**
	 * fix for BPEL projects tree
	 * 
	 * @param multiWorkspace
	 */
	private final void analysesBpelProjectDependencies(Ora10gMultiWorkspace multiWorkspace) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					((BpelProject) project).analysisProjectDependencies();
				}
			}
		}

	}

	/**
	 * analysis of dependencies between BPEL and ESB
	 * 
	 * @param multiWorkspace
	 */
	private final void analysesDepBetweenBpelEsb(Ora10gMultiWorkspace multiWorkspace) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {

			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpelProject = (BpelProject) project;
					for (PartnerLinkBinding partnerLinkBinding : bpelProject.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyEsbProject() == null) {

							URL urlWsdl = parseWsdlToUrl(partnerLinkBinding.getWsdlLocation());
							if (urlWsdl != null) {
								String qName = esbParser.convertWsdlToQname(urlWsdl);
								if (qName != null) {
									EsbProject qNameProject = findEsbProjectByQname(qName, urlWsdl, multiWorkspace);
									if (qNameProject != null) {
										partnerLinkBinding.setDependencyEsbProject(qNameProject);// .setDependencyProject(qNameProject);
									}
								}

							}

						}
					}
				} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
					EsbProject esbProject = (EsbProject) project;

					List<EsbSvc> esbSvcs = esbProject.getAllEsbSvc();
					for (EsbSvc esbSvc : esbSvcs) {
						URL url = parseWsdlToUrl(esbSvc.getWsdlURL());
						if (url != null) {
							if (url.getFile().startsWith("/orabpel")) {
								BpelProject bpelProject = findBpelProjectForEsb(multiWorkspace, url.getFile());
								if (bpelProject != null) {
									compareEsbAndBpelOperation(esbSvc, bpelProject);
								}

							}
						}
					}
				}
			}

		}
	}

	/**
	 * check, whether BPEL project wsdl-operation is in ESB service
	 * 
	 * @param esbSvc
	 * @param bpelProject
	 */
	private final void compareEsbAndBpelOperation(EsbSvc esbSvc, BpelProject bpelProject) {
		for (EsbOperation esbOperation : esbSvc.getEsbOperations()) {
			if (bpelProject.getWsdl() != null) {
				if (bpelProject.getWsdl().existWsldOperation(esbOperation.getWsdlOperation())) {
					esbSvc.getOwnerEsbProject().addDependency(bpelProject);
					esbOperation.addDepdendencyProject(bpelProject);

				}
			}

		}

	}

	/**
	 * find BPEL project for ESB service
	 * 
	 * @param multiWorkspace
	 * @param url
	 * @return
	 */
	private final BpelProject findBpelProjectForEsb(Ora10gMultiWorkspace multiWorkspace, String url) {
		int index = url.indexOf("?wsdl");
		if (index != -1) {
			url = url.replace("?", ".");

			index = url.lastIndexOf("/");
			if (index != -1) {
				url = url.substring(0, index);
				String processName = url.substring(url.lastIndexOf("/") + 1, index);
				return findBpelByName(multiWorkspace, processName);
			}

		}
		return null;
	}

	/**
	 * find BPEL process by name in {@link Ora10gMultiWorkspace}
	 * 
	 * @param multiWorkspace
	 * @param bpelProcessName
	 * @return if not found, return <b>null</b>
	 */
	private final BpelProject findBpelByName(Ora10gMultiWorkspace multiWorkspace, String bpelProcessName) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpelProject = (BpelProject) project;
					if (bpelProject.toString().equals(bpelProcessName)) {
						return bpelProject;
					}
				}
			}
		}
		return null;
	}

	/**
	 * parse all BPEL and ESB files
	 * 
	 * @param listOfProjects
	 * @param workspace
	 * @param isInJws
	 * @return list of bpel.xml and *.esb witch is parsed
	 */
	private final List<File> parseBpelEsbProjects(List<File> listOfProjects, Ora10gWorkspace workspace, boolean isInJws) {
		List<File> parsedBpelEsbFiles = new ArrayList<File>();
		for (File projectFile : listOfProjects) {
			File bpelFile = findBpelXmlFile(projectFile.getParentFile());
			if (bpelFile != null) {
				try {

					parsedBpelEsbFiles.add(bpelFile);
					BpelProject bpelProject = bpelParser.parseBpelXml(bpelFile);
					bpelProject.setInJws(isInJws);
					bpelProject.setWorkspace(workspace);
					workspace.addProject(bpelProject);
				} catch (ServiceParserException ep) {
					// TODO: dokoncit error project
					ep.printStackTrace();
				}
			} else {
				File esbFile = findEsbProjectFolder(projectFile.getParentFile());
				if (esbFile != null) {
					try {

						parsedBpelEsbFiles.add(esbFile);
						EsbProject esbProject = esbParser.parse(esbFile);
						esbProject.setInJws(isInJws);
						esbProject.setWorkspace(workspace);
						workspace.addProject(esbProject);
					} catch (ServiceParserException ep) {
						// TODO: dokoncit error project
						ep.printStackTrace();
					}
				}
			}
		}
		return parsedBpelEsbFiles;
	}

	/**
	 * parse all project from file system which not found in jws
	 * 
	 * @param projectFiles
	 * @param workspace
	 * @return
	 */
	private final void findBpelEsbProjectFromFS(List<File> projectFiles, List<File> fsProjects, File workspace) {
		File[] files = workspace.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					findBpelEsbProjectFromFS(projectFiles, fsProjects, file);
				}
				if (file.isFile() && file.getName().equals("bpel.xml") && !projectFiles.contains(file) && !fsProjects.contains(file)) {
					fsProjects.add(file);
				} else if (file.isFile() && file.getName().endsWith(".esb") && !projectFiles.contains(file.getParentFile()) && !fsProjects.contains(file.getParentFile())) {
					fsProjects.add(file.getParentFile());
				}
			}
		}
	}

	/**
	 * Analysis dependencies between BPEL processes
	 * 
	 * @param workspace
	 */
	private final void analysesBpelDependencies(Ora10gMultiWorkspace multiWorkspace) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project service : workspace.getProjects()) {
				if (service.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpel = (BpelProject) service;
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyEsbProject() == null) {
							try {
								bpelParser.parseBpelByWsdl(partnerLinkBinding);
							} catch (ServiceParserException e) {
								// TODO dokoncit
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * analysis of dependencies between ESB projects
	 * 
	 * @param multiWorkspace
	 */
	private final void analysesEsbDependencies(Ora10gMultiWorkspace multiWorkspace) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_ESB) {
					EsbProject esbProject = (EsbProject) project;

					analyzeEsbDependencies(multiWorkspace, esbProject);
				}
			}
		}
	}

	/**
	 * analyze ESB service dependencies
	 * 
	 * @param multiWorkspace
	 * @param sourceEsbProject
	 */
	private final void analyzeEsbDependencies(Ora10gMultiWorkspace multiWorkspace, EsbProject sourceEsbProject) {
		for (EsbSvc esbSvc : sourceEsbProject.getAllEsbSvc()) {
			URL url = parseWsdlToUrl(esbSvc.getWsdlURL());
			if (url != null) {
				String qName = esbParser.convertWsdlToQname(url);
				EsbProject esbProject = findEsbProjectByQname(qName, url, multiWorkspace);
				if (esbProject != null) {
					esbSvc.getOwnerEsbProject().addDependency(esbProject);
					// /esbSvc.get
					esbSvc.getEsbOperations().get(0).addDepdendencyProject(esbProject);
				}
			}

		}
	}

	/**
	 * find {@link EsbProject} by qName
	 * 
	 * @param qName
	 *            //service/@name
	 * @param serviceURL
	 *            WSDL from //service/serviceDefinition/wsdlURL
	 * @param workspace
	 * @return
	 */
	private final EsbProject findEsbProjectByQname(String qName, URL serviceURL, Ora10gMultiWorkspace multiWorkspace) {
		for (Ora10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_ESB) {
					EsbProject esbProject = (EsbProject) project;
					EsbProject fProject = esbProject.findEsbProjectByQname(qName, serviceURL);
					if (fProject != null) {
						return fProject;
					}
				}
			}
		}
		return null;
	}

	private final URL parseWsdlToUrl(String wsdl) {
		if (wsdl != null) {
			try {
				return new URL(wsdl);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * find BPEL XML in project folder
	 * 
	 * @param project
	 * @return
	 */
	private final File findBpelXmlFile(File project) {
		File[] files = project.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File bpel = findBpelXmlFile(file);
					if (bpel != null) {
						return bpel;
					}
				}
				if (file.isFile() && file.getName().equals("bpel.xml")) {
					return file;
				}
			}
		}
		return null;
	}

	/**
	 * find *.esb in workspace and return folder path
	 * 
	 * @param project
	 * @return
	 */
	private final File findEsbProjectFolder(File project) {
		File[] files = project.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File esb = findEsbProjectFolder(file);
					if (esb != null) {
						return esb;
					}
				}
				if (file.isFile() && (file.getName().endsWith(".esb"))) {
					return file.getParentFile();
				}
			}
		}
		return null;
	}

	/**
	 * parser jws file in workspaceFolder
	 * 
	 * @param element
	 * @param workspaceFolder
	 * @return
	 */
	private final List<File> parseListOfProjectsFromJWS(Element element, File workspaceFolder) {
		List<File> files = new ArrayList<File>();

		Element eList = element.element("list");
		if (eList != null) {
			String value = eList.attributeValue("n");
			if (value != null && "listOfChildren".equals(value)) {

				List<?> eListHash = eList.elements("hash");
				for (Object e : eListHash) {
					Element eHash = (Element) e;
					String url = parseEhash(eHash);
					if (url != null) {
						files.add(new File(workspaceFolder + File.separator + url));
					}
				}
			}
		}
		return files;
	}

	/**
	 * parse element hash
	 * 
	 * @param eHash
	 * @return
	 */
	private final String parseEhash(Element eHash) {
		Element eValue = eHash.element("value");
		if (eValue != null) {
			String vValue = eValue.attributeValue("v");
			if (vValue != null && "oracle.ide.model.Project".equals(vValue)) {
				Element eUrl = eHash.element("url");
				if (eUrl != null) {
					return eUrl.attributeValue("path");
				}
			}

		}

		return null;
	}

	/***
	 * find all file with extension .jws in workspace folder
	 * 
	 * @param workspaceFolder
	 * @param jwsFiles
	 * @throws ServiceParserException
	 */
	private final void findAllJws(File workspaceFolder, List<File> jwsFiles) throws ServiceParserException {
		if (workspaceFolder.isFile()) {
			throw new ServiceParserException("workspace folder: " + workspaceFolder + " is file", true);
		}
		File[] files = workspaceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					findAllJws(file, jwsFiles);
				}
				if (file.isFile() && file.getName().endsWith(".jws")) {
					jwsFiles.add(file);
				}
			}
		}
	}
}
