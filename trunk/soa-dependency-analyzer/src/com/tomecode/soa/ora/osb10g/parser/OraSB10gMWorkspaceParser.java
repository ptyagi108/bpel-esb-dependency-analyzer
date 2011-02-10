package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.util.List;

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
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
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
	 * parse new multi-workspace
	 * 
	 * @param multiWorkspace
	 * @param workspacePaths
	 */
	public final void parse(OraSB10gMultiWorkspace multiWorkspace, List<String> workspacePaths) {
		for (String path : workspacePaths) {
			File f = new File(path);
			multiWorkspace.addWorkspace(parse2(f));
		}

		analyzeDependnecies(multiWorkspace);
	}

	private final OraSB10gWorkspace parse2(File file) {
		OraSB10gWorkspace workspace = new OraSB10gWorkspace(file.getName(), file);

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

		return workspace;
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
	public final void analyzeDependnecies(OraSB10gMultiWorkspace multiWorkspace) {
		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			OraSB10gWorkspace oraSB10gWorkspace = (OraSB10gWorkspace) workspace;

			for (Project project : oraSB10gWorkspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;

				// analyze dependencies in jar project
				if (!oraSB10gProject.isFolder()) {

					for (Service service : oraSB10gProject.getServices()) {

						ServiceDependencies serviceDependencies = service.getServiceDependencies();
						for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
							// find dependency in selected project
							Service targetService = findServiceInJarProject(oraSB10gProject, serviceDependency);
							if (targetService != null) {
								serviceDependency.addService(targetService);
								service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
							} else {

								// find dependency in others projects

								targetService = findServiceByPath(multiWorkspace, serviceDependency);
								if (targetService != null) {
									serviceDependency.addService(targetService);
									service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
								} else {
									addUnknowService(serviceDependencies, oraSB10gProject, serviceDependency, service);
								}
							}
						}

					}

				} else {

					// analyze dependencies in project as folder (workspace)
					for (Service service : oraSB10gProject.getServices()) {

						ServiceDependencies serviceDependencies = service.getServiceDependencies();
						for (ServiceDependency serviceDependency : serviceDependencies.getDependnecies()) {
							// name of dependency project
							String depProjectName = serviceDependency.getProjectNameFromRefPath();
							if (depProjectName != null) {
								OraSB10gProject depProject = findProjectByName(oraSB10gWorkspace, depProjectName);
								if (depProject != null) {
									Service targetService = findService(depProject, serviceDependency.getServiceName(), serviceDependency.getRealPath(), serviceDependency.getType());
									if (targetService == null) {
										addUnknowService(serviceDependencies, depProject, serviceDependency, service);
									} else {
										serviceDependency.addService(targetService);
										service.getActivityDependency().addDependecy(serviceDependency.getActivity(), targetService);
									}
								} else {
									Service targetService = findServiceByPath(multiWorkspace, serviceDependency);
									if (targetService == null) {
										addUnknowService(serviceDependencies, depProject, serviceDependency, service);
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
	}

	/**
	 * find service by name in all projects
	 * 
	 * @param multiWorkspace
	 * @param serviceDependency
	 * @return
	 */
	private final Service findServiceByPath(OraSB10gMultiWorkspace multiWorkspace, ServiceDependency serviceDependency) {

		String[] paths = serviceDependency.toString().split("/");
		String serviceName = paths[paths.length - 1];
		String serviceName2 = serviceName.replaceAll(" ", "_");

		for (Workspace workspace : multiWorkspace.getWorkspaces()) {
			for (Project project : workspace.getProjects()) {
				OraSB10gProject oraSB10gProject = (OraSB10gProject) project;
				for (Service service : oraSB10gProject.getServices()) {

					if (service.getType().equals(serviceDependency.getType())) {

						if (service.getName().equals(serviceName) || service.getName().replaceAll(" ", "_").equals(serviceName2)) {

							try {
								if (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName())) {
									return service;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}

				}
			}

		}
		return null;
	}

	/**
	 * find service in project (from jar)
	 * 
	 * @param project
	 * @param serviceDependency
	 * @return
	 */
	private final Service findServiceInJarProject(OraSB10gProject project, ServiceDependency serviceDependency) {

		// String serviceDependencyName = serviceDependency.getServiceName();
		// String sserviceDependencyName2 =
		// serviceDependencyName.replaceAll(" ", "_");

		for (Service service : project.getServices()) {
			if (service.getType() == serviceDependency.getType()) {

				// String serviceName = service.getName().replace(" ", "_");

				String serviceDepName = serviceDependency.getServiceName();
				if (service.getName().equals(serviceDepName) || service.getName().replaceAll(" ", "_").equals(serviceDepName.replaceAll(" ", "_"))) {
					String refPath = serviceDependency.getRefPath();
					if (refPath == null) {
						return service;
					} else {

						if (service.getFolder() != null) {
							if (service.getFolder().getPath().equals(serviceDependency.getRefPathWithoutServiceName())) {
								return service;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private final void addUnknowService(ServiceDependencies serviceDependencies, OraSB10gProject depProject, ServiceDependency serviceDependency, Service service) {
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

		String serviceName2 = serviceName.replaceAll(" ", "_");
		for (Service service : project.getServices()) {
			if (service.getType() == type) {

				try {
					if (service.getOrginalName().equals(serviceName) || service.getOrginalName().replaceAll(" ", "_").equals(serviceName2)) {
						if (realPath == null) {
							return service;
						} else {
							if (service.getFolder().getPath().equals(realPath)) {
								return service;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
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
