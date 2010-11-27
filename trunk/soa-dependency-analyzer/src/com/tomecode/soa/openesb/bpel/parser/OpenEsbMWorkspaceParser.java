package com.tomecode.soa.openesb.bpel.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.bpel.activity.OnMessage;
import com.tomecode.soa.openesb.bpel.activity.PartnerLink;
import com.tomecode.soa.openesb.bpel.dependencies.BpelActivityDependency;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.ora.suite10g.activity.Invoke;
import com.tomecode.soa.ora.suite10g.activity.Receive;
import com.tomecode.soa.ora.suite10g.activity.Reply;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * 
 * This parser parse all workspace and projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class OpenEsbMWorkspaceParser extends AbstractParser {

	private static final String BPEL_PROJECT_TYPE = "org.netbeans.modules.bpel.project";

	/**
	 * parse for BPEL processes
	 */
	private final OpenEsbBpelParser bpelParser;

	/**
	 * parse for WSDL file
	 */
	private final OpenEsbWsdlParser wsdlParser;

	/**
	 * Constructor
	 */
	public OpenEsbMWorkspaceParser() {
		this.bpelParser = new OpenEsbBpelParser();
		this.wsdlParser = new OpenEsbWsdlParser();
	}

	/**
	 * parse {@link OpenEsbMultiWorkspace}
	 * 
	 * @param name
	 * @param path
	 *            multi-workspace name
	 * @return
	 * @throws ServiceParserException
	 */
	public final OpenEsbMultiWorkspace parse(String name, File path) throws ServiceParserException {
		if (!path.exists()) {
			throw new ServiceParserException("File not exists: " + path, false);
		}
		// TODO: missing workspace name
		OpenEsbMultiWorkspace multiWorkspace = new OpenEsbMultiWorkspace(name, path);

		List<File> projects = new ArrayList<File>();
		findAllProjects(path, projects);

		OpenEsbWorkspace workspace = new OpenEsbWorkspace(path.getName(), path);
		multiWorkspace.addWorkspace(workspace);

		// filter only BPEL projects
		List<OpenEsbBpelProject> esbBpelProjects = filterBpelProjects(projects);
		parseProjectSources(workspace, esbBpelProjects);

		return multiWorkspace;
	}

	/**
	 * parse all BPEL files
	 * 
	 * @param workspace
	 * @param esbBpelProjects
	 *            list of filtered BPEL projects
	 */
	private final void parseProjectSources(OpenEsbWorkspace workspace, List<OpenEsbBpelProject> esbBpelProjects) {
		for (OpenEsbBpelProject project : esbBpelProjects) {
			List<File> bpelFiles = new ArrayList<File>();

			File srcFolder = new File(project.getFile() + File.separator + "src");
			filterBpelProcess(srcFolder, bpelFiles);

			for (File bpelFile : bpelFiles) {
				try {
					project.addBpelProcess(bpelParser.parseBpel(bpelFile));
				} catch (ServiceParserException e) {
					// TODO:error
					e.printStackTrace();
				}
			}

			List<File> wsdlFiles = new ArrayList<File>();
			filterWsldFiles(srcFolder, wsdlFiles);
			for (File wsdlFile : wsdlFiles) {
				project.addWsdlFile(wsdlParser.parseWsdl(wsdlFile));
			}

			project.setWorkspace(workspace);
			workspace.addProject(project);
		}

		linkingWsdlWithPartnerLinks(esbBpelProjects);
		analyzeDependencies(esbBpelProjects);
		linkingProcessDependencies(esbBpelProjects);
	}

	private final void linkingProcessDependencies(List<OpenEsbBpelProject> projects) {
		for (OpenEsbBpelProject project : projects) {
			for (OpenEsbBpelProcess process : project.getProcesses()) {
				for (BpelActivityDependency dependency : process.getActivityDependencies()) {
					PartnerLink partnerLink = null;
					if (dependency.getActivity() instanceof Receive) {
						Receive receive = (Receive) dependency.getActivity();
						partnerLink = process.findPartnerLink(receive.getPartnerLink());
					} else if (dependency.getActivity() instanceof Invoke) {
						Invoke invoke = (Invoke) dependency.getActivity();
						partnerLink = process.findPartnerLink(invoke.getPartnerLink());
					} else if (dependency.getActivity() instanceof Reply) {
						Reply reply = (Reply) dependency.getActivity();
						partnerLink = process.findPartnerLink(reply.getPartnerLink());
					} else if (dependency.getActivity() instanceof OnMessage) {
						OnMessage onMessage = (OnMessage) dependency.getActivity();
						partnerLink = process.findPartnerLink(onMessage.getPartnerLink());
					}
					dependency.addDependencyProcess(partnerLink.getDependenciesProcesses().get(0));
				}
			}
		}
	}

	/**
	 * linking partnerlink with WSDL files
	 * 
	 * @param projects
	 */
	private final void linkingWsdlWithPartnerLinks(List<OpenEsbBpelProject> projects) {
		for (OpenEsbBpelProject project : projects) {
			for (OpenEsbBpelProcess bpelProcess : project.getProcesses()) {
				for (PartnerLink partnerLink : bpelProcess.getPartnerLinks()) {
					Wsdl wsdl = project.findWsdlByPartnerLinkType(partnerLink.getPartnerLinkType());
					if (wsdl != null) {
						partnerLink.setWsdl(wsdl);
					} else {
						wsdl = findWsdlInOtherProjects(projects, partnerLink.getPartnerLinkType());
					}

				}
			}
		}
	}

	/**
	 * analyze of dependencies
	 * 
	 * @param projects
	 */
	private final void analyzeDependencies(List<OpenEsbBpelProject> projects) {
		for (OpenEsbBpelProject project : projects) {
			for (OpenEsbBpelProcess bpelProcess : project.getProcesses()) {
				for (PartnerLink partnerLink : bpelProcess.getPartnerLinks()) {
					// TODO: co ak je null
					Wsdl wsdl = partnerLink.getWsdl();
					if (wsdl != null) {
						List<OpenEsbBpelProcess> dependenciesProcess = findWsldRefInOtherProcess(projects, wsdl);
						for (OpenEsbBpelProcess depProcess : dependenciesProcess) {
							// if (!bpelProcess.equals(depProcess)) {
							partnerLink.addDependency(depProcess);

							// }
						}
					}
				}
			}
		}
	}

	/**
	 * find reference for all dependencies node
	 * 
	 * @param projects
	 * @param wsdl
	 * @return
	 */
	private final List<OpenEsbBpelProcess> findWsldRefInOtherProcess(List<OpenEsbBpelProject> projects, Wsdl wsdl) {
		List<OpenEsbBpelProcess> bpelProcesses = new ArrayList<OpenEsbBpelProcess>();
		for (OpenEsbBpelProject project : projects) {
			for (OpenEsbBpelProcess process : project.getProcesses()) {
				for (PartnerLink partnerLink : process.getPartnerLinks()) {
					if (wsdl.equals(partnerLink.getWsdl())) {
						bpelProcesses.add(process);
					}
				}
			}
		}

		return bpelProcesses;
	}

	/**
	 * find WSDL in others projects
	 * 
	 * @param projects
	 * @param partnerLinkTypeName
	 * @return
	 */
	private final Wsdl findWsdlInOtherProjects(List<OpenEsbBpelProject> projects, String partnerLinkTypeName) {
		for (OpenEsbBpelProject project : projects) {
			Wsdl wsdl = project.findWsdlByPartnerLinkType(partnerLinkTypeName);
			if (wsdl != null) {
				return wsdl;
			}
		}
		return null;
	}

	/**
	 * filter all files with extensions .bpel
	 * 
	 * @param sourceFolder
	 * @param bpelFiles
	 *            list of BPEL files
	 */
	private final void filterBpelProcess(File sourceFolder, List<File> bpelFiles) {
		File[] files = sourceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".bpel")) {
					bpelFiles.add(file);
				} else if (file.isDirectory()) {
					filterBpelProcess(file, bpelFiles);
				}
			}
		}
	}

	/**
	 * find all files with extensions .wsdl
	 * 
	 * @param sourceFolder
	 * @param wsdlFiles
	 */
	private final void filterWsldFiles(File sourceFolder, List<File> wsdlFiles) {
		File[] files = sourceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".wsdl")) {
					wsdlFiles.add(file);
				} else if (file.isDirectory()) {
					filterBpelProcess(file, wsdlFiles);
				}
			}
		}
	}

	/**
	 * filter only BPEL projects from list of NETBEANSE projects
	 * 
	 * @param projectXmls
	 *            list of NETBEANS projects
	 * @return list of Open ESB - BPEL projects
	 */
	private final List<OpenEsbBpelProject> filterBpelProjects(List<File> projectXmls) {
		List<OpenEsbBpelProject> projects = new ArrayList<OpenEsbBpelProject>();
		for (File projectXml : projectXmls) {
			try {
				OpenEsbBpelProject project = parseNProjectXml(parseXml(projectXml), projectXml);
				if (project != null) {
					projects.add(project);
				}
			} catch (ServiceParserException e) {
				// TODO: error
				e.printStackTrace();
			}
		}

		return projects;
	}

	/**
	 * parse project in
	 * 
	 * @param projectXml
	 * @return
	 */
	private final OpenEsbBpelProject parseNProjectXml(Element eProject, File projectXml) {
		if (!BPEL_PROJECT_TYPE.equals(eProject.elementTextTrim("type"))) {
			return null;
		}

		Element eConfiguration = eProject.element("configuration");
		if (eConfiguration != null && eConfiguration.element("data") != null) {
			String projectName = eConfiguration.element("data").elementTextTrim("name");

			if (projectName == null) {
				projectName = projectXml.getParentFile().getParentFile().getName();
			}
			return new OpenEsbBpelProject(projectName, projectXml.getParentFile().getParentFile());
		}

		return null;
	}

	/**
	 * find all SRC folder
	 * 
	 * @param workspaceFolder
	 * @param projects
	 *            list contains projects
	 */
	private final void findAllProjects(File workspaceFolder, List<File> projects) {
		File[] files = workspaceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().equalsIgnoreCase("project.xml") && file.getParentFile() != null && file.getParentFile().getName().equals("nbproject")) {
					projects.add(file);
				} else if (file.isDirectory()) {
					findAllProjects(file, projects);
				}
			}
		}
	}
}
