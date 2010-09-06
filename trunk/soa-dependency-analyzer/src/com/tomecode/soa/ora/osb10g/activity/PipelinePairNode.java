package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * pipeline-node in proxy
 * 
 * @author frastia
 * 
 */
public final class PipelinePairNode extends OsbActivity {

	public PipelinePairNode(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "PipelinePairNode" : "PipelinePairNode - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_PARI_NODE;
	}
}
