package com.tomecode.soa.bpel.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;
import com.tomecode.soa.dependency.analyzer.utils.Utils;
import com.tomecode.util.gui.Dialog;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * 
 * Frm for about
 * 
 * @author Tomas Frastia
 * 
 */
public final class FrmAbout extends Dialog {

	private static final long serialVersionUID = -2549954953983634577L;

	private final JButton buttonOk;

	/**
	 * Constructor
	 * 
	 * @param owner
	 */
	public FrmAbout(Frame owner) {
		super(owner, "About SOA: BPEL and ESB Dependency analyzer", 450, 175, false, true, false, true);
		setIconImage(IconFactory.BDA_SMALL.getImage());

		panelRoot.removeAll();

		JPanel pButton = PanelFactory.createButtonsPanelCenter();
		buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {

			@Override
			public final void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		pButton.add(buttonOk);
		panelRoot.add(pButton, BorderLayout.SOUTH);

		JLabel iconLabel = new JLabel();
		iconLabel.setIcon(IconFactory.BDA);
		JPanel pCenter = PanelFactory.createBorderLayout(15, 15, 0, 15);
		pCenter.add(iconLabel, BorderLayout.WEST);

		JPanel pGrid = PanelFactory.createGridLayout(4, 1);

		pGrid.add(new JLabel("    "));
		pGrid.add(new JLabel("    Version 0.7"));
		pGrid.add(createClickLabel("http://code.google.com/p/bpel-esb-dependency-analyzer/"));
		pGrid.add(createClickLabel("http://www.tomecode.com"));
		pCenter.add(PanelFactory.wrapByBorderLayout(pGrid, BorderLayout.NORTH), BorderLayout.CENTER);
		panelRoot.add(pCenter, BorderLayout.CENTER);
	}

	/**
	 * Create click label for url
	 * 
	 * @param url
	 * @return
	 */
	private final JLabel createClickLabel(final String url) {
		final JLabel label = new JLabel("    " + url);
		label.addMouseListener(new MouseAdapter() {

			public final void mouseClicked(MouseEvent e) {
				Utils.openInDefaultBrowser(url);
			}

			public final void mouseEntered(MouseEvent e) {
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				label.setForeground(Color.red);
			}

			public final void mouseExited(MouseEvent e) {
				label.setCursor(Cursor.getDefaultCursor());
				label.setForeground(Color.black);
			}
		});

		return label;
	}

	/**
	 * show {@link FrmAbout}
	 * 
	 * @param owner
	 */
	public static final void showMe(final Frame owner) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FrmAbout(owner).setVisible(true);
			}
		});
	}
}
