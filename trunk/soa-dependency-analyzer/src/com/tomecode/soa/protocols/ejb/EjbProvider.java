package com.tomecode.soa.protocols.ejb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * EJB provider
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class EjbProvider implements ImageFace {

	private String name;

	private final List<EjbHome> ejbHomes;

	public EjbProvider(String name) {
		this.ejbHomes = new ArrayList<EjbHome>();
		this.name = name;
	}

	public final List<EjbHome> getEjbHomes() {
		return ejbHomes;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.EJB_PROVIDER;
	}

	@Override
	public String getToolTip() {
		return "EJBprovider";
	}

	public final void addEjbHome(EjbHome ejbHome) {
		ejbHomes.add(ejbHome);
		ejbHome.setEjbProvider(this);
	}

}
