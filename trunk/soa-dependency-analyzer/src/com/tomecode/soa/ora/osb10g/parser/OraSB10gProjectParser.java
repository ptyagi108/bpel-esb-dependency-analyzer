package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

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

	/**
	 * PROXY service parser
	 */
	private final OraSB10gProxyParser proxyParser;

	private final OraSB10gSplitJoinParser splitJoinParser;
	/**
	 * business service parser
	 */
	private final OraSB10gBusinessServiceParser businessServiceParser;

	public OraSB10gProjectParser() {
		proxyParser = new OraSB10gProxyParser();
		splitJoinParser = new OraSB10gSplitJoinParser();
		businessServiceParser = new OraSB10gBusinessServiceParser();
	}

	/**
	 * parse Oracle Service Bus 10g Project
	 * 
	 * @param projectFolder
	 *            project folder
	 * @return
	 */
	public final OraSB10gProject parse(File projectFolder) {
		OraSB10gProject project = new OraSB10gProject(projectFolder);

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

		return project;
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
		} else if (name.endsWith(".biz")) {
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
