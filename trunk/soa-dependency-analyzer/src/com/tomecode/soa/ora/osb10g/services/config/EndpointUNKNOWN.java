package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Endpoint protocol - UNKNOWN - custom adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "Unknown Endpoint")
public final class EndpointUNKNOWN extends EndpointConfig<EndpointUNKNOWN> implements ImageFace, Node<EndpointUNKNOWN> {

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	@PropertyViewData(title = "Provider ID:")
	private String providerId;

	private Service parentService;

	public EndpointUNKNOWN(Service parentService) {
		super(ProviderProtocol.UNKNOWN);
		this.parentService = parentService;
	}

	/**
	 * @return the providerId
	 */
	public final String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public final void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public final String toString() {
		return "Unknow Endpoint - Provider Id: " + providerId;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.UNKNOWN_SERVICE_SMALL;
		}
		return ImageFactory.UNKNOWN_SERVICE;
	}

	@Override
	public final String getToolTip() {
		return "Unknown Endpoint";
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final EndpointUNKNOWN getObj() {
		return this;
	}

}
