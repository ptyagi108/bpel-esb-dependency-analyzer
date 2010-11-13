package com.tomecode.soa.dependency.analyzer.view.graph.flow.figures;

import org.eclipse.draw2d.Border;
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
public class EndTag extends Label {

	private static final Border BORDER = new MarginBorder(2, 0, 2, 2);

	/**
	 * Creates a new StartTag
	 * 
	 * @param name
	 *            the text to display in this StartTag
	 */
	public EndTag(String name) {
		setIconTextGap(8);
		setText(name);
		// setIcon(FlowImages.GEAR);
		setBorder(BORDER);
	}

	protected final void paintFigure(Graphics g) {
		super.paintFigure(g);
		Rectangle r = getTextBounds();

		r.resize(0, -1).expand(1, 1);
		g.drawLine(r.x, r.y, r.right(), r.y); // Top line
		g.drawLine(r.x, r.bottom(), r.right(), r.bottom()); // Bottom line
		g.drawLine(r.right(), r.bottom(), r.right(), r.y); // Right line

		g.drawLine(r.x - 7, r.y + r.height / 2, r.x, r.y);
		g.drawLine(r.x - 7, r.y + r.height / 2, r.x, r.bottom());
	}

}
