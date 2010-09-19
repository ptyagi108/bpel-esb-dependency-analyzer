package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: scope in SplitJoin flow
 * 
 * @author Tomas Frastia
 * 
 */
public final class Scope extends OsbActivity {

	public Scope(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Scope" : name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_SCOPE;
	}

	public final OsbActivity getDataFlyout() {
		for (OsbActivity activity : activities) {
			if (activity instanceof DataLayout) {
				return activity;
			}
		}
		return null;
	}

	public final OsbActivity getLogicFlyout() {
		for (OsbActivity activity : activities) {
			if (activity instanceof LogicFlyout) {
				return activity;
			}
		}
		return null;
	}

}
