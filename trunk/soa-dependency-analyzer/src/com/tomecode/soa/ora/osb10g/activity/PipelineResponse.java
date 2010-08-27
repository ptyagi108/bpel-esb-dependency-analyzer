package com.tomecode.soa.ora.osb10g.activity;

import java.util.List;

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
		this.activities.addAll(activities);
	}
}
