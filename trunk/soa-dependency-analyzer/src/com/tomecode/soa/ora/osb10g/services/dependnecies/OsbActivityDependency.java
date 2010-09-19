package com.tomecode.soa.ora.osb10g.services.dependnecies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.services.Service;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class OsbActivityDependency {

	private Service parentService;

	private OsbActivity activity;

	private List<Service> services;

	private final List<OsbActivityDependency> activityDependencies;

	private OsbActivityDependency() {
		this.activityDependencies = new ArrayList<OsbActivityDependency>();
		this.services = new ArrayList<Service>();
	}

	public OsbActivityDependency(Service service) {
		this();
		this.parentService = service;
	}

	public OsbActivityDependency(OsbActivity targetActivity, Service targetService) {
		this();
		this.activity = targetActivity;
		this.services.add(targetService);
	}

	/**
	 * @return the parentService
	 */
	public final Service getParentService() {
		return parentService;
	}

	/**
	 * @return the osbActivity
	 */
	public final OsbActivity getActivity() {
		return activity;
	}

	/**
	 * @return the service
	 */
	public final List<Service> getServices() {
		return services;
	}

	public final void setTargetService(Service targetService) {
		if (!services.contains(targetService)) {
			this.services.add(targetService);
		}
	}

	public final String getServiceName() {
		if (!services.isEmpty()) {
			return services.get(0).toString();
		}
		return null;
	}

	public final void addDependecy(OsbActivity targetActivity, Service targetService) {
		this.activityDependencies.add(new OsbActivityDependency(targetActivity, targetService));
	}

	/**
	 * @return the activityDependencies
	 */
	public final List<OsbActivityDependency> getActivityDependencies() {
		return activityDependencies;
	}

	public final String toString() {
		return activity.toString();
	}

	public final Image getImage() {
		return activity.getImage();
	}

}
