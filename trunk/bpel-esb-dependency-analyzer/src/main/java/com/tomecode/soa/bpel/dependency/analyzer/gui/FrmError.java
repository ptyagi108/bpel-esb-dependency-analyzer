package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * Error form
 * 
 * @author Frastia Tomas
 * 
 */
public final class FrmError extends Frame {

	private static final long serialVersionUID = 8081131443487843647L;

	private final JButton bContinue;
	private final JButton bExist;

	private final JPanel pTxt;

	/**
	 * Constructor
	 * 
	 * @param userMsg
	 * @param e
	 */
	private FrmError(String userMsg, Throwable e) {
		super("Oops error... ;)", 600, 300, true, false);
		setIconImage(IconFactory.BDA_SMALL.getImage());
		bContinue = new JButton("Continue");
		bContinue.addActionListener(new ActionListener() {
			public final void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		bExist = new JButton("Exit");
		bExist.addActionListener(new ActionListener() {
			public final void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		pTxt = PanelFactory.createBorderLayout(PanelFactory.wrapWithTile("StactTrace...", new JScrollPane(new JTextArea(e.getMessage() + "\n\n" + sw.toString()))), 0, 0, 0, 0);

		JPanel pNorth = PanelFactory.createBorderLayout(10, 10, 10, 10);
		pNorth.add(PanelFactory.wrapWithTile("Message", new JLabel(userMsg)), BorderLayout.NORTH);

		final JPanel pCenter = PanelFactory.createBorderLayout(0, 10, 0, 10);

		pCenter.add(pTxt, BorderLayout.CENTER);
		addToContainer(pNorth, BorderLayout.NORTH);
		addToContainer(pCenter, BorderLayout.CENTER);
		addToContainer(PanelFactory.createButtonsPanel(bContinue, bExist), BorderLayout.SOUTH);
	}

	public static final void showMe(final String userMsg, final Throwable e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FrmError(userMsg, e).setVisible(true);
			}
		});
	}
}
