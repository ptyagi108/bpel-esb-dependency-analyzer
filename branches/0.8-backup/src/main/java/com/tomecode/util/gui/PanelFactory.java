package com.tomecode.util.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/***
 * helper class for create an panels more easily
 * 
 * @author Frastia Tomas
 * 
 */
public final class PanelFactory {

	private PanelFactory() {

	}

	/**
	 * create new {@link JPanel} with layout {@link BorderLayout}
	 * 
	 * @return
	 */
	public static final JPanel createBorderLayout() {
		return new JPanel(new BorderLayout());
	}

	/**
	 * create new {@link JPanel} with layout {@link FlowLayout}
	 * 
	 * @return
	 */
	public static final JPanel createButtonsPanel() {
		return createButtonsPanel(0, 10, 10, 10);
	}

	public static final JPanel createButtonsPanelCenter() {
		return new JPanel(new FlowLayout(FlowLayout.CENTER));
	}

	/***
	 * new {@link JPanel} with {@link FlowLayout}
	 * 
	 * @param top
	 * @param left
	 * @param bottom
	 * @param right
	 * @return
	 */
	public static final JPanel createButtonsPanel(int top, int left, int bottom, int right) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		return panel;
	}

	/**
	 * panel with label
	 * 
	 * 
	 * @param label
	 * @param component
	 * @param midle
	 * @return
	 */
	public static final JPanel wrapWithLabelMax(String label, Component component, int midle) {
		JPanel panel = createBorderLayout();

		JPanel panelLabel = createBorderLayout();
		panelLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, midle));
		panelLabel.add(new JLabel(label), BorderLayout.CENTER);

		JPanel panelLabeLUp = createBorderLayout();
		panelLabeLUp.add(panelLabel, BorderLayout.NORTH);
		panel.add(panelLabeLUp, BorderLayout.WEST);
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}

	public static final JPanel wrapWithLabelMaxWhitScrolll(String label, Component component, int midle) {
		return wrapWithLabelMax(label, new JScrollPane(component), midle);
	}

	public static final JPanel wrapWithTile(String title, Component component) {
		JPanel panel = createBorderLayout();
		panel.setBorder(new TitledBorder(title));
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}

	public static final JPanel wrapWithLabelNorm(String label, Component component, int midle) {
		JPanel panel = createBorderLayout();
		JPanel panelLabel = createBorderLayout(0, 0, 0, midle);
		panelLabel.add(new JLabel(label), BorderLayout.CENTER);

		panel.add(panelLabel, BorderLayout.WEST);
		panel.add(component, BorderLayout.CENTER);

		JPanel panelWrap = createBorderLayout();
		panelWrap.add(panel, BorderLayout.NORTH);
		return panelWrap;
	}

	public static final JPanel createBorderLayout(int top, int left, int bottom, int right) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		return panel;
	}

	public static final JPanel creteaBorderWithScroll(Component component, int top, int left, int bottom, int right) {
		JPanel panel = createBorderLayout(top, left, bottom, right);
		panel.add(new JScrollPane(component), BorderLayout.CENTER);
		return panel;
	}

	public final static JPanel wrapWithLabelNorm(String label, int midle, Component component, Component left) {
		JPanel panel = createBorderLayout();
		JPanel panelLabel = createBorderLayout(0, 0, 0, midle);
		panelLabel.add(new JLabel(label), BorderLayout.CENTER);

		panel.add(panelLabel, BorderLayout.WEST);

		panel.add(component, BorderLayout.CENTER);
		panel.add(left, BorderLayout.EAST);

		JPanel panelWrap = createBorderLayout();
		panelWrap.add(panel, BorderLayout.NORTH);
		return panelWrap;
	}

	public static final JPanel wrapToGridOnlyRows(Component... components) {
		JPanel panel = new JPanel(new GridLayout(components.length, 0));
		for (Component component : components) {
			panel.add(component);
		}
		return panel;
	}

	public final static JPanel createButtonsPanel(Component... components) {
		JPanel panel = createButtonsPanel();
		for (Component component : components) {
			panel.add(component);
		}
		return panel;
	}

	/**
	 * create panel with gridlayout
	 * 
	 * @param rows
	 * @param colums
	 * @return
	 */
	public static final JPanel createGridLayout(int rows, int colums) {
		return new JPanel(new GridLayout(rows, colums));
	}

	public static final JPanel wrapWithTile(String title, Component component, int top, int left, int bottom, int right) {
		JPanel panel = createBorderLayout(top, left, bottom, right);
		panel.add(wrapWithTile(title, component), BorderLayout.CENTER);
		return panel;
	}

	public static final JPanel createBorderLayout(String title) {
		JPanel panel = createBorderLayout();
		panel.setBorder(new TitledBorder(title));
		return panel;
	}

	public static final JPanel createButtonsPanel(int top, int left, int bottom, int right, Component... components) {
		JPanel panel = createButtonsPanel(top, left, bottom, right);
		for (Component component : components) {
			panel.add(component);
		}
		return panel;
	}

	public static final JPanel wrapPanelWithCheckBoxOnRight(JComponent component) {
		JPanel panel = createBorderLayout(0, 0, 0, 0);
		JCheckBox checkBox = new JCheckBox();
		checkBox.setToolTipText("for comment");
		panel.add(component, BorderLayout.CENTER);
		panel.add(checkBox, BorderLayout.EAST);
		return panel;
	}

	public static final Component wrapByBorderLayout(Component component, String borederLaout) {
		JPanel panel = createBorderLayout(0, 0, 0, 0);
		panel.add(component, borederLaout);
		return panel;
	}

	public static final JPanel createBorderLayout(Component component, int top, int left, int bottom, int right) {
		JPanel panel = createBorderLayout(top, left, bottom, right);
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}

	public final static JSplitPane createSplitPanel() {
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jSplitPane.setUI(new BasicSplitPaneUI() {

			@Override
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {

					private static final long serialVersionUID = 2708443041295298633L;

					@Override
					public void setBorder(Border b) {
					}
				};

			}
		});
		jSplitPane.setBorder(null);
		jSplitPane.setDividerSize(2);
		return jSplitPane;
	}

	public static final JPanel createBorderLayout(String title, Component component) {
		JPanel panel = createBorderLayout(title);
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}
}
