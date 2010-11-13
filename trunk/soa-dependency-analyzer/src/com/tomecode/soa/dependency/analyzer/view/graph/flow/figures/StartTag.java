package com.tomecode.soa.dependency.analyzer.view.graph.flow.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class StartTag extends Label {

	// private static final Border BORDER = new MarginBorder(2, 0, 2, 9);

	/**
	 * Creates a new StartTag
	 * 
	 * @param name
	 *            the text to display in this StartTag
	 */
	public StartTag(String name) {
		setIconTextGap(4);
		setText(name);
		// setIcon(FlowImages.GEAR);
		setBorder(new MarginBorder(2, 0, 2, 9));
	}

	protected final void paintFigure(Graphics g) {
		super.paintFigure(g);
		Rectangle r = getTextBounds();

		r.resize(-1, -1);
		r.expand(1, 1);
		r.width -= 1;
		r.x -= 2;
		g.drawLine(r.x, r.y, r.right(), r.y); // Top line
		g.drawLine(r.x, r.bottom(), r.right(), r.bottom()); // Bottom line
		g.drawLine(r.x, r.bottom(), r.x, r.y); // left line

		g.drawLine(r.right() + 7, r.y + r.height / 2, r.right(), r.y);
		g.drawLine(r.right() + 7, r.y + r.height / 2, r.right(), r.bottom());
	}

}
