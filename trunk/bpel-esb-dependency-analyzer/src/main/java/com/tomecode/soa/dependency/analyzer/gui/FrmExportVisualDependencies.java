package com.tomecode.soa.dependency.analyzer.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tomecode.soa.dependency.analyzer.gui.components.TabbedManager;
import com.tomecode.soa.dependency.analyzer.icons.IconFactory;
import com.tomecode.util.gui.Dialog;
import com.tomecode.util.gui.Frame;
import com.tomecode.util.gui.PanelFactory;

/**
 * Frm for exporting viasual graph
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class FrmExportVisualDependencies extends Dialog {

	private static final long serialVersionUID = 5752792596390258767L;

	private final JTextField txtPath;

	private final JButton buttonExport;

	/**
	 * Constructor
	 * 
	 * @param owner
	 */
	private FrmExportVisualDependencies(Frame owner) {
		super(owner, "Export dependency graph...", 600, 160, false, true, true, true);
		setIconImage(IconFactory.BDA_SMALL.getImage());

		JLabel infoIcon = new JLabel(IconFactory.EXPORT_BIG);

		JPanel pRoot = PanelFactory.createBorderLayout(15, 15, 0, 15);
		panelRoot.add(pRoot, BorderLayout.CENTER);
		pRoot.add(PanelFactory.wrapByBorderLayout(infoIcon, BorderLayout.NORTH), BorderLayout.WEST);
		JPanel pDetail = PanelFactory.createBorderLayout();
		txtPath = new JTextField();
		txtPath.addKeyListener(new KeyAdapter() {
			public final void keyTyped(KeyEvent e) {
				validatePath();
			}

			public final void keyReleased(KeyEvent e) {
				validatePath();
			}
		});
		pDetail.add(txtPath, BorderLayout.CENTER);
		JButton bFind = new JButton("...");
		pDetail.add(bFind, BorderLayout.EAST);
		bFind.addActionListener(new ActionListener() {
			@Override
			public final void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = fc.showSaveDialog(FrmExportVisualDependencies.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtPath.setText(fc.getSelectedFile().toString());
				}
			}
		});

		buttonExport = new JButton("Export");
		buttonExport.setEnabled(false);
		buttonExport.addActionListener(new ActionListener() {
			@Override
			public final void actionPerformed(ActionEvent e) {
				File fExport = new File(txtPath.getText().trim());
				if (fExport.getName().endsWith(".png")) {
					try {
						TabbedManager.getInstance().exportToPng(fExport);
					} catch (IOException ex) {
						FrmError.showMe("Failed export file to png formate", ex);
					}
				} else {
					try {
						TabbedManager.getInstance().exportToJpg(fExport);
					} catch (Exception ex) {
						FrmError.showMe("Failed export file to png formate", ex);
					}
				}
				hideMe();
			}
		});
		addToButtonLayout(buttonExport);
		pRoot.add(PanelFactory.wrapByBorderLayout(PanelFactory.wrapWithTile("Export path", pDetail), BorderLayout.NORTH), BorderLayout.CENTER);
	}

	/**
	 * validate entered path
	 */
	private final void validatePath() {
		if (txtPath.getText().trim().length() != 0) {
			File f = new File(txtPath.getText().trim());
			if (f.getParentFile() != null) {
				buttonExport.setEnabled(f.getName().endsWith(".png") || f.getName().endsWith(".jpg"));
				return;
			}

		}
		buttonExport.setEnabled(false);
	}

	/**
	 * show {@link FrmExportVisualDependencies}
	 * 
	 * @param project
	 */
	public final static void showMe() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public final void run() {
				new FrmExportVisualDependencies(Desktop.getFrame()).setVisible(true);
			}
		});
	}

}
