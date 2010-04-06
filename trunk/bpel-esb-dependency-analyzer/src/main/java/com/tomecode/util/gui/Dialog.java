package com.tomecode.util.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * default dialog
 * 
 * @author Frastia Tomas
 * 
 */
public class Dialog extends JDialog implements Serializable {

	private static final long serialVersionUID = 7459550653255913759L;

	protected final JButton buttonCancel;

	private final JPanel panelButtons;

	protected final JPanel panelRoot;

	private boolean isCancel;

	private final List<HideNotifiListener> hideNotifyListeners;

	/**
	 * 
	 * 
	 * @param title
	 * @param width
	 * @param height
	 * @param resizable
	 * @param modal
	 * @param enableCancelButton
	 *            if true thew add cancle button to dialog
	 * @param disposeOnExit
	 */
	public Dialog(Frame owner, String title, int width, int height, boolean resizable, boolean modal, boolean enableCancelButton, boolean disposeOnExit) {
		super(owner, title, modal);
		this.hideNotifyListeners = new ArrayList<HideNotifiListener>();
		setResizable(resizable);
		setSize(width, height);

		if (disposeOnExit) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} else {
			setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		}

		buttonCancel = new JButton("Cancel");
		// buttonCancel = new JButton("Cancel", ImageFactory.CANCEL, true,
		// false);
		buttonCancel.addActionListener(new ActionListener() {

			public final void actionPerformed(ActionEvent e) {
				isCancel = true;
				hideMe();
			}
		});

		panelRoot = PanelFactory.createBorderLayout();
		panelButtons = PanelFactory.createButtonsPanel();
		if (enableCancelButton) {
			panelButtons.add(buttonCancel);
		}

		Container container = new Container();
		container.setLayout(new BorderLayout());
		container.add(panelRoot, BorderLayout.CENTER);
		container.add(panelButtons, BorderLayout.SOUTH);
		setContentPane(container);

		setLocation(getCenterLocation());

	}

	public void hideMe() {
		setVisible(false);
		dispose();
	}

	/**
	 * 
	 * close window
	 */
	protected void hideMe(Object... returnObj) {
		setVisible(false);
		dispose();

		sendReturnObj(returnObj);
	}

	protected void sendReturnObj(Object... returnObj) {
		for (HideNotifiListener listener : hideNotifyListeners) {
			listener.hideForm(returnObj);
		}
	}

	/**
	 * 
	 * set {@link Component} befroe Cancel button
	 * 
	 * @param component
	 */
	protected final void addToButtonLayout(Component component) {
		panelButtons.add(component, 0);
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

	// protected final Container getContainer() {
	// return super.getContentPane();
	// }

	public final Dialog addHideListeners(HideNotifiListener notifiListener) {
		this.hideNotifyListeners.add(notifiListener);
		return this;
	}

	public final boolean isCanceled() {
		return isCancel;
	}
}
