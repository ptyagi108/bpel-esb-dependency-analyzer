package com.tomecode.soa.dependency.analyzer.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.tomecode.soa.dependency.analyzer.gui.FrmError;
import com.tomecode.soa.dependency.analyzer.settings.RecentFile.RecentFileType;

/**
 * 
 * Settings manager is a simple object for save/edit settings
 * 
 * @author Tomas Frastia
 * 
 */
public final class SettingsManager {

	private ReloadRecentMenuListener reloadRecentMenuListener;

	/**
	 * singleton instance
	 */
	private static SettingsManager me;

	private final SAXReader saxReader;

	/**
	 * list of parsed {@link RecentFile}
	 */
	private final List<RecentFile> recentFiles;

	/**
	 * Constructor
	 */
	private SettingsManager() {
		saxReader = new SAXReader();
		recentFiles = new ArrayList<RecentFile>();
	}

	/**
	 * read recent files
	 * 
	 * @return
	 */
	public final List<RecentFile> getRecentFiles() {
		File userHome = createSettingsXml();
		if (userHome != null) {

			try {
				Document document = saxReader.read(userHome);
				parseSettings(document.getRootElement());
			} catch (Exception e) {
				if (!e.getMessage().contains("Error on line 1 of document")) {
					FrmError.showMe("Failed read settings file", e);
				}
			}
		}

		return recentFiles;
	}

	/**
	 * parse settings xml
	 * 
	 * @param rootElement
	 */
	private final void parseSettings(Element rootElement) {
		recentFiles.clear();
		if (rootElement != null) {
			if (rootElement.getName().equals("settings")) {
				Element eRecentFiles = rootElement.element("recentFiles");
				if (eRecentFiles != null) {
					parseRecentFiles(eRecentFiles);
				}
			}
		}
	}

	/**
	 * parse recent files
	 * 
	 * @param eRecentFiles
	 */
	private final void parseRecentFiles(Element eRecentFiles) {
		List<?> eRecentFilesList = eRecentFiles.elements("recentFile");
		if (eRecentFilesList != null) {
			for (Object o : eRecentFilesList) {
				Element e = (Element) o;
				if (e.getName().equals("recentFile")) {
					recentFiles.add(new RecentFile(e.attributeValue("name"), e.attributeValue("type"), new File(e.getTextTrim())));
				}
			}
		}
	}

	/**
	 * create settings file is not exists
	 * 
	 * @param fileName
	 * @return
	 */
	private final File createSettingsXml() {
		File file = new File(System.getProperty("user.home") + File.separator + "bed.settings.xml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				FrmError.showMe("failed create setting file [" + file + "]", e);
				return null;
			}
		}
		return file;
	}

	/**
	 * set new type and change order for {@link RecentFile}
	 * 
	 * @param type
	 * @param index
	 */
	public final void addRecentFile(RecentFileType type, int index) {
		RecentFile recentFile = recentFiles.remove(index);
		recentFile.setType(type);
		recentFiles.add(0, recentFile);
		writeRecentFiles(recentFiles);
		reloadRecentMenuListener.changesInRecentFiles();
	}

	/**
	 * add new {@link RecentFile}
	 * 
	 * @param type
	 * @param name
	 * @param file
	 */
	public final void addRecentFile(String name, RecentFileType type, File file) {
		RecentFile recentFile = new RecentFile(name, type, file);
		recentFiles.add(0, recentFile);
		writeRecentFiles(recentFiles);
		reloadRecentMenuListener.changesInRecentFiles();
	}

	/**
	 * write list of {@link RecentFile} to setting file
	 * 
	 * @param recentFiles
	 */
	private final void writeRecentFiles(List<RecentFile> recentFiles) {
		Document document = null;
		try {
			document = saxReader.read(createSettingsXml());
		} catch (Exception e) {
			if (!e.getMessage().contains("Error on line 1 of document")) {
				FrmError.showMe("Failed read settings file", e);
			}
		}

		if (document == null) {
			document = DocumentHelper.createDocument();
			document.addElement("settings");
		}

		Element rootElement = document.getRootElement();
		writeRecentFiles(rootElement);
		XMLWriter output = null;
		try {
			output = new XMLWriter(new FileWriter(createSettingsXml()));
			output.write(document);
		} catch (Exception e) {
			FrmError.showMe("failed save settings file", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					FrmError.showMe("failed save settings file", e);
				}
			}
		}

	}

	/**
	 * write recnet files
	 * 
	 * @param rootElement
	 */
	private final void writeRecentFiles(Element rootElement) {
		Element eRecentFiles = rootElement.element("recentFiles");
		if (eRecentFiles != null) {
			rootElement.remove(eRecentFiles);
		}
		eRecentFiles = DocumentHelper.createElement("recentFiles");
		rootElement.add(eRecentFiles);

		for (RecentFile recentFile : recentFiles) {
			Element eRecentFile = DocumentHelper.createElement("recentFile");
			eRecentFile.addAttribute("name", recentFile.getName());
			eRecentFile.addAttribute("type", recentFile.getType().getXmlValue());
			eRecentFile.addText(recentFile.getFile().getPath());
			eRecentFiles.add(eRecentFile);
		}
	}

	public final RecentFile getRecentFile(int index) {
		List<RecentFile> recentFiles = getRecentFiles();
		if (index >= recentFiles.size()) {
			return null;
		}
		return recentFiles.get(index);
	}

	public final void setReloadRecentMenuListener(ReloadRecentMenuListener reloadRecentMenuListener) {
		this.reloadRecentMenuListener = reloadRecentMenuListener;
	}

	/**
	 * get singleton instance
	 * 
	 * @return
	 */
	public final static SettingsManager getInstance() {

		if (me == null) {
			me = new SettingsManager();
		}
		return me;
	}
}
