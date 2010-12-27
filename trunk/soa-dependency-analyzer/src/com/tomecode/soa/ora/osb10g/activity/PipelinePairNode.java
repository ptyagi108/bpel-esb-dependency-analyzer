package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * pipeline-node in proxy
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelinePairNode extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public PipelinePairNode(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final String toString() {
		return name == null ? "PipelinePairNode" : "PipelinePairNode - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_PARI_NODE;
	}

	public final String getToolTip() {
		return "Type: PipelinePairNode\nName: " + (name != null ? name : "");
	}
}
