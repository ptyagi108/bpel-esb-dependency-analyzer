package com.tomecode.soa.dependency.analyzer.gui.tree.node;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.icons.IconFactory;

/**
 * 
 * Icon node for get icon
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public interface IconNode {

	/**
	 * return icon from {@link IconFactory}
	 * 
	 * @return
	 */
	ImageIcon getIcon();
}
