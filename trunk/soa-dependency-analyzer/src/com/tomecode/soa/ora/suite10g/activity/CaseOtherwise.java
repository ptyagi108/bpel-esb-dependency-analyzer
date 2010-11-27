package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * case from switch in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class CaseOtherwise extends Activity {

	private static final long serialVersionUID = -4292260142977278326L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public CaseOtherwise(String name) {
		super(ActivityType.ORACLE_10G_OTHERWISE, name);
	}

	/**
	 * Constructor
	 */
	public CaseOtherwise() {
		this(null);
	}

	public final String toString() {
		return name;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SWITCH;
	}
}
