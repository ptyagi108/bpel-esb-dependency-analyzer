package com.tomecode.soa.ora.osb10g.activity;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: pipeline type="response"
 * 
 * @author Tomas Frastia
 * 
 */
public final class PipelineResponse extends OsbActivity {

	public PipelineResponse(String name) {
		super(name);
	}

	public void merge(List<OsbActivity> activities) {
		for (OsbActivity copyActivity : activities) {
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
}
