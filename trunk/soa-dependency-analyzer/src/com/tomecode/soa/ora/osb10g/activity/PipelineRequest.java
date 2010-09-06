package com.tomecode.soa.ora.osb10g.activity;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: pipeline type="request"
 * 
 * @author Tomas Frastia
 * 
 */
public final class PipelineRequest extends OsbActivity {

	public PipelineRequest(String name) {
		super(name);
	}

	public final void merge(List<OsbActivity> activities) {

		for (OsbActivity copyActivity : activities) {
			copyActivity.setParent(this);
			this.activities.add(copyActivity);
		}
	}

	public final String toString() {
		return name == null ? "Pipeline Request" : "Pipeline Request - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_REQUEST;
	}
}
