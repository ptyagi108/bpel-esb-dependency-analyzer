package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * EJB home
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbHome implements ImageFace {

	private String name;

	private EjbProvider ejbProvider;

	private final List<EjbObject> ejbObjects;

	public EjbHome(String name) {
		this.ejbObjects = new ArrayList<EjbObject>();
		this.name = name;
	}

	/**
	 * @return the ejbObjects
	 */
	public final List<EjbObject> getEjbObjects() {
		return ejbObjects;
	}

	public final String getName() {
		return name;
	}

	public final EjbProvider getEjbProvider() {
		return ejbProvider;
	}

	public final void addEjbObject(EjbObject ejbObject) {
		ejbObjects.add(ejbObject);
		ejbObject.setEjbHome(this);
	}

	/**
	 * @param ejbProvider
	 *            the ejbProvider to set
	 */
	public final void setEjbProvider(EjbProvider ejbProvider) {
		this.ejbProvider = ejbProvider;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.EJB_HOME;
	}

	@Override
	public final String getToolTip() {
		// TODO Auto-generated method stub
		return null;
	}

}
