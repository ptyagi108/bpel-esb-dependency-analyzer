package com.tomecode.soa.ora.suite10g.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.suite10g.esb.EsbOperation;
import com.tomecode.soa.ora.suite10g.esb.EsbRoutingRule;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelEsbDependency;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gMultiWorkspace;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Parser for file with extension .jws
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
	private final Ora10gEsbParser esbParser;

	/**
	 * Constructor
	 */
	public Ora10gMWorkspaceParser() {
		bpelParser = new Ora10gBpelParser();
		esbParser = new Ora10gEsbParser();
	}

	/**
	 * parse all workspaces and projects in {@link MultiWorkspace}
	 * 
	 * @param ora10gMultiWorkspace
	 * @throws ServiceParserException
	 */
	public final void parse(Ora10gMultiWorkspace ora10gMultiWorkspace) throws ServiceParserException {

		for (Workspace w : ora10gMultiWorkspace.getWorkspaces()) {
			Ora10gWorkspace workspace = (Ora10gWorkspace) w;

			for (Project p : workspace.getProjects()) {
				if (p.getType() == ProjectType.ORACLE10G_BPEL) {
					Ora10gBpelProject bpelProject = (Ora10gBpelProject) p;
					bpelProject.setWorkspace(workspace);
					parseBpelProject(bpelProject);
				} else if (p.getType() == ProjectType.ORACLE10G_ESB) {
					Ora10gEsbProject esbProject = (Ora10gEsbProject) p;
					esbProject.setWorkspace(workspace);
					esbParser.parse(esbProject);
				}
			}
		}

		// List<File> jwsFiles = new ArrayList<File>();
		// findAllJws(path, jwsFiles);
		//
		// Ora10gMultiWorkspace multiWorkspace = new Ora10gMultiWorkspace(name,
		// path);
		//
		// for (File jwsFile : jwsFiles) {
		//
		// // create new workspace
		// Ora10gWorkspace workspace = new
		// Ora10gWorkspace(getNameWithouExtension(jwsFile.getName()), jwsFile);
		// multiWorkspace.addWorkspace(workspace);
		// try {
		// // load list of projects from jws
		// List<File> jwsProjectFiles =
		// parseListOfProjectsFromJWS(parseXml(jwsFile),
		// jwsFile.getParentFile());
		//
		// // parse all projects from jws and return list this process
		// List<File> listOfParsedBpelEsb =
		// parseBpelEsbProjects(jwsProjectFiles, workspace, true);
		//
		// // parse all projects from file system wiche not are in jws
		// List<File> fsProjects = new ArrayList<File>();
		// findBpelEsbProjectFromFS(listOfParsedBpelEsb, fsProjects,
		// jwsFile.getParentFile());
		// parseBpelEsbProjects(fsProjects, workspace, false);
		// } catch (ServiceParserException e) {
		// e.printStackTrace();
		// }
		//
		// }
		//
		analyseDependencies(ora10gMultiWorkspace);
		// return multiWorkspace;
	}

	/**
	 * parse new multi-workspace
	 * 
	 * @param multiWorkspace
	 * @throws ServiceParserException
	 */
	public final void parseNewMultiWorkspace(Ora10gMultiWorkspace multiWorkspace, List<String> workspacesPaths) throws ServiceParserException {

		for (String workspacePath : workspacesPaths) {
			File jwsFile = findAllProjectsInWorkspace(new File(workspacePath));
			if (jwsFile != null) {

				int index = jwsFile.getName().lastIndexOf(".");
				String name = jwsFile.getName().substring(0, index);
				Ora10gWorkspace workspace = new Ora10gWorkspace(name, jwsFile);
				multiWorkspace.addWorkspace(workspace);
				List<File> jwsProjectFiles = parseListOfProjectsFromJWS(parseXml(jwsFile), jwsFile.getParentFile());
				parseBpelEsbProjects(jwsProjectFiles, (Ora10gWorkspace) workspace, true);
			}

		}

		analyseDependencies(multiWorkspace);
	}

	/**
	 * find jws file in workspace dir
	 * 
	 * @param workspaceFolder
	 * @return
	 */
	private final File findAllProjectsInWorkspace(File workspaceFolder) {
		File[] files = workspaceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File jwsFile = findAllProjectsInWorkspace(file);
					if (jwsFile != null) {
						return jwsFile;
					}
				}
				if (file.isFile() && file.getName().endsWith(".jws")) {
					return file;
				}
			}
		}
		return null;
	}

	private final void parseBpelProject(Ora10gBpelProject project) {
		File bpelFile = findBpelXmlFile(project.getFile());
		if (bpelFile != null) {
			try {
				bpelParser.parseBpelXml(project, bpelFile);
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}
	}

	public final Ora10gWorkspace parseWorkspace(File workspaceFolder) throws ServiceParserException {
		List<File> jwsFiles = new ArrayList<File>();
		findAllJws(workspaceFolder, jwsFiles);

		// create new workspace
		Ora10gWorkspace workspace = new Ora10gWorkspace(getNameWithouExtension(workspaceFolder.getName()), workspaceFolder);

		for (File jwsFile : jwsFiles) {
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
				// TODO: fix
				e.printStackTrace();
			}
		}
		return workspace;
	}

	public final void analyseDependencies(Ora10gMultiWorkspace multiWorkspace) {
		analysesBpelDependencies(multiWorkspace);
		analysesEsbDependencies(multiWorkspace);
		analysesDepBetweenBpelEsb(multiWorkspace);
		analysesBpelProjectDependencies(multiWorkspace);
	}

	/**
	 * fix for BPEL projects tree
	 * 
	 * @param multiWorkspace
	 */
	private final void analysesBpelProjectDependencies(Ora10gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;
			for (Project project : ora10gWorkspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					((Ora10gBpelProject) project).analysisProjectDependencies();
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
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;

			for (Project project : ora10gWorkspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					Ora10gBpelProject bpelProject = (Ora10gBpelProject) project;
					for (PartnerLinkBinding partnerLinkBinding : bpelProject.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyEsbProject() == null) {

							URL urlWsdl = parseWsdlToUrl(partnerLinkBinding.getWsdlLocation());
							if (urlWsdl != null) {
								String qName = esbParser.convertWsdlToQname(urlWsdl);
								if (qName != null) {
									// TODO: parsovanie wsdl
									Ora10gEsbProject esbDepProject = findEsbProjectByQname(qName, urlWsdl, multiWorkspace);
									if (esbDepProject != null) {
										EsbSvc esbSvc = esbDepProject.findEsbSvcByQname(qName);
										partnerLinkBinding.setDependencyEsbProject(new BpelEsbDependency(esbDepProject, esbSvc, urlWsdl));
									} else {
										toString();
									}
								}

							}

						}
					}
				} else if (project.getType() == ProjectType.ORACLE10G_ESB) {
					Ora10gEsbProject esbProject = (Ora10gEsbProject) project;

					List<EsbSvc> esbSvcs = esbProject.getAllEsbSvc();
					for (EsbSvc esbSvc : esbSvcs) {
						URL url = parseWsdlToUrl(esbSvc.getWsdlURL());
						if (url != null) {
							if (url.getFile().startsWith("/orabpel")) {
								Ora10gBpelProject bpelProject = findBpelProjectForEsb(multiWorkspace, url.getFile());
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
	private final void compareEsbAndBpelOperation(EsbSvc esbSvc, Ora10gBpelProject bpelProject) {
		for (EsbOperation esbOperation : esbSvc.getEsbOperations()) {
			if (bpelProject.getWsdl() != null) {
				if (bpelProject.getWsdl().existWsldOperation(esbOperation.getWsdlOperation())) {
					esbSvc.getProject().addDependency(bpelProject);
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
	private final Ora10gBpelProject findBpelProjectForEsb(Ora10gMultiWorkspace multiWorkspace, String url) {
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
	private final Ora10gBpelProject findBpelByName(Ora10gMultiWorkspace multiWorkspace, String bpelProcessName) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;
			for (Project project : ora10gWorkspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_BPEL) {
					Ora10gBpelProject bpelProject = (Ora10gBpelProject) project;
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

					// TODO: dokonicit

					Ora10gBpelProject bpelProject = bpelParser.parseBpelXml(bpelFile);
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
						Ora10gEsbProject esbProject = esbParser.parse(esbFile);
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
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;

			for (Project service : ora10gWorkspace.getProjects()) {
				if (service.getType() == ProjectType.ORACLE10G_BPEL) {
					Ora10gBpelProject bpel = (Ora10gBpelProject) service;
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
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;

			for (Project project : ora10gWorkspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_ESB) {
					Ora10gEsbProject esbProject = (Ora10gEsbProject) project;

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
	private final void analyzeEsbDependencies(Ora10gMultiWorkspace multiWorkspace, Ora10gEsbProject sourceEsbProject) {
		List<EsbSvc> esbSvcs = sourceEsbProject.getAllEsbSvc();
		for (EsbSvc esbSvc : esbSvcs) {

			// if( esbSvc.getName().equals("LoggerReader_RS")){
			// esbSvc.toString();
			// }
			//
			// URL url = parseWsdlToUrl(esbSvc.getWsdlURL());
			// if (url != null) {
			// String qName = esbParser.convertWsdlToQname(url);
			// Ora10gEsbProject esbProject = findEsbProjectByQname(qName, url,
			// multiWorkspace);
			// if (esbProject != null) {
			// esbSvc.getOwnerEsbProject().addDependency(esbProject);
			// // /esbSvc.get
			// esbSvc.getEsbOperations().get(0).addDepdendencyProject(esbProject);
			// }
			// }

			if (esbSvc.getTargetServiceQName() != null) {
				EsbSvc depEsbSvc = findEsbSvcByQname(esbSvc.getTargetServiceQName(), sourceEsbProject);
				if (depEsbSvc != null) {
					for (EsbOperation esbOperation : esbSvc.getEsbOperations()) {
						EsbOperation depEsbOperation = depEsbSvc.getOperation(esbOperation.getWsdlOperation());
						if (depEsbOperation != null) {
							esbOperation.addDependency(depEsbSvc);

							for (Object o : esbOperation.getDependencies()) {
								if (o instanceof EsbRoutingRule) {
									EsbRoutingRule routingRule = (EsbRoutingRule) o;

									EsbSvc routingDepSvc = findEsbSvcByQname(routingRule.getQname(), sourceEsbProject);
									routingRule.addDependencySvc(routingDepSvc);
								}
							}
						}
					}

				}

			} else {
				for (EsbOperation esbOperation : esbSvc.getEsbOperations()) {

					for (Object o : esbOperation.getDependencies()) {
						if (o instanceof EsbRoutingRule) {
							EsbRoutingRule routingRule = (EsbRoutingRule) o;

							EsbSvc routingDepSvc = findEsbSvcByQname(routingRule.getServiceQName(), sourceEsbProject);
							routingRule.addDependencySvc(routingDepSvc);
						}
					}

				}

			}

		}
	}

	/**
	 * find {@link EsbSvc} by qName
	 * 
	 * @param qName
	 * @param esbProject
	 * @return
	 */
	private final EsbSvc findEsbSvcByQname(String qName, Ora10gEsbProject esbProject) {
		EsbSvc esbSvc = esbProject.findEsbSvcByQname(qName);
		if (esbSvc != null) {
			return esbSvc;
		}

		MultiWorkspace multiWorkspace = esbProject.getWorkpsace().getMultiWorkspace();
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			if (workspace.getType() == WorkspaceType.ORACLE_1OG) {
				for (Project project : workspace.getProjects()) {

					if (!project.equals(esbProject) && project.getType() == ProjectType.ORACLE10G_ESB) {
						Ora10gEsbProject esbProjectNext = (Ora10gEsbProject) project;
						EsbSvc svcNext = esbProjectNext.findEsbSvcByQname(qName);
						if (svcNext != null) {
							return svcNext;
						}
					}

				}
			}
		}

		return null;
	}

	/**
	 * find {@link Ora10gEsbProject} by qName
	 * 
	 * @param qName
	 *            //service/@name
	 * @param serviceURL
	 *            WSDL from //service/serviceDefinition/wsdlURL
	 * @param workspace
	 * @return
	 */
	private final Ora10gEsbProject findEsbProjectByQname(String qName, URL serviceURL, Ora10gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			Ora10gWorkspace ora10gWorkspace = (Ora10gWorkspace) workspace;

			for (Project project : ora10gWorkspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_ESB) {
					Ora10gEsbProject esbProject = (Ora10gEsbProject) project;
					Ora10gEsbProject fProject = esbProject.findEsbProjectByQname(qName, serviceURL);
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
