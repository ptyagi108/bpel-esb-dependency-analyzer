package com.tomecode.soa.openesb.bpel.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.openesb.workspace.OpenEsbMultiWorkspace;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * 
 * This parser parse all workspace and projects
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbMWorkspaceParser extends AbstractParser {

	private static final String BPEL_PROJECT_TYPE = "org.netbeans.modules.bpel.project";

	/**
	 * parse for BPEL processes
	 */
	private final OpenEsbBpelParser bpelParser;

	public OpenEsbMWorkspaceParser() {
		this.bpelParser = new OpenEsbBpelParser();
	}

	/**
	 * parse {@link OpenEsbMultiWorkspace}
	 * 
	 * @param workspaceFolder
	 * @param mWorkspaceName
	 *            multi-workspace name
	 * @return
	 * @throws ServiceParserException
	 */
	public final OpenEsbMultiWorkspace parse(File workspaceFolder, String mWorkspaceName) throws ServiceParserException {
		if (!workspaceFolder.exists()) {
			throw new ServiceParserException("File not exists: " + workspaceFolder, false);
		}
		// TODO: missing workspace name
		OpenEsbMultiWorkspace multiWorkspace = new OpenEsbMultiWorkspace(mWorkspaceName, workspaceFolder);

		List<File> projects = new ArrayList<File>();
		findAllProjects(workspaceFolder, projects);

		OpenEsbWorkspace workspace = new OpenEsbWorkspace(workspaceFolder.getName(), workspaceFolder);
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
			filterBpelProcess(new File(project.getFile() + File.separator + "src"), bpelFiles);

			for (File bpelFile : bpelFiles) {
				try {
					project.addBpelProcess(bpelParser.parseBpel(bpelFile));
				} catch (ServiceParserException e) {
					e.printStackTrace();
				}
			}
			project.setWorkspace(workspace);
			workspace.addProject(project);
		}
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
	 * filter only BPEL projects from list of NETBEANSE projects
	 * 
	 * @param projectXmls
	 *            list of NETBEANS projects
	 * @return list of Open ESB - BPEL projects
	 */
	private final List<OpenEsbBpelProject> filterBpelProjects(List<File> projectXmls) {
		List<OpenEsbBpelProject> esbBpelProjects = new ArrayList<OpenEsbBpelProject>();
		for (File projectXml : projectXmls) {
			try {
				OpenEsbBpelProject project = parseNProjectXml(parseXml(projectXml), projectXml);
				if (project != null) {
					esbBpelProjects.add(project);
				}
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}

		return esbBpelProjects;
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
			OpenEsbBpelProject project = new OpenEsbBpelProject(projectName, projectXml.getParentFile().getParentFile());

			return project;
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
