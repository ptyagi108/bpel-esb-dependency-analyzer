package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.esb.BasicEsbNode;
import com.tomecode.soa.oracle10g.esb.EsbGrp;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.oracle10g.esb.EsbSys;
import com.tomecode.soa.process.Project;
import com.tomecode.soa.process.ProjectType;

/**
 * 
 * Parser for bpel and esb workspace
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
			// parse bpel projects
			List<File> listOfFiles = new ArrayList<File>();
			findBpelXmlFiles(workspaceFolder, listOfFiles);
			Workspace workspace = new Workspace(workspaceFolder);
			for (File bpelXml : listOfFiles) {
				workspace.addProject(bpelParser.parseBpelXml(bpelXml));
			}

			// analyze of bpel dependecy
			for (Project service : workspace.getProjects()) {
				if (service.getType() == ProjectType.ORACLE10G_BPEL) {
					BpelProject bpel = (BpelProject) service;
					for (PartnerLinkBinding partnerLinkBinding : bpel.getPartnerLinkBindings()) {
						if (partnerLinkBinding.getDependencyProject() == null) {
							bpelParser.parseBpelByWsdl(partnerLinkBinding);
						}
					}
				}
			}

			// parse esb projects
			List<File> esbProjectFolders = new ArrayList<File>();
			findAllEsbProjectFolders(workspaceFolder, esbProjectFolders);
			for (File esbProjectFolder : esbProjectFolders) {
				workspace.addProject(esbParser.parse(esbProjectFolder));
			}

			// analysis of esb dependency
			for (Project project : workspace.getProjects()) {
				if (project.getType() == ProjectType.ORACLE10G_ESB) {
					EsbProject esbProject = (EsbProject) project;
					// mal by nacitat vsetky projekty atd.
					analyzeEsbDependencies(esbProject.getBasicEsbNodes(), workspace);
				}
			}
			// analysis of unresolved dependency
			anaylizeUnresolvedDependnecy(workspace);

			return workspace;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceParserException(e);
		}

	}

	/**
	 * analyse {@link EsbProject} dependencies
	 * 
	 * @param basicEsbNodes
	 * @param workspace
	 */
	private final void analyzeEsbDependencies(List<BasicEsbNode> basicEsbNodes, Workspace workspace) {
		for (BasicEsbNode basicEsbNode : basicEsbNodes) {
			if (basicEsbNode.get() instanceof EsbSvc) {
				EsbSvc esbSvc = (EsbSvc) basicEsbNode.get();
				URL url = parseWsdlToUrl(esbSvc.getWsdlURL());
				if (url != null) {
					String qName = esbParser.convertWsdlToQname(url);
					Project esbProject = findEsbProjectByQname(qName, url, workspace);
					if (esbProject != null) {
						esbSvc.getOwnerEsbProject().addDependency(esbProject);
					}
				}

			} else if (basicEsbNode.get() instanceof EsbSys) {
				EsbSys esbSys = (EsbSys) basicEsbNode.get();
				analyzeEsbDependencies(esbSys.getBasicEsbNodes(), workspace);
			} else if (basicEsbNode.get() instanceof EsbGrp) {
				EsbGrp esbGrp = (EsbGrp) basicEsbNode.get();
				analyzeEsbDependencies(esbGrp.getBasicEsbNodes(), workspace);
			}
		}
	}

	/**
	 * analyse unresolved dependecy for oracle 10g bpel process
	 * 
	 * @param workspace
	 */
	private final void anaylizeUnresolvedDependnecy(Workspace workspace) {
		for (Project project : workspace.getProjects()) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpelProject = (BpelProject) project;
				for (PartnerLinkBinding partnerLinkBinding : bpelProject.getPartnerLinkBindings()) {
					if (partnerLinkBinding.getDependencyProject() == null) {

						URL urlWsdl = parseWsdlToUrl(partnerLinkBinding.getWsdlLocation());
						String qName = esbParser.convertWsdlToQname(urlWsdl);
						if (urlWsdl != null && qName != null) {
							Project qNameProject = findEsbProjectByQname(qName, urlWsdl, workspace);
							if (qNameProject != null) {
								partnerLinkBinding.setDependencyProject(qNameProject);
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
							BpelProject bpelProject = findBpelProjectForEsb(workspace, url.getFile());
							if (bpelProject != null) {
								esbSvc.getOwnerEsbProject().addDependency(bpelProject);
							}

						}
					}
				}

				// najst vsetky esbserice ktore su unresolved reps. nemaju
				// zavisloti ako bpel a tie parsovat voci bpelu

				// for(esbSvc: listEsbsvc) {
				// findInBpel
				// }
				//
			}
		}
	}

	private final BpelProject findBpelProjectForEsb(Workspace workspace, String url) {
		int index = url.indexOf("?wsdl");
		if (index != -1) {
			url = url.replace("?", ".");

			index = url.lastIndexOf("/");
			if (index != -1) {
				url = url.substring(0, index);
				String processName = url.substring(url.lastIndexOf("/") + 1, index);
				return findBpelByName(workspace, processName);
			}

		}
		return null;
	}

	private final BpelProject findBpelByName(Workspace workspace, String bpelProcessName) {
		for (Project project : workspace.getProjects()) {
			if (project.getType() == ProjectType.ORACLE10G_BPEL) {
				BpelProject bpelProject = (BpelProject) project;
				if (bpelProject.toString().equals(bpelProcessName)) {
					return bpelProject;
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
	 * find {@link EsbProject} by qName
	 * 
	 * @param qName
	 *            //service/@name
	 * @param serviceURL
	 *            wsld from //service/serviceDefinition/wsdlURL
	 * @param workspace
	 * @return
	 */
	private final Project findEsbProjectByQname(String qName, URL serviceURL, Workspace workspace) {
		for (Project project : workspace.getProjects()) {
			if (project.getType() == ProjectType.ORACLE10G_ESB) {
				EsbProject esbProject = (EsbProject) project;
				EsbProject fProject = esbProject.findEsbProjectByQname(qName, serviceURL);
				if (fProject != null) {
					return fProject;
				}
			}
		}
		return null;
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

	/**
	 * find list of files that end with '.esb'
	 * 
	 * @param workspace
	 * @param findedFolders
	 */
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
