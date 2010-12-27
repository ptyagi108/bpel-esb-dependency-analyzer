package com.tomecode.soa.ora.osb10g.activity;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: pipeline type="error"
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class PipelineError extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public PipelineError(String name) {
		super(name, null);
	}

	public final void merge(List<OsbActivity> activities) {

		for (OsbActivity copyActivity : activities) {
			copyActivity.setParent(this);
			this.activities.add(copyActivity);
		}
	}

	public final String toString() {
		return "Error Handler";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_ERROR;
	}

	public final String getToolTip() {
		return "Type: Error Handler";
	}
}
