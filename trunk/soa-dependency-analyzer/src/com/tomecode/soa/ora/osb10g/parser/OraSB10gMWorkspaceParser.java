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

		File[] files = file.listFiles();
		if (files != null) {
			for (File projectFile : files) {
				if (projectFile.isDirectory()) {
					OraSB10gProject project = projectParser.parse(projectFile);
					workspace.addProject(project);
				}
			}
		}

		analyzeDependnecies(multiWorkspace);
		return multiWorkspace;
	}

	/**
	 * Analyze dependencies in projects
	 * 
	 * @param multiWorkspace
	 */
	private final void analyzeDependnecies(OraSB10gMultiWorkspace multiWorkspace) {
		for (OraSB10gWorkspace workspace : multiWorkspace.getWorkspaces()) {
			for (OraSB10gProject project : workspace.getProjects()) {
				for (Service service : project.getServices()) {

					ServiceDependencies serviceDependencies = service.getServiceDependencies();
					for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
						// name of dependency project
						String depProjectName = serviceDependency.getProjectName();
						if (depProjectName != null) {
							OraSB10gProject depProject = findProjectByName(workspace, depProjectName);
							if (depProject != null) {
								Service targetService = findService(depProject, serviceDependency.getServiceName(), serviceDependency.getRealPath(), serviceDependency.getType());
								if (targetService == null) {
									UnknownService unknownService = serviceDependencies.findUnknownService(depProject, serviceDependency.getServiceName());
									unknownService = (unknownService == null ? new UnknownService(depProject, serviceDependency.getServiceName()) : unknownService);
									serviceDependency.addService(unknownService);
									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), unknownService);
								} else {
									serviceDependency.addService(targetService);
									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
								}
							}
						}
					}
				}
			}
		}
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
		for (OraSB10gProject project : workspace.getProjects()) {
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		return null;
	}

}
