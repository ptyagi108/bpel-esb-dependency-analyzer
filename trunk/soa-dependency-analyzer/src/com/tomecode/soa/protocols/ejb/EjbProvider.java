package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * EJB provider
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbProvider implements ImageFace, Node<EjbProvider> {

	/**
	 * ejb provider name
	 */
	private String name;

	/**
	 * list of {@link EjbHome}
	 */
	private final List<EjbHome> ejbHomes = new ArrayList<EjbHome>();

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            provider name
	 */
	public EjbProvider(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.EJB_PROVIDER_SMALL;
		}
		return ImageFactory.EJB_PROVIDER;
	}

	@Override
	public String getToolTip() {
		return "Type: EJBprovider\nName: " + name;
	}

	public final void addEjbHome(EjbHome ejbHome) {
		ejbHomes.add(ejbHome);
		ejbHome.setEjbProvider(this);
	}

	public final String toString() {
		return name;
	}

	public final void setParentService(Service parentService) {
		this.parentService = parentService;
	}

	@Override
	public final Object getParent() {
		return parentService;
	}

	@Override
	public final List<EjbHome> getChilds() {
		return ejbHomes;
	}

	@Override
	public final EjbProvider getObj() {
		return this;
	}
}
