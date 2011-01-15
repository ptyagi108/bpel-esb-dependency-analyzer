package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.osb10g.services.ResourceFile;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownFile;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * 
 * Project parser for Oracle Service Bus 10g
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class OraSB10gProjectParser {

	/**
	 * PROXY service parser
	 */
	private final OraSB10gProxyParser proxyParser;

	private final OraSB10gSplitJoinParser splitJoinParser;
	/**
	 * business service parser
	 */
	private final OraSB10gBusinessServiceParser businessServiceParser;

	/**
	 * Constructor - initialize parsers
	 */
	public OraSB10gProjectParser() {
		proxyParser = new OraSB10gProxyParser();
		splitJoinParser = new OraSB10gSplitJoinParser();
		businessServiceParser = new OraSB10gBusinessServiceParser();
	}

	public final void parse(OraSB10gProject project) {
		if (project.isFolder()) {
			executeParse(project.getFile(), project);
		} else {
			executeParserJar(project.getFile(), project);
		}
	}

	/**
	 * parse ORacle Service Bus 10g Project as JAR = export
	 * 
	 * @param jar
	 * @return
	 */
	public final OraSB10gProject parseJar(File jar) {
		OraSB10gProject project = new OraSB10gProject(jar, false);
		executeParserJar(jar, project);
		return project;
	}

	/**
	 * Internal method for parsing OSB 10g project as JAR or from JAR file
	 * 
	 * @param jar
	 * @param project
	 */
	private final void executeParserJar(File jar, OraSB10gProject project) {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jar);

			JarInputStream inputStream = new JarInputStream(new FileInputStream(jarFile.getName()));
			JarEntry entry = null;
			while ((entry = inputStream.getNextJarEntry()) != null) {

				if (entry.getName().equalsIgnoreCase("ExportInfo") || entry.getName().contains("_folderdata.LocationData") || entry.getName().contains("_projectdata.LocationData")) {
					continue;
				}

				OraSB10gFolder folder = null;
				int index = entry.getName().lastIndexOf("/");
				if (index != -1) {
					String path = entry.getName().substring(0, index);
					folder = project.getFolders().findAndCreate(project, path, jar);
				} else {
					System.err.print("******************");
				}
				Service service = parseServiceInJar(new File(entry.getName()), jarFile.getInputStream(entry));
				if (service != null) {
					service.setFolder(folder);

					folder.addService(service);
					project.getFolders().addToAllServices(service);
				}
				// }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * parse Oracle Service Bus 10g Project
	 * 
	 * @param projectFolder
	 *            project folder
	 * @return
	 */
	public final OraSB10gProject parse(File projectFolder) {
		OraSB10gProject project = new OraSB10gProject(projectFolder, true);
		executeParse(projectFolder, project);
		return project;
	}

	/**
	 * internal method for parsing OSB 10g project
	 * 
	 * @param projectFolder
	 * @param project
	 */
	private final void executeParse(File projectFolder, OraSB10gProject project) {
		File[] files = projectFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory() && !file.getName().equals(".settings")) {

					String path = file.getPath().replace(project.getFile().toString(), "");
					if (path.startsWith(File.separator)) {
						path = path.substring(1);
					}

					OraSB10gFolder folder = new OraSB10gFolder(project, file, path, file.getName());
					project.getFolders().addFolder(folder);
					parseFolders(project, folder, file);
				} else if (file.isFile()) {
					Service service = parseService(file);
					if (service != null) {
						service.setFolder(null);
						project.getFolders().addService(service);
						project.getFolders().addToAllServices(service);
					}
				}
			}
		}

	}

	/**
	 * parse service or file
	 * 
	 * @param file
	 * @return
	 */
	private final Service parseService(File file) {
		String name = file.getName().toLowerCase();
		if (name.endsWith(".proxy") || name.endsWith(".proxyservice")) {
			try {
				return proxyParser.parseProxy(file);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO : error
			}

		} else if (name.endsWith(".flow")) {
			try {
				return splitJoinParser.parseSplitJoin(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (name.endsWith(".biz") || name.endsWith(".businessservice")) {
			try {
				return businessServiceParser.parseBusinessService(file);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO: error
			}
		} else if (name.equals(".project")) {
			return null;
		}

		return parseResourceFile(file);
	}

	/**
	 * parse resource file
	 * 
	 * @param file
	 * @return
	 */
	private final Service parseResourceFile(File file) {
		String name = file.getName().toLowerCase();
		if (name.endsWith(".xq") || name.endsWith(".xquery")) {
			return new ResourceFile(file, ServiceDependencyType.XQUERY);
		} else if (name.endsWith(".wsdl")) {
			return new ResourceFile(file, ServiceDependencyType.WSDL);
		} else if (name.endsWith(".xmlschema") || name.endsWith(".xsd")) {
			return new ResourceFile(file, ServiceDependencyType.XML_SCHEMA);
		} else if (name.endsWith(".serviceaccount") || name.endsWith(".sa")) {
			return new ResourceFile(file, ServiceDependencyType.SERVICE_ACCOUNT);
		} else if (name.endsWith(".archive")) {
			return new ResourceFile(file, ServiceDependencyType.ARCHIVE);
		} else if (name.endsWith(".skp")) {
			return new ResourceFile(file, ServiceDependencyType.SKP);
		}
		return new UnknownFile(file);
	}

	/**
	 * parse services in jar
	 * 
	 * @param file
	 * @param fileStream
	 * @return
	 */
	private final Service parseServiceInJar(File file, InputStream fileStream) {
		String name = file.getName().toLowerCase();
		if (name.endsWith(".proxy") || name.endsWith(".proxyservice")) {
			try {
				return proxyParser.parseProxy(file, fileStream);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO : error
			}

		} else if (name.endsWith(".flow")) {
			try {
				return splitJoinParser.parseSplitJoin(file, fileStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (name.endsWith(".biz") || name.endsWith("businessservice")) {
			try {
				return businessServiceParser.parseBusinessService(file, fileStream);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO: error
			}
		}
		return parseResourceFile(file);
	}

	/**
	 * parse all folders and files in project
	 * 
	 * @param root
	 * @param parentFile
	 */
	private final void parseFolders(OraSB10gProject project, OraSB10gFolder root, File parentFile) {
		File[] files = parentFile.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory() && !file.getName().equals(".settings")) {
					OraSB10gFolder folder = new OraSB10gFolder(project, file, root.getPath() + "/" + file.getName(), file.getName());
					root.addFolder(folder);
					parseFolders(project, folder, file);
				} else {
					Service service = parseService(file);
					if (service != null) {
						service.setFolder(root);
						root.addService(service);
						project.getFolders().addToAllServices(service);
					}
				}
			}
		}
	}

}
