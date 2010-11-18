package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownService;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gMultiWorkspace;
import com.tomecode.soa.ora.osb10g.workspace.OraSB10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * Oracle Service Bus 10g Multi Workspace parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class OraSB10gMWorkspaceParser extends AbstractParser {

	/**
	 * parse for Oracle Service Bus 10g projects
	 */
	private final OraSB10gProjectParser projectParser;

	public OraSB10gMWorkspaceParser() {
		projectParser = new OraSB10gProjectParser();
	}

	/**
	 * parse OSB 10G multi workspace
	 * 
	 * @param name
	 *            multi workspace name
	 * @param file
	 *            workspace path
	 * @return
	 */
	public final OraSB10gMultiWorkspace parse(String name, File file) {
		OraSB10gMultiWorkspace multiWorkspace = new OraSB10gMultiWorkspace(name, file);
		OraSB10gWorkspace workspace = new OraSB10gWorkspace(name, file);

		multiWorkspace.addWorkspace(workspace);

		if (containsProjectFiles(file)) {
			OraSB10gProject project = projectParser.parse(file);
			workspace.addProject(project);
		} else {

			File[] files = file.listFiles();
			if (files != null) {
				for (File projectFile : files) {
					if (projectFile.isDirectory()) {
						OraSB10gProject project = projectParser.parse(projectFile);
						workspace.addProject(project);
					}
				}
			}
		}

		analyzeDependnecies(multiWorkspace);
		return multiWorkspace;
	}

	/**
	 * parse {@link MultiWorkspace}
	 * 
	 * @param multiWorkspace
	 */
	public final void parse(MultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {

			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				projectParser.parse(oraSB10gProject);
			}
		}

		analyzeDependnecies((OraSB10gMultiWorkspace) multiWorkspace);
	}

	/**
	 * contains whether project folder contains .settings or .project
	 * 
	 * @param projectFile
	 * @return
	 */
	private final boolean containsProjectFiles(File projectFile) {
		File[] files = projectFile.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory() && f.getName().equalsIgnoreCase(".settings")) {
					return true;
				} else if (f.isFile() && f.getName().equalsIgnoreCase(".project")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Analyze dependencies in projects
	 * 
	 * @param multiWorkspace
	 */
	private final void analyzeDependnecies(OraSB10gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			OraSB10gWorkspace oraSB10gWorkspace = (OraSB10gWorkspace) workspace;

			for (Project project : oraSB10gWorkspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;

				for (Service service : oraSB10gProject.getServices()) {

					ServiceDependencies serviceDependencies = service.getServiceDependencies();

					if (service.toString().equals("AQ_processBatch")) {
						toString();
					}

					for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
						// name of dependency project
						String depProjectName = serviceDependency.getProjectNameFromRefPath();
						if (depProjectName != null) {
							OraSB10gProject depProject = findProjectByName(oraSB10gWorkspace, depProjectName);
							if (depProject != null) {
								Service targetService = findService(depProject, serviceDependency.getServiceName(), serviceDependency.getRealPath(), serviceDependency.getType());
								if (targetService == null) {
//									UnknownService unknownService = serviceDependencies.findUnknownService(depProject, serviceDependency.getServiceName());
//									unknownService = (unknownService == null ? new UnknownService(depProject, serviceDependency.getServiceName()) : unknownService);
//									serviceDependency.addService(unknownService);
//									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), unknownService);
									addUnknowService(serviceDependencies, depProject, serviceDependency, service);									
								} else {
									serviceDependency.addService(targetService);
									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
								}
							} else {
								addUnknowService(serviceDependencies, depProject, serviceDependency, service);
							}
						}
					}
				}
			}
		}
	}

	private final void addUnknowService(ServiceDependencies serviceDependencies,OraSB10gProject depProject, ServiceDependency serviceDependency, Service service){
		UnknownService unknownService = serviceDependencies.findUnknownService(depProject, serviceDependency.getServiceName());
		unknownService = (unknownService == null ? new UnknownService(depProject, serviceDependency.getServiceName()) : unknownService);
		serviceDependency.addService(unknownService);
		service.getActivityDependency().addDependecy(serviceDependency.getActivity(), unknownService);
	}
	/**
	 * find {@link Service} in project by parameter
	 * 
	 * @param project
	 * @param serviceName
	 * @param realPath
	 * @param type
	 * @return
	 */
	private final Service findService(OraSB10gProject project, String serviceName, String realPath, ServiceDependencyType type) {
		for (Service service : project.getServices()) {
			if (service.getType() == type) {
				if (service.getName().equals(serviceName)) {
					if (realPath == null) {
						return service;
					} else {
						if (service.getFolder().getPath().equals(realPath)) {
							return service;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * find {@link Project} by name in {@link Workspace}
	 * 
	 * @param workspace
	 * @param projectName
	 * @return
	 */
	private final OraSB10gProject findProjectByName(OraSB10gWorkspace workspace, String projectName) {
		for (Project project : workspace.getProjects()) {
			if (project.getName().equals(projectName)) {
				return (OraSB10gProject) project;
			}
		}
		return null;
	}

	/**
	 * parse new project
	 * 
	 * @param asFolder
	 * @param path
	 * @return
	 */
	public final OraSB10gProject addNewProject(boolean asFolder, File path) {
		if (asFolder) {
			return projectParser.parse(path);
		}
		return projectParser.parseJar(path);
	}

}
