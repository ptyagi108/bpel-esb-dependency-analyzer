package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.osb10g.services.Service;
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

	public static final void main(String[] arg) throws FileNotFoundException, IOException {
		OraSB10gProjectParser parser = new OraSB10gProjectParser();

		// OraSB10gProject project = parser.parseJar(new
		// File("/Users/tomasfrastia/Downloads/varky/sbconfig-alsbcore-var-NSIFAT-20101020150117.jar"));
		OraSB10gProject project = parser.parseJar(new File("/Users/tomasfrastia/Downloads/varky/var-new/sbconfig-osbcore-base-1.0.63.jar"));
		project.toString();// /Users/tomasfrastia/Downloads/varky/sbconfig-alsbcore-var-NSIFAT-20101020150117.jar
	}

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
		if (project.isAsJar()) {
			executeParserJar(project.getFile(), project);
		} else {
			executeParse(project.getFile(), project);
		}
	}

	/**
	 * parse ORacle Service Bus 10g Project as JAR = export
	 * 
	 * @param jar
	 * @return
	 */
	public final OraSB10gProject parseJar(File jar) {
		OraSB10gProject project = new OraSB10gProject(jar, true);
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

				if (entry.getName().equalsIgnoreCase("ExportInfo") || entry.getName().equalsIgnoreCase("_folderdata.LocationData") || entry.getName().equalsIgnoreCase("_projectdata.LocationData")) {
					continue;
				}

				if (entry.isDirectory()) {
					// TODO:problem
				} else {
					Service service = parseServiceInJar(new File(entry.getName()), jarFile.getInputStream(entry));
					if (service != null) {
						service.setFolder(null);
						project.getOraSB10gFolders().addService(service);
					}
				}

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
		OraSB10gProject project = new OraSB10gProject(projectFolder, false);
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
					OraSB10gFolder folder = new OraSB10gFolder(project, file, file.getName(), file.getPath());
					project.getOraSB10gFolders().addFolder(folder);
					parseFolders(project, folder, file);
				} else if (file.isFile()) {
					Service service = parseService(file);
					if (service != null) {
						service.setFolder(null);
						project.getOraSB10gFolders().addService(service);
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
		} else if (name.endsWith(".biz") || name.endsWith(".BusinessService")) {
			try {
				return businessServiceParser.parseBusinessService(file);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO: error
			}
		} else if (name.endsWith(".xq") || name.endsWith(".xquery")) {

		} else if (!name.equals(".project")) {
			// return new UnknownFile(file);
		}
		return null;
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
		} else if (name.endsWith(".biz") || name.endsWith(".BusinessService")) {
			try {
				return businessServiceParser.parseBusinessService(file, fileStream);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO: error
			}
		} else if (name.endsWith(".xq") || name.endsWith(".xquery")) {

		}
		return null;
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
					}
				}
			}
		}
	}

}
