package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.ora.osb10g.services.OraSB10gFolder;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.UnknownFile;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * 
 * Parser of Oracle Service Bus 10g
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gProjectParser {

	/**
	 * proxy service parser
	 */
	private final OraSB10gProxyParser proxyParser;

	/**
	 * business service parser
	 */
	private final OraSB10gBusinessServiceParser businessServiceParser;

	public OraSB10gProjectParser() {
		proxyParser = new OraSB10gProxyParser();
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
		if (file.getName().endsWith(".proxy")) {
			try {
				return proxyParser.parseProxy(file);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO : error
			}

			// && (file.getName().endsWith(".proxy") ||
			// file.getName().endsWith(".biz") ||
			// file.getName().endsWith(".xq"))
		} else if (file.getName().endsWith(".biz")) {
			try {
				return businessServiceParser.parseBusinessService(file);
			} catch (ServiceParserException e) {
				e.printStackTrace();
				// TODO: error
			}
		} else if (file.getName().endsWith(".xq")) {

		} else if (!file.getName().equals(".project")) {
			return new UnknownFile(file);
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
					OraSB10gFolder folder = new OraSB10gFolder(project, file, root.getPath() + "\\" + file.getName(), file.getName());
					root.addFolder(folder);
					parseFolders(project, folder, file);
				} else {
					Service service = parseService(file);
					if (service != null) {
						root.addService(service);
					}
				}
			}
		}
	}
}
