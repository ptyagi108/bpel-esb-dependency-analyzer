package com.tomecode.util.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.JFrame;

/**
 * 
 * Default frame
 * 
 * @author Frastia Tomas
 * 
 */
public class Frame extends JFrame implements Serializable {

	private static final long serialVersionUID = -6086024702874038952L;

	private final Container container;

	/***
	 * 
	 * 
	 * 
	 * @param title
	 *            of window
	 * @param width
	 *            for window
	 * @param height
	 *            for window
	 * @param resizable
	 *            if true window have enable resisable
	 * @param exitOnClose
	 *            if true window have set default close operation
	 */
	public Frame(String title, int width, int height, boolean resizable, boolean exitOnClose) {
		setTitle(title);
		setResizable(resizable);
		setSize(width, height);
		setLocation(getCenterLocation());
		if (exitOnClose) {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		} else {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}

		this.container = new Container();
		container.setLayout(new BorderLayout());
		setContentPane(container);
	}

	/**
	 * center window location
	 * 
	 * @return
	 */
	protected final Point getCenterLocation() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;

		return new Point(x, y);
	}

	//
	// protected final Container getContainer() {
	// return container;
	// }

	protected final void addToContainer(Component component, String borderLayout) {
		container.add(component, borderLayout);
	}

}
