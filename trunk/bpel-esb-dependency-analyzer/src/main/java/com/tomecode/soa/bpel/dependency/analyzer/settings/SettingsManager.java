package com.tomecode.soa.bpel.dependency.analyzer.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.dependency.analyzer.gui.FrmError;

/**
 * 
 * Settings manager is a simple object for save/edit settings
 * 
 * @author Tomas Frastia
 * 
 */
public final class SettingsManager {

	private SettingsManager() {

	}

	/**
	 * read recent files
	 * 
	 * @return
	 */
	public static final List<RecentFile> getRecentFiles() {
		List<RecentFile> files = new ArrayList<RecentFile>();
		File userHome = createRecentFile();
		if (userHome != null) {

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(userHome));
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] strings = line.split("::");
					if (strings.length == 3) {
						files.add(new RecentFile(strings[0], strings[1], new File(strings[2])));
					} else if (strings.length == 2) {
						files.add(new RecentFile(strings[0], "W", new File(strings[1])));
					} else {
						files.add(new RecentFile(strings[0], "W", new File(strings[0])));
					}

				}

			} catch (IOException e) {
				FrmError.showMe(e.getMessage(), e);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
					}
				}
			}
		}

		return files;
	}

	/**
	 * create settings file is not exists
	 * 
	 * @param fileName
	 * @return
	 */
	private final static File createRecentFile() {
		File file = new File(System.getProperty("user.home") + File.separator + "bed.settings");
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

	public final static void addRecentFile(String name, String type, File file) {
		List<RecentFile> recentFiles = getRecentFiles();
		if (!containsRecentFiles(name, file, recentFiles)) {
			recentFiles.add(0, new RecentFile(name, type, file));
		}
		writeRecentFiles(recentFiles);
	}

	/**
	 * write list of {@link RecentFile} to setting file
	 * 
	 * @param recentFiles
	 */
	private static final void writeRecentFiles(List<RecentFile> recentFiles) {

		BufferedWriter bw = null;
		try {
			File file = createRecentFile();
			if (file != null) {
				bw = new BufferedWriter(new FileWriter(createRecentFile()));
				for (RecentFile recentFile : recentFiles) {
					bw.write(recentFile.toString() + "\n");
				}
			}

		} catch (IOException e) {
			FrmError.showMe(e.getMessage(), e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static final boolean containsRecentFiles(String name, File file, List<RecentFile> recentFiles) {
		for (RecentFile recentFile : recentFiles) {
			if (recentFile.equals(name, file)) {
				return true;
			}
		}
		return false;
	}
}
