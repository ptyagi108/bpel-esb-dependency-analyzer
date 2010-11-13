package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.Subgraph;
import org.eclipse.jface.viewers.TextCellEditor;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.figures.SimpleActivityLabel;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityCellEditorLocator;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class SimpleActivityPart extends ActivityPart {

	public void contributeNodesToGraph(CompoundDirectedGraph graph, Subgraph s, Map map) {
		Node n = new Node(this, s);
		n.outgoingOffset = getAnchorOffset();
		n.incomingOffset = getAnchorOffset();
		n.width = getFigure().getPreferredSize().width;
		n.height = getFigure().getPreferredSize().height;
		n.setPadding(new Insets(10, 8, 10, 12));
		map.put(this, n);
		graph.nodes.add(n);
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Label l = new SimpleActivityLabel();
		l.setLabelAlignment(PositionConstants.LEFT);
		l.setIcon(ImageFactory.OSB_10G_PROXY_SERVICE);
		return l;
	}

	int getAnchorOffset() {
		return 9;
	}

	protected void performDirectEdit() {
		if (manager == null) {
			Label l = (Label) getFigure();
			manager = new ActivityDirectEditManager(this, TextCellEditor.class, new ActivityCellEditorLocator(l), l);
		}
		manager.show();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		((Label) getFigure()).setText(getActivity().getName());
	}

}
