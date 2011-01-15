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

	private String name;

	private EjbHome ejbHome;

	private final List<EjbMethod> ejbMethods;

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
	public final Image getImage() {
		return ImageFactory.EJB_OBJECT;
	}

	@Override
	public final String getToolTip() {
		return "EJB Object\nName: " + name;
	}

}
