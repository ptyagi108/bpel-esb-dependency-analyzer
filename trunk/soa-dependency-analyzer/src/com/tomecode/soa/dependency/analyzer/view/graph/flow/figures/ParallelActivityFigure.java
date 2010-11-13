package com.tomecode.soa.dependency.analyzer.view.graph.flow.figures;

import org.eclipse.draw2d.ColorConstants;
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
public final class ParallelActivityFigure extends SubgraphFigure {

	private boolean selected;

	/**
	 * @param header
	 * @param footer
	 */
	public ParallelActivityFigure() {
		super(new Label(""), new Label(""));
		setBorder(new MarginBorder(3, 5, 3, 0));
		setOpaque(true);
	}

	protected final void paintFigure(Graphics g) {
		super.paintFigure(g);
		Rectangle r = getBounds();
		g.setBackgroundColor(ColorConstants.button);
		if (selected) {
			g.setBackgroundColor(ColorConstants.menuBackgroundSelected);
			g.setForegroundColor(ColorConstants.menuForegroundSelected);
		}

		g.fillRectangle(r.x, r.y, 3, r.height);
		g.fillRectangle(r.right() - 3, r.y, 3, r.height);
		g.fillRectangle(r.x, r.bottom() - 18, r.width, 18);
		g.fillRectangle(r.x, r.y, r.width, 18);
	}

	public final void setSelected(boolean selected) {
		if (this.selected == selected)
			return;
		this.selected = selected;
		if (!selected) {
			getHeader().setForegroundColor(null);
			getFooter().setForegroundColor(null);
		} else {
			getHeader().setForegroundColor(ColorConstants.menuForegroundSelected);
			getFooter().setForegroundColor(ColorConstants.menuForegroundSelected);
		}

		repaint();
	}

}
