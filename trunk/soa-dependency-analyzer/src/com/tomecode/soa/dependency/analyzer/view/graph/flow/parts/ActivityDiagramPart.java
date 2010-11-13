package com.tomecode.soa.dependency.analyzer.view.graph.flow.parts;

import java.util.EventObject;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.ActivityContainerEditPolicy;
import com.tomecode.soa.dependency.analyzer.view.graph.flow.policies.StructuredActivityLayoutEditPolicy;

/**
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ActivityDiagramPart extends StructuredActivityPart {

	CommandStackListener stackListener = new CommandStackListener() {
		public void commandStackChanged(EventObject event) {
			if (!GraphAnimation.captureLayout(getFigure()))
				return;
			while (GraphAnimation.step())
				getFigure().getUpdateManager().performUpdate();
			GraphAnimation.end();
		}
	};

	protected void applyOwnResults(CompoundDirectedGraph graph, Map map) {
	}

	/**
	 * @see com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.examples.flow.parts.ActivityPart#activate()
	 */
	public void activate() {
		super.activate();
		getViewer().getEditDomain().getCommandStack().addCommandStackListener(stackListener);
	}

	/**
	 * @see com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.examples.flow.parts.ActivityPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.NODE_ROLE, null);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, null);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new StructuredActivityLayoutEditPolicy());
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ActivityContainerEditPolicy());
	}

	protected IFigure createFigure() {
		Figure f = new Figure() {
			public void setBounds(Rectangle rect) {
				int x = bounds.x, y = bounds.y;

				boolean resize = (rect.width != bounds.width) || (rect.height != bounds.height), translate = (rect.x != x) || (rect.y != y);

				if (isVisible() && (resize || translate))
					erase();
				if (translate) {
					int dx = rect.x - x;
					int dy = rect.y - y;
					primTranslate(dx, dy);
				}
				bounds.width = rect.width;
				bounds.height = rect.height;
				if (resize || translate) {
					fireMoved();
					repaint();
				}
			}
		};
		f.setLayoutManager(new GraphLayoutManager(this));
		return f;
	}

	/**
	 * @see com.tomecode.soa.dependency.analyzer.view.graph.flow.parts.examples.flow.parts.ActivityPart#deactivate()
	 */
	public void deactivate() {
		getViewer().getEditDomain().getCommandStack().removeCommandStackListener(stackListener);
		super.deactivate();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#isSelectable()
	 */
	public boolean isSelectable() {
		return false;
	}

	/**
	 * @see org.eclipse.gef.examples.flow.parts.StructuredActivityPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
	}

}
