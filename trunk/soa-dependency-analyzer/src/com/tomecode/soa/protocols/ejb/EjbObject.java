package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * EJB object
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbObject implements ImageFace {

	/**
	 * ejb object name
	 */
	private String name;

	/**
	 * parent {@link EjbHome}
	 */
	private EjbHome ejbHome;
	/**
	 * list of {@link EjbMethod}
	 */
	private final List<EjbMethod> ejbMethods;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            ejb object name
	 */
	public EjbObject(String name) {
		this.ejbMethods = new ArrayList<EjbMethod>();
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	/**
	 * @return the ejbHome
	 */
	public final EjbHome getEjbHome() {
		return ejbHome;
	}

	/**
	 * @return the ejbMethods
	 */
	public final List<EjbMethod> getEjbMethods() {
		return ejbMethods;
	}

	/**
	 * @param ejbHome
	 *            the ejbHome to set
	 */
	public final void setEjbHome(EjbHome ejbHome) {
		this.ejbHome = ejbHome;
	}

	public final void addEjbMethod(EjbMethod ejbMethod) {
		ejbMethod.setEjbObject(this);
		ejbMethods.add(ejbMethod);
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.EJB_OBJECT_SMALL;
		}
		return ImageFactory.EJB_OBJECT;
	}

	@Override
	public final String getToolTip() {
		return "Type: EJB Object\nName: " + name;
	}

}
