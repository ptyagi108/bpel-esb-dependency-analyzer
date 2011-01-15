package com.tomecode.soa.protocols.ejb;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * EJB method
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbMethod implements ImageFace {

	/**
	 * method name
	 */
	private String name;
	/**
	 * parent object
	 */
	private EjbObject ejbObject;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public EjbMethod(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final EjbObject getEjbObject() {
		return ejbObject;
	}

	/**
	 * @param ejbObject
	 *            the ejbObject to set
	 */
	public final void setEjbObject(EjbObject ejbObject) {
		this.ejbObject = ejbObject;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.EJB_METHOD_SMALL;
		}
		return ImageFactory.EJB_METHOD;
	}

	@Override
	public final String getToolTip() {
		return "EJB Method\nName: " + name;
	}

}
