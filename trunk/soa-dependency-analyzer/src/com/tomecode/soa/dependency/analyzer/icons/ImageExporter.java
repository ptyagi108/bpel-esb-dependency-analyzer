package com.tomecode.soa.dependency.analyzer.icons;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.zest.core.widgets.Graph;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Simple utilities class for export graph/chart to image
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class ImageExporter {

	private ImageExporter() {

	}

	/**
	 * 
	 * export graph to file
	 * 
	 * 
	 * @param graph
	 *            selected graph
	 * @param imageFile
	 *            file where saved graph
	 * @param imageFormate
	 *            image format
	 * @throws IOException
	 */
	public static final void export(Graph graph, File imageFile, int imageFormat) throws IOException {

		createImageFile(imageFile);

		GC gc = new GC(graph);
		try {
			Image image = new Image(graph.getDisplay(), graph.getSize().x, graph.getSize().y);
			gc.copyArea(image, 0, 0);
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] { image.getImageData() };
			loader.save(imageFile.getPath(), imageFormat);
		} finally {
			if (gc != null) {
				gc.dispose();
			}
		}
	}

	private static final void createImageFile(File imageFile) throws IOException {
		File parentDirs = imageFile.getParentFile();
		if (!parentDirs.exists()) {
			parentDirs.mkdirs();
		}

		imageFile.createNewFile();
	}
}
