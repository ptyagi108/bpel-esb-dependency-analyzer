package com.tomecode.soa.dependency.analyzer.view.graph.flow.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class SequentialActivityFigure extends SubgraphFigure {

	private static final MarginBorder MARGIN_BORDER = new MarginBorder(0, 8, 0, 0);

	static final PointList ARROW = new PointList(3);
	{
		ARROW.addPoint(0, 0);
		ARROW.addPoint(10, 0);
		ARROW.addPoint(5, 5);
	}

	/**
	 * @param header
	 * @param footer
	 */
	public SequentialActivityFigure() {
		super(new StartTag(""), new EndTag(""));
		setBorder(MARGIN_BORDER);
		setOpaque(true);
	}

	protected final void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.setBackgroundColor(ColorConstants.button);
		Rectangle r = getBounds();
		graphics.fillRectangle(r.x + 13, r.y + 10, 8, r.height - 18);
		// graphics.fillPolygon(ARROW);
		// graphics.drawPolygon(ARROW);
	}

}
