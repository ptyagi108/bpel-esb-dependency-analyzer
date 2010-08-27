package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: ifThenElse
 * 
 * @author Tomas Frastia
 * 
 */
public final class IfThenElse extends OsbActivity {

	public final boolean hasIf() {
		for (OsbActivity activity : activities) {
			if (activity instanceof If) {
				return true;
			}
		}
		return false;
	}

}
