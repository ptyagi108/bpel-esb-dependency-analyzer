package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.Subgraph;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.TextCellEditor;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.StructuredActivity;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.figures.SubgraphFigure;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityCellEditorLocator;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityContainerEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityContainerHighlightEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityNodeEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.StructuredActivityDirectEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.StructuredActivityLayoutEditPolicy;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class StructuredActivityPart extends ActivityPart implements NodeEditPart {

	static final Insets PADDING = new Insets(8, 6, 8, 6);
	static final Insets INNER_PADDING = new Insets(0);

	protected void applyChildrenResults(CompoundDirectedGraph graph, Map map) {
		for (int i = 0; i < getChildren().size(); i++) {
			ActivityPart part = (ActivityPart) getChildren().get(i);
			part.applyGraphResults(graph, map);
		}
	}

	protected void applyGraphResults(CompoundDirectedGraph graph, Map map) {
		applyOwnResults(graph, map);
		applyChildrenResults(graph, map);
	}

	protected void applyOwnResults(CompoundDirectedGraph graph, Map map) {
		super.applyGraphResults(graph, map);
	}

	/**
	 * @see com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.examples.flow.parts.ActivityPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ActivityNodeEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ActivityEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ActivityContainerHighlightEditPolicy());
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ActivityContainerEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new StructuredActivityLayoutEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new StructuredActivityDirectEditPolicy());
	}

	public void contributeNodesToGraph(CompoundDirectedGraph graph, Subgraph s, Map map) {
		GraphAnimation.recordInitialState(getContentPane());
		Subgraph me = new Subgraph(this, s);
		// me.rowOrder = getActivity().getSortIndex();
		me.outgoingOffset = 5;
		me.incomingOffset = 5;
		IFigure fig = getFigure();
		if (fig instanceof SubgraphFigure) {
			me.width = fig.getPreferredSize(me.width, me.height).width;
			int tagHeight = ((SubgraphFigure) fig).getHeader().getPreferredSize().height;
			me.insets.top = tagHeight;
			me.insets.left = 0;
			me.insets.bottom = tagHeight;
		}
		me.innerPadding = INNER_PADDING;
		me.setPadding(PADDING);
		map.put(this, me);
		graph.nodes.add(me);
		for (int i = 0; i < getChildren().size(); i++) {
			ActivityPart activity = (ActivityPart) getChildren().get(i);
			activity.contributeNodesToGraph(graph, me, map);
		}
	}

	private boolean directEditHitTest(Point requestLoc) {
		IFigure header = ((SubgraphFigure) getFigure()).getHeader();
		header.translateToRelative(requestLoc);
		if (header.containsPoint(requestLoc))
			return true;
		return false;
	}

	/**
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			if (request instanceof DirectEditRequest && !directEditHitTest(((DirectEditRequest) request).getLocation().getCopy()))
				return;
			performDirectEdit();
		}
	}

	int getAnchorOffset() {
		return -1;
	}

	public IFigure getContentPane() {
		if (getFigure() instanceof SubgraphFigure)
			return ((SubgraphFigure) getFigure()).getContents();
		return getFigure();
	}

	protected List getModelChildren() {
		return getStructuredActivity().getChildren();
	}

	StructuredActivity getStructuredActivity() {
		return (StructuredActivity) getModel();
	}

	/**
	 * @see com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.examples.flow.parts.ActivityPart#performDirectEdit()
	 */
	protected void performDirectEdit() {
		if (manager == null) {
			Label l = ((Label) ((SubgraphFigure) getFigure()).getHeader());
			manager = new ActivityDirectEditManager(this, TextCellEditor.class, new ActivityCellEditorLocator(l), l);
		}
		manager.show();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		((Label) ((SubgraphFigure) getFigure()).getHeader()).setText(getActivity().getName());
		((Label) ((SubgraphFigure) getFigure()).getFooter()).setText("/" + getActivity().getName()); //$NON-NLS-1$
	}

}
