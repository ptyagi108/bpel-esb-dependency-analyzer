package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JProgressBar;

import com.tomecode.util.gui.Dialog;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class FrmLoadingWorkspace extends Dialog {

	private static final long serialVersionUID = -571212973983820836L;

	private final JProgressBar progressBar;

	public FrmLoadingWorkspace(Frame owner) {
		super(owner, "Loading workspace...", 200, 222, false, true, true, true);

		progressBar = new JProgressBar(0, 50);
		progressBar.setPreferredSize(new Dimension(100, 10));
		progressBar.setIndeterminate(true);

		panelRoot.add(PanelFactory.createBorderLayout(PanelFactory.wrapByBorderLayout(progressBar, BorderLayout.NORTH), 10, 15, 10, 15), BorderLayout.CENTER);

	}

}
