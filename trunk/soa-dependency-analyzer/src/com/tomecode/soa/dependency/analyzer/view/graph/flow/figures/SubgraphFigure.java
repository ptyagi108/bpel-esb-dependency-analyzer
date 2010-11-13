package com.tomecode.soa.dependency.analyzer.view.graph.flow.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.DummyLayout;

/**
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class SubgraphFigure extends Figure {

	private IFigure contents;
	private IFigure footer;
	private IFigure header;

	public SubgraphFigure(IFigure header, IFigure footer) {
		contents = new Figure();
		contents.setLayoutManager(new DummyLayout());
		add(contents);
		add(this.header = header);
		add(this.footer = footer);
	}

	public final IFigure getContents() {
		return contents;
	}

	public final IFigure getFooter() {
		return footer;
	}

	public final IFigure getHeader() {
		return header;
	}

	/**
	 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
	 */
	public final Dimension getPreferredSize(int wHint, int hHint) {
		Dimension dim = new Dimension();
		dim.width = getFooter().getPreferredSize().width;
		dim.width += getInsets().getWidth();
		dim.height = 50;
		return dim;
	}

	public final void setBounds(Rectangle rect) {
		super.setBounds(rect);
		rect = Rectangle.SINGLETON;
		getClientArea(rect);
		contents.setBounds(rect);
		Dimension size = footer.getPreferredSize();
		footer.setLocation(rect.getBottomLeft().translate(0, -size.height));
		footer.setSize(size);

		size = header.getPreferredSize();
		header.setSize(size);
		header.setLocation(rect.getLocation());
	}

	public void setSelected(boolean value) {
	}

}
