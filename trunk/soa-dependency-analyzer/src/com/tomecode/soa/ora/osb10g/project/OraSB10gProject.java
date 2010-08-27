package com.tomecode.soa.ora.osb10g.project;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.services.Service;

/**
 * Oracle Service Bus 10g project
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gProject {

	private final List<Service> services;

	public OraSB10gProject() {
		this.services = new ArrayList<Service>();
	}

	public final List<Service> getServices() {
		return services;
	}

}
