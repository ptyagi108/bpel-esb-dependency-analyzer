package com.tomecode.soa.ora.suite10g.esb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * routing rule in ESB
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbRoutingRule implements BasicEsbNode {

	private static final long serialVersionUID = -342513167744595428L;
	private List<EsbSvc> esbSvcs;
	private String qname;
	private String serviceQName;

	/**
	 * Constructor
	 * 
	 * @param qname
	 * @param serviceQName
	 */
	public EsbRoutingRule(String qname, String serviceQName) {
		this.esbSvcs = new ArrayList<EsbSvc>();
		this.qname = qname;
		this.serviceQName = serviceQName;
	}

	public final String getQname() {
		return qname;
	}

	public final String getServiceQName() {
		return serviceQName;
	}

	public final String toString() {
		return serviceQName;
	}

	public final List<EsbSvc> getEsbSvcs() {
		return esbSvcs;
	}

	public final void addEsbSvcs(EsbSvc esbSvc) {
		this.esbSvcs.add(esbSvc);
	}

	public final void addDependencySvc(EsbSvc esbSvc) {
		if (esbSvc != null) {
			if (!esbSvcs.contains(esbSvc)) {
				esbSvcs.add(esbSvc);
			}
		}
	}

	@Override
	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ROUTING_RULES;
	}

	@Override
	public final String getToolTip() {
		return "Routing Rule";
	}

	@Override
	public final EsbNodeType getType() {
		return EsbNodeType.ROUTING;
	}

	@Override
	public final Object get() {
		return this;
	}

}
