package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: pipeline type="response"
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelineResponse extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public PipelineResponse(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final void merge(PipelineResponse response) {
		this.errorHandler = response.getErrorHandlerName();
		for (OsbActivity copyActivity : response.activities) {
			copyActivity.setParent(this);
			this.activities.add(copyActivity);
		}
	}

	public final String toString() {
		return name == null ? "Pipeline Response" : "Pipeline Response - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_RESPONSE;
	}

	public final String getToolTip() {
		return "Type: Pipeline Response\nName: " + (name != null ? name : "");
	}
}
