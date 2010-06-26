package com.tomecode.soa.dependency.analyzer.gui.visual.cellview;

import java.util.Map;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexView;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class CellViewFactory extends DefaultCellViewFactory {

	private static final long serialVersionUID = -6534537855135483640L;

	public static final String VIEW_CLASS_KEY = "viewClassKey";

	@SuppressWarnings("unchecked")
	public static final void setRoundRectView(Map map) {
		map.put(VIEW_CLASS_KEY, "com.tomecode.soa.dependency.analyzer.gui.visual.cellview.RoundRectView");
	}

	protected VertexView createVertexView(Object v) {
		try {
			DefaultGraphCell cell = (DefaultGraphCell) v;
			String viewClass = (String) cell.getAttributes().get(VIEW_CLASS_KEY);

			VertexView view = (VertexView) Thread.currentThread().getContextClassLoader().loadClass(viewClass).newInstance();
			view.setCell(v);
			return view;
		} catch (Exception ex) {
		}
		return super.createVertexView(v);
	}

}
