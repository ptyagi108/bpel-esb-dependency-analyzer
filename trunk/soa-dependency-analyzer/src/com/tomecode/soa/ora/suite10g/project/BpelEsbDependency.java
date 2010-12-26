package com.tomecode.soa.ora.suite10g.project;

import java.net.URL;

import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * (Wrapper object) for esb project
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BpelEsbDependency {

	private Ora10gEsbProject[] esbProject;

	private EsbSvc esbSvc;

	private URL url;

	/**
	 * Constructor
	 * 
	 * @param esbProject
	 * @param esbSvc
	 * @param url
	 */
	public BpelEsbDependency(Ora10gEsbProject esbProject, EsbSvc esbSvc, URL url) {
		this.esbProject = new Ora10gEsbProject[1];
		this.esbProject[0] = esbProject;
		this.esbSvc = esbSvc;
		this.url = url;
	}

	/**
	 * @return the esbProject
	 */
	public final Ora10gEsbProject getEsbProject() {
		return esbProject[0];
	}

	public final Ora10gEsbProject[] getEsbProjectAsList() {
		return esbProject;
	}

	/**
	 * @return the esbSvc
	 */
	public final EsbSvc getEsbSvc() {
		return esbSvc;
	}

	/**
	 * @return the url
	 */
	public final URL getUrl() {
		return url;
	}

}
