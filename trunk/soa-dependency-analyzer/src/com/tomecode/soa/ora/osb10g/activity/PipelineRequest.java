package com.tomecode.soa.ora.osb10g.activity;

import java.util.List;

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
}
