package com.tomecode.soa.dependency.analyzer.gui.tab.frm;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import com.tomecode.soa.dependency.analyzer.gui.Desktop;
import com.tomecode.util.gui.Dialog;

/**
 * 
 * Simple form for selected workspace via ctrl+tab key events
 * 
 * @author Frastia Tomas
 * 
 */
public final class TabSwitchFrom extends Dialog {

	private static final long serialVersionUID = 3769707851832521496L;

	/**
	 * Constructor
	 */
	public TabSwitchFrom() {
		super(Desktop.getFrame(), "Switch workspace", 300, 300, false, true, false, true);
		setUndecorated(true);

		JLabel labelTitle =  new JLabel("Switch workspace");
		labelTitle.setForeground(Color.RED);
		labelTitle.setBackground(Color.GREEN);

		panelRoot.add(labelTitle, BorderLayout.NORTH);

	}

	public static final void showMe() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TabSwitchFrom().setVisible(true);
			}
		});
	}
}
