package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * EJB method
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbMethod implements ImageFace, Node<EjbMethod> {

	/**
	 * method name
	 */
	private String name;
	/**
	 * method signature
	 */
	private String signature;
	/**
	 * parent object
	 */
	private EjbObject ejbObject;

	private final List<?> nodes = new ArrayList<Node<?>>();

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public EjbMethod(String name, String signature) {
		this.name = name;
		this.signature = signature;
	}

	public final String getName() {
		return name;
	}

	/**
	 * @return the signature
	 */
	public final String getSignature() {
		return signature;
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

	public final String toString() {
		return name;
	}

	@Override
	public final String getToolTip() {
		return "Type: EJB Method\nName: " + name + "\nSignature: " + signature;
	}

	@Override
	public final Object getParent() {
		return ejbObject;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final EjbMethod getObj() {
		return this;
	}

}
