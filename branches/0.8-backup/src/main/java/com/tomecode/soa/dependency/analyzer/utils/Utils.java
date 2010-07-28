package com.tomecode.soa.dependency.analyzer.utils;

/**
 * 
 * utilities class
 * 
 * @author Tomas Frastia
 * 
 */
public final class Utils {

	/**
	 * open url in default browser
	 * 
	 * @param url
	 */
	public static final void openInDefaultBrowser(String url) {
		if (java.awt.Desktop.isDesktopSupported()) {
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new java.net.URI(url));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
