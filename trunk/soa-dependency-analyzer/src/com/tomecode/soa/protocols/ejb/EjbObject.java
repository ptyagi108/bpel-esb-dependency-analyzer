package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * EJB object
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "EJB Object", parentMethod = "getParent")
public final class EjbObject implements ImageFace, Node<EjbObject> {

	/**
	 * ejb object name
	 */
	@PropertyViewData(title = "Name:")
	private String name;

	/**
	 * parent {@link EjbHome}
	 */
	private EjbHome ejbHome;
	/**
	 * list of {@link EjbMethod}
	 */
	private final List<EjbMethod> ejbMethods = new ArrayList<EjbMethod>();

	/**
	 * Constructor
	 * 
	 * @param name
	 *            ejb object name
	 */
	public EjbObject(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
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

	public final String toString() {
		return name;
	}

	@Override
	public final EjbHome getParent() {
		return ejbHome;
	}

	@Override
	public final List<EjbMethod> getChilds() {
		return ejbMethods;
	}

	@Override
	public final EjbObject getObj() {
		return this;
	}

}
